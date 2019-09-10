package pers.john.spring.aop.advice;

import java.lang.reflect.Method;

public interface MethodAfterAdvice extends Advice {

    /**
     * 后置增强
     * 在方法之后增强，不需要返回值，但有可能需要处理被增强方法的返回值
     * @param returnValue
     * @param method
     * @param args
     * @param target
     */
    public void after(Object returnValue, Method method, Object[] args, Object target);
}
