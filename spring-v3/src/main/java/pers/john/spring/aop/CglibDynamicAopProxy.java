/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.aop
 * 文件名：	CglibDynamaicAopProxy
 * 模块说明：
 * 修改历史：
 * 2019/9/10 - tongyongjian - 创建。
 */

package pers.john.spring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import pers.john.spring.aop.advisor.Advisor;
import pers.john.spring.bean.BeanDefinition;
import pers.john.spring.bean.BeanFactory;
import pers.john.spring.bean.DefaultBeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Cglib 实现动态代理
 * @author tongyongjian
 * @date 2019/9/10
 */
public class CglibDynamicAopProxy implements AopProxy, MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    private String beanName;
    private Object target;
    private List<Advisor> matchAdvisors;
    private BeanFactory beanFactory;

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy)
        throws Throwable {
        return AopProxyUtils.applyAdvices(target, method, args, matchAdvisors, methodProxy, beanFactory);
    }

    @Override
    public Object getProxy() {
        return getProxy(target.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

        Class<?> superClass = this.target.getClass();
        enhancer.setSuperclass(superClass);
        enhancer.setInterfaces(this.getClass().getInterfaces());
        enhancer.setCallback(this);
        Constructor constructor = null;
        try {
            constructor = superClass.getConstructor(new Class<?>[]{});
        } catch (NoSuchMethodException e) {
        }

        if(constructor != null) {
            return enhancer.create();
        } else {
            BeanDefinition definition = ((DefaultBeanFactory)beanFactory).getBeanDefinition(this.beanName);
            return enhancer.create(definition.getConstructor().getParameterTypes()
                , definition.getConstructorArgumentValues());
        }
    }
}
