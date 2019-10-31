package pers.john.spring.aop;

import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import pers.john.spring.aop.advisor.Advisor;
import pers.john.spring.aop.advisor.AdvisorRegistry;
import pers.john.spring.aop.advisor.PointcutAdvisor;
import pers.john.spring.aop.pointcut.Pointcut;
import pers.john.spring.bean.BeanFactory;
import pers.john.spring.bean.BeanFactoryAware;
import pers.john.spring.bean.BeanPostProcessor;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 通知者自动代理生成器
 */
public class AdvisorAutoProxyCreator implements BeanPostProcessor, AdvisorRegistry,
    BeanFactoryAware {

    private BeanFactory beanFactory;

    private List<Advisor> advisorList = new ArrayList<>(16);

    @Override
    public void registryAdvisor(Advisor advisor) {
        advisorList.add(advisor);
    }

    @Override
    public List<Advisor> getAdvisor() {
        return advisorList;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) {
        // 判断此 Bean 是否需要切面增强, 不需要增强就直接返回
        List<Advisor> matchedAdvisor = getAllMatchedAdvisor(bean, beanName);
        if(CollectionUtils.isEmpty(matchedAdvisor)) {
            return bean;
        }

        // 需要增强就返回代理对象
        return createProxy(bean, beanName, matchedAdvisor);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取当前对象所有匹配的切面通知
     * @param bean
     * @param beanName
     * @return
     */
    private List<Advisor> getAllMatchedAdvisor(Object bean, String beanName) {
        if(CollectionUtils.isEmpty(this.advisorList)) {
            return null;
        }

        // 获取对象的所有方法
        Class beanClass = bean.getClass();
        List<Method> methodList = getAllMethod(beanClass);

        List<Advisor> matched = new ArrayList<>();
        // 遍历 Advisor 查找匹配的
        for(Advisor temp : this.advisorList) {
            if(temp instanceof PointcutAdvisor) {
                if(isPointcutMethod((PointcutAdvisor) temp, beanClass, methodList)) {
                    matched.add(temp);
                }
            }
        }

        return matched;
    }

    /**
     * 获取所有方法
     * @param beanClass
     * @return
     */
    private List<Method> getAllMethod(Class<?> beanClass) {
        List<Method> methodList = new LinkedList<>();
        Set<Class<?>> classSet = new LinkedHashSet<>(ClassUtils.getAllInterfacesAsSet(beanClass));

        classSet.add(beanClass);

        for(Class<?> temp : classSet) {
            Method[] tempArr = ReflectionUtils.getAllDeclaredMethods(temp);
            for(Method m : tempArr) {
                methodList.add(m);
            }
        }

        return methodList;
    }

    /**
     * 判断当前切面是否需要在这个 Bean 上加强
     * @param advisor 切面通知
     * @param beanClass Bean Class 对象
     * @param methodList Bean 的所有方法
     * @return
     */
    private boolean isPointcutMethod(PointcutAdvisor advisor, Class<?> beanClass, List<Method> methodList) {
        if(advisor == null) {
            return false;
        }

        // 获取切点
        Pointcut pointcut = advisor.getPointcut();

        // 先判断类是否匹配
        if(!pointcut.matchClass(beanClass)) {
            return false;
        }

        // 再判断方法是否匹配
        for(Method temp : methodList) {
            if(pointcut.matchMethod(temp, beanClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建代理对象
     * @param bean 需要代理的对象
     * @param beanName 对象名称
     * @param matchedAdvisor 匹配的切面
     * @return
     */
    private Object createProxy(Object bean, String beanName, List<Advisor> matchedAdvisor) {
        return AopProxyFactory.getDefaultAopProxyFactory().createAopProxy(bean, beanName, matchedAdvisor, beanFactory).getProxy();
    }
}
