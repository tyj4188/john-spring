package pers.john.spring.aop;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends Advice {
    /**
     * 前置增强
     * 在方法前置增强，不需要返回值
     * @param method
     * @param args
     * @param target
     */
    public void befor(Method method, Object[] args, Object target);
}
