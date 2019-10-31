/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.aop
 * 文件名：	AopAdviceChainInvocation
 * 模块说明：
 * 修改历史：
 * 2019/9/25 - tongyongjian - 创建。
 */

package pers.john.spring.aop;

import pers.john.spring.aop.advice.MethodAfterAdvice;
import pers.john.spring.aop.advice.MethodAroundAdvice;
import pers.john.spring.aop.advice.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 责任链式调用
 * @author tongyongjian
 * @date 2019/9/25
 */
public class AopAdviceChainInvocation {

    private static Method INVOKE_METHOD;

    static {
        try {
            INVOKE_METHOD = AopAdviceChainInvocation.class.getMethod("invoke", null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Object target;

    private Method method;

    private Object[] args;

    private Object proxy;

    private List<Object> advices;

    public AopAdviceChainInvocation(Object target, Method method, Object[] args, Object proxy,
        List<Object> advices) {
        this.target = target;
        this.method = method;
        this.args = args;
        this.proxy = proxy;
        this.advices = advices;
    }

    private int index = 0;
    public Object invoke() throws Throwable {
        // 实现链式调用
        if(index < advices.size()) {
            // 获取通知
            Object advice = advices.get(index ++);
            // 前置增强
            if(advice instanceof MethodBeforeAdvice) {
                ((MethodBeforeAdvice) advice).before(method, args, target);
            }
            // 环绕增强, 环绕增强需要使用当前链式调用的类
            else if(advice instanceof MethodAroundAdvice) {
                return ((MethodAroundAdvice) advice).around(INVOKE_METHOD, null, this);
            }
            // 后置增强，需要先得到结果再执行后置增强
            else if(advice instanceof MethodAfterAdvice) {
                Object object = this.invoke();
                ((MethodAfterAdvice) advice).after(object, method, args, target);
                return object;
            }
            return this.invoke();
        } else {
            return method.invoke(target, args);
        }
    }
}
