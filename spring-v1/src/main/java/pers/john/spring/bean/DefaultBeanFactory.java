package pers.john.spring.bean;

import pers.john.spring.utils.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {

    private static Map<String, BeanDefinition> BEAN_DEFINITION_MAP = new ConcurrentHashMap(32);

    private static Map<String, Object> BEAN_MAP = new ConcurrentHashMap(32);

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

    protected Object doGetBean(String beanName) throws Exception {
        Objects.requireNonNull(beanName, "BeanFactory Bean Name 参数不能为空！");

        Object instance = BEAN_MAP.get(beanName);

        if(instance != null) {
            return instance;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Objects.requireNonNull(beanDefinition, String.format("[%s] BeanDefinition 不存在", beanName));

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

        doInit(instance, beanDefinition);

        // 单例的放入缓存中
        if(beanDefinition.isSingleton()) {
            BEAN_MAP.put(beanName, instance);
        }

        return instance;
    }

    // 构造器
    private Object createInstanceByConstructor(BeanDefinition beanDefinition)
        throws IllegalAccessException, InstantiationException {
        return beanDefinition.getBeanClass().newInstance();
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
