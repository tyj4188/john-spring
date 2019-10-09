package pers.john.spring.bean;

public interface BeanPostProcessor {

    public Object postProcessorBeforeInitialization(Object bean, String beanName);

    public Object postProcessorAfterInitialization(Object bean, String beanName);
}
