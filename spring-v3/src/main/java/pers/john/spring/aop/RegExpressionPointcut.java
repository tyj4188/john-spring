package pers.john.spring.aop;

import java.lang.reflect.Method;

/**
 * 正则表达式的切点实现
 */
public class RegExpressionPointcut implements Pointcut {
    public boolean matchClass(Class<?> beanClass) {
        return false;
    }

    public boolean matchMethod(Method method, Class<?> beanClass) {
        return false;
    }
}
