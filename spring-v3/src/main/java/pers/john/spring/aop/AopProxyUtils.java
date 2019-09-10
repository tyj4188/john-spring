/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.aop
 * 文件名：	AopProxyUtils
 * 模块说明：
 * 修改历史：
 * 2019/9/10 - tongyongjian - 创建。
 */

package pers.john.spring.aop;

import pers.john.spring.aop.advisor.Advisor;
import pers.john.spring.bean.BeanFactory;

import java.lang.reflect.Method;
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
        , List<Advisor> matchAdvices, Object proxy, BeanFactory beanFactory) {
        // TODO 判断哪些通知可以进行增强调用

        // TODO 判断是否需要增强调用

        // TODO 不需要增强直接执行方法并返回

        // TODO 需要增强，递归调用链进行增强

        return null;
    }
}
