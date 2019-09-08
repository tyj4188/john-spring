
package pers.john.spring.bean;

import pers.john.spring.aop.BeanPostProcessor;

/**
 * Bean 工厂
 */
public interface BeanFactory {
    /**
     * 获取对象
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;

    void registerBeanPostProcessor(BeanPostProcessor processor);
}
