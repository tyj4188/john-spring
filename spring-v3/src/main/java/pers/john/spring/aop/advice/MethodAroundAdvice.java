package pers.john.spring.aop.advice;

import java.lang.reflect.Method;

public interface MethodAroundAdvice extends Advice {

    /**
     * 环绕增强
     * @param method
     * @param args
     * @param target
     * @return
     */
    public Object around(Method method, Object[] args, Object target) throws Throwable;
}
