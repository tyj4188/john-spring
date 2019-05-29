package pers.john.spring.bean;

/**
 * Bean 定义注册
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws
        BeanDefinitionRegistryException;

    BeanDefinition getBeanDefinition(String beanName);

    boolean containsBeanDefinition(String beanName);
}
