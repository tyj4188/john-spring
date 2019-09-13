/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.aop
 * 文件名：	JdkDynamicAopProxy
 * 模块说明：
 * 修改历史：
 * 2019/9/10 - tongyongjian - 创建。
 */

package pers.john.spring.aop;

import pers.john.spring.aop.advisor.Advisor;
import pers.john.spring.bean.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * JDK 动态代理实现
 * 需要持有 :
 *  beanName
 *  代理目标对象
 *  匹配的通知
 *  BeanFactory
 *
 * @author tongyongjian
 * @date 2019/9/10
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private String beanName;
    private Object target;
    private List<Advisor> matchAdvisors;
    private BeanFactory beanFactory;

    public JdkDynamicAopProxy(String beanName, Object target,
        List<Advisor> matchAdvisors, BeanFactory beanFactory) {
        super();
        this.beanName = beanName;
        this.target = target;
        this.matchAdvisors = matchAdvisors;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 通过 AopProxyUtils 实现增强调用
        return AopProxyUtils.applyAdvices(target, method, args, matchAdvisors, proxy, beanFactory);
    }

    @Override
    public Object getProxy() {
        return this.getProxy(target.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        // 调用 JDK 动态代理创建代理对象
        return Proxy.newProxyInstance(classLoader, target.getClass().getInterfaces(), this);
    }
}
