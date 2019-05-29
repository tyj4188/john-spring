package pers.john.spring.bean;

import pers.john.spring.utils.StringUtils;

/**
 * Bean 定义
 */
public interface BeanDefinition {

    static final String SINGLETON = "singleton";

    static final String PROTOTYPE = "prototype";

    /**
     * 获取类型
     * @return
     */
    Class getBeanClass();

    /**
     * 获取工厂方法名
     * @return
     */
    String getFactoryMethodName();

    /**
     * 获取工厂类名
     * @return
     */
    String getFactoryBeanName();

    /**
     * 初始化方法
     * @return
     */
    String getInitMethodName();

    /**
     * 对象销毁方法
     * @return
     */
    String getDestroyMethodName();

    /**
     * 类型
     * @return
     */
    String getScope();

    /**
     * 是否单例
     * @return
     */
    boolean isSingleton();

    /**
     * 是否原型
     * @return
     */
    boolean isPrototype();

    default boolean validate() {

        // BeanClass 和 工厂方法、工厂类，必须指定一个
        if(null == getBeanClass()) {
            if(StringUtils.isEmpty(getFactoryMethodName())
                || StringUtils.isEmpty(getFactoryBeanName())) {
                return false;
            }
        }

        // 对象类和工厂类不能同时存在
        if(null != getBeanClass() && StringUtils.isNotBlank(getFactoryBeanName())) {
            return false;
        }

        return true;
    }
}
