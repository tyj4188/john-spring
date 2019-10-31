/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.aop
 * 文件名：	AopProxyUtils
 * 模块说明：
 * 修改历史：
 * 2019/9/10 - tongyongjian - 创建。
 */

package pers.john.spring.aop;

import org.springframework.util.CollectionUtils;
import pers.john.spring.aop.advisor.Advisor;
import pers.john.spring.aop.advisor.PointcutAdvisor;
import pers.john.spring.bean.BeanFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理工具类
 *  功能 : 实现代理增强
 * @author tongyongjian
 * @date 2019/9/10
 */
public class AopProxyUtils {

    /**
     * 执行代理增强
     * @param target 被代理对象
     * @param method 执行的方法
     * @param args 方法参数
     * @param matchAdvices 需要匹配的通知
     * @param proxy 代理对象
     * @param beanFactory bean 工厂
     * @return 方法执行返回的结果
     */
    public static Object applyAdvices(Object target, Method method, Object[] args
        , List<Advisor> matchAdvices, Object proxy, BeanFactory beanFactory) throws Throwable {
        // 判断哪些通知可以进行增强调用
        List<Object> advices = getShouldApplyAdvices(target.getClass()
            , method, matchAdvices, beanFactory);
        // 判断是否需要增强调用
        if(CollectionUtils.isEmpty(advices)) {
            // 不需要增强直接执行方法并返回
            return method.invoke(target, args);
        }
        // 需要增强，递归调用链进行增强
        AopAdviceChainInvocation chain = new AopAdviceChainInvocation(target, method, args, proxy, advices);
        return chain.invoke();
    }

    /**
     * 获取当前需要增强的切面通知
     * @param beanClass 需要增强的对象
     * @param method 增强的方法
     * @param matchAdvices 所有切面
     * @param beanFactory 通过工厂获取通知对象
     * @return
     * @throws Exception
     */
    private static List<Object> getShouldApplyAdvices(Class<?> beanClass
        , Method method, List<Advisor> matchAdvices, BeanFactory beanFactory) throws Exception {

        if(!CollectionUtils.isEmpty(matchAdvices)) {
            List<Object> arr = new ArrayList<>(matchAdvices.size());
            for(Advisor advisor : matchAdvices) {
                if(advisor instanceof PointcutAdvisor &&
                    (((PointcutAdvisor) advisor).getPointcut().matchMethod(method, beanClass))) {
                    arr.add(beanFactory.getBean(advisor.getAdviceBeanName()));
                }
            }
            return arr;
        }

        return null;
    }
}
