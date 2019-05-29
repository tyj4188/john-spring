package pers.john.spring.bean;

/**
 * Bean 定义注册
 */
public class BeanDefinitionRegistryException extends Exception {
    public BeanDefinitionRegistryException(String message) {
        super(message);
    }

    public BeanDefinitionRegistryException(String message, Throwable cause) {
        super(message, cause);
    }
}
