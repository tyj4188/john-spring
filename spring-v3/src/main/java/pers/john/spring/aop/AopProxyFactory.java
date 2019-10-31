/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.aop
 * 文件名：	AopProxyFactory
 * 模块说明：
 * 修改历史：
 * 2019/10/29 - tongyongjian - 创建。
 */

package pers.john.spring.aop;

import pers.john.spring.aop.advisor.Advisor;
import pers.john.spring.bean.BeanFactory;

import java.util.List;

/**
 * 代理类工厂
 * @author tongyongjian
 * @date 2019/10/29
 */
public interface AopProxyFactory {
    public AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchedAdvisor, BeanFactory beanFactory);

    public static AopProxyFactory getDefaultAopProxyFactory() {
        return new DefaultAopProxyFactory();
    }
}
