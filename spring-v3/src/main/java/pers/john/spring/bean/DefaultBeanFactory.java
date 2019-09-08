package pers.john.spring.bean;

import pers.john.spring.aop.BeanPostProcessor;
import pers.john.spring.bean.di.BeanReference;
import pers.john.spring.bean.di.PropertyValue;
import pers.john.spring.utils.ClassUtils;
import pers.john.spring.utils.CollectionUtils;
import pers.john.spring.utils.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {

    private static Map<String, BeanDefinition> BEAN_DEFINITION_MAP = new ConcurrentHashMap(32);

    private static Map<String, Object> BEAN_MAP = new ConcurrentHashMap(32);

    private ThreadLocal<Set<String>> buildingBeans = new ThreadLocal<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
        throws BeanDefinitionRegistryException {
        Objects.requireNonNull(beanName, "注册 Bean 需要 Bean Name");
        Objects.requireNonNull(beanDefinition, "注册 Bean 需要 Bean Definition");

        if(!beanDefinition.validate()) {
            throw new BeanDefinitionRegistryException("名字为 [%s] 的 Bean Definition 定义不合法");
        }

        if(containsBeanDefinition(beanName)) {
            throw new BeanDefinitionRegistryException("名字为 [%s] 的 Bean Definition 已存在");
        }

        BEAN_DEFINITION_MAP.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return BEAN_DEFINITION_MAP.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return BEAN_DEFINITION_MAP.containsKey(beanName);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return doGetBean(beanName);
    }

    @Override
    public void registerBeanPostProcessor(BeanPostProcessor processor) {

    }

    protected Object doGetBean(String beanName) throws Exception {
        Objects.requireNonNull(beanName, "BeanFactory Bean Name 参数不能为空！");

        Object instance = BEAN_MAP.get(beanName);

        if(instance != null) {
            return instance;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Objects.requireNonNull(beanDefinition, String.format("[%s] BeanDefinition 不存在", beanName));

        // 记录正在创建的 Bean
        Set<String> buildBeanSet = buildingBeans.get();
        if(null == buildBeanSet) {
            buildBeanSet = new HashSet<>();
            buildingBeans.set(buildBeanSet);
        }

        // 判断当前的Bean是否已经在构造中了
        if(buildBeanSet.contains(beanName)) {
            throw new Exception(String.format("循环依赖 BeanName [%s] ", beanName));
        }

        // 构建中的 Bean
        buildBeanSet.add(beanName);

        Class<?> type = beanDefinition.getBeanClass();
        if(type != null) {
            if(StringUtils.isEmpty(beanDefinition.getFactoryMethodName())) {
                // 通过构造器创建
                instance = createInstanceByConstructor(beanDefinition);
            } else {
                // 通过静态工厂创建
                instance = createInstanceByStaticFactoryMethod(beanDefinition);
            }
        } else {
            // 通过工厂对象
            instance = createInstanceByFactoryMethod(beanDefinition);
        }

        // DI 功能
        setPropertyDIValue(beanDefinition, instance);

        // 调用托管实例的初始化方法
        doInit(instance, beanDefinition);

        // 初始化完成后删除构建中状态
        buildBeanSet.remove(beanName);

        // 单例的放入缓存中
        if(beanDefinition.isSingleton()) {
            BEAN_MAP.put(beanName, instance);
        }

        return instance;
    }

    // 构造器
    private Object createInstanceByConstructor(BeanDefinition beanDefinition)
        throws Exception {
        // 判断是否设置构造器参数
        Object[] args = getConstructorArgumentValues(beanDefinition);
        if(args == null) {
            return beanDefinition.getBeanClass().newInstance();
        } else {
            // 查找对应的带参构造器
            return determineConstructor(beanDefinition, args).newInstance(args);
        }
    }

    // 静态工厂
    private Object createInstanceByStaticFactoryMethod(BeanDefinition beanDefinition)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class type = beanDefinition.getBeanClass();
        Method method = type.getMethod(beanDefinition.getFactoryMethodName(), null);
        return  method.invoke(type, null);
    }

    // 工厂 Bean 创建
    private Object createInstanceByFactoryMethod(BeanDefinition beanDefinition) throws Exception {
        Object factory = this.doGetBean(beanDefinition.getFactoryBeanName());
        Method method = factory.getClass().getMethod(beanDefinition.getFactoryMethodName(), null);
        return  method.invoke(factory, null);
    }

    private void setPropertyDIValue(BeanDefinition beanDefinition, Object instance)
        throws Exception {
        // DI
        if(CollectionUtils.isEmpty(beanDefinition.getPropertyValues())) {
            return;
        }

        Class<?> clazz = instance.getClass();

        for(PropertyValue tempPv : beanDefinition.getPropertyValues()) {
            if(StringUtils.isEmpty(tempPv.getName())) {
                continue;
            }
            Field field = clazz.getDeclaredField(tempPv.getName());
            field.setAccessible(true);
            Object argValue = argValueProcess(tempPv.getValue());
            field.set(instance, argValue);
        }
    }

    // 初始化方法
    private void doInit(Object instance, BeanDefinition beanDefinition)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(instance != null && StringUtils.isNotEmpty(beanDefinition.getInitMethodName())) {
            Method method = instance.getClass().getMethod(beanDefinition.getInitMethodName(), null);
            method.invoke(instance, null);
        }
    }

    private void doDestroy(Object instance, BeanDefinition beanDefinition)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        if(instance != null && StringUtils.isNotEmpty(beanDefinition.getInitMethodName())) {
            Method method = instance.getClass().getMethod(beanDefinition.getDestroyMethodName(), null);
            method.invoke(instance, null);
        }
    }

    private Constructor<?> determineConstructor(BeanDefinition beanDefinition, Object[] args)
        throws Exception {
        // 通过参数签名获取对应的构造器
        Constructor<?> constructor = null;
        Class beanClass = beanDefinition.getBeanClass();

        if(null == args || args.length == 0) {
            // 返回无参构造器
            return beanClass.getConstructor(null);
        }

        if((constructor = beanDefinition.getConstructor()) != null) {
            return constructor;
        }

        // 获取参数类型并精确匹配
        try {
            constructor = beanClass.getConstructor(getArgumentType(args));
        } catch (Exception e) {
            // 不需要做处理
        }

        // 精确匹配没有匹配到，采用查找的方式
        if(constructor == null) {
            // 先判断参数数量，然后每个类型都一一对应判断
            out : for(Constructor<?> outTemp : beanClass.getConstructors()) {
                Class[] tempTypes = outTemp.getParameterTypes();
                if(tempTypes.length == args.length) {
                    for(int i = 0; i < tempTypes.length; i++) {
                        // 基本类型
                        if (tempTypes[i].isPrimitive()) {
                            Class primitiveClass = ClassUtils.getPrimitiveClassByWrapper(args[i].getClass());
                            if(primitiveClass == null || !primitiveClass.isAssignableFrom(tempTypes[i])) {
                                continue out;
                            }
                            // 非基本类型
                        } else if(!tempTypes[i].isAssignableFrom(args[i].getClass())) {
                            continue out;
                        }
                    }
                    constructor = outTemp;
                    break out;
                }
            }
        }

        if(constructor != null) {
            // 原型模式的需要缓存，方便下次构造时使用
            if(beanDefinition.isPrototype()) {
                beanDefinition.setConstructor(constructor);
            }
            return constructor;
        } else {
            throw new Exception("未找到对应的构造方法 : " + beanDefinition.getBeanClass().getName());
        }
    }

    private Object[] getConstructorArgumentValues(BeanDefinition beanDefinition) throws Exception {
        return getArgumentRealValues(beanDefinition.getConstructorArgumentValues());
    }

    private Object[] getArgumentRealValues(List<?> args) throws Exception {
        if(CollectionUtils.isEmpty(args)) {
            return null;
        }

        Object[] values = new Object[args.size()];
        for(int i = 0; i < args.size(); i++) {
            values[i] = argValueProcess(args.get(i));
        }

        return values;
    }

    private Class<?>[] getArgumentType(Object[] args) {
        if(args != null) {
            Class<?>[] types = new Class[args.length];
            for(int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }
            return types;
        }
        return null;
    }

    private Object argValueProcess(Object object) throws Exception {
        Object obj = null;
        if(null == object) {
            obj = null;
        } else if (object instanceof BeanReference) {
            obj = doGetBean(((BeanReference) object).getBeanName());
        } else if (object instanceof Object[]) {
            obj = getArgumentRealValues(Arrays.asList((Object[]) object));
        } else if (object instanceof Collection) {
            obj = getArgumentRealValues((List<?>) object);
        } else if (object instanceof Properties) {
            // TODO 处理 Properties 中的 Bean 引用
        } else if (object instanceof Map) {
            // TODO 处理 Map 中的 Bean 引用
        } else {
            obj = object;
        }

        return obj;
    }

    @Override
    public void close() throws IOException {
        // 容器关闭调用对象的销毁方法
        for(Map.Entry<String, BeanDefinition> entry : BEAN_DEFINITION_MAP.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition definition = entry.getValue();

            if(definition.isSingleton() && StringUtils.isNotEmpty(definition.getDestroyMethodName())) {
                // 调用 destroy 方法
                try {
                    doDestroy(BEAN_MAP.get(beanName), definition);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
