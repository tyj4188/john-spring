/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.aop
 * 文件名：	DefaultAopProxyFactory
 * 模块说明：
 * 修改历史：
 * 2019/10/29 - tongyongjian - 创建。
 */

package pers.john.spring.aop;

import pers.john.spring.aop.advisor.Advisor;
import pers.john.spring.bean.BeanFactory;

import java.util.List;

/**
 * 代理工厂默认实现
 * @author tongyongjian
 * @date 2019/10/29
 */
public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchedAdvisor,
        BeanFactory beanFactory) {
        if(shouldUseJdkDynamicProxy(bean, beanName)) {
            return new JdkDynamicAopProxy(beanName, bean, matchedAdvisor, beanFactory);
        }
        return new CglibDynamicAopProxy(beanName, bean, matchedAdvisor, beanFactory);
    }

    /**
     * 判断是否需要使用 JDK 动态代理
     * @return
     */
    private boolean shouldUseJdkDynamicProxy(Object bean, String beanName) {
        // TODO 判断是否实现 JDK 动态代理接口
        return false;
    }
}
