package pers.john.spring.aop.pointcut;

import java.lang.reflect.Method;

/**
 * 切点，用来确定哪些方法需要增强
 */
public interface Pointcut {
    /**
     * 匹配类
     * @param beanClass
     * @return
     */
    public boolean matchClass(Class<?> beanClass);

    /**
     * 匹配类方法
     * @param method
     * @param beanClass
     * @return
     */
    public boolean matchMethod(Method method, Class<?> beanClass);
}
