package pers.john.spring.bean;

/**
 * GetBean 创建对象的后续处理
 */
public interface BeanPostProcessor {

    /**
     * 初始化前的处理
     * @param bean
     * @param beanName
     * @return
     */
    default Object postProcessorBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    /**
     * 初始化后的处理
     * @param bean
     * @param beanName
     * @return
     */
    default Object postProcessorAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
