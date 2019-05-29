package pers.john.spring.bean;

/**
 * 基本的 Bean 定义
 */
public class GenericBeanDefinition implements BeanDefinition {

    private Class<?> beanClass;

    // 默认单例
    private String scope = BeanDefinition.SINGLETON;

    private String factoryMethodName;

    private String factoryBeanName;

    private String initMethodName;

    private String destroyMethodName;

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getFactoryMethodName() {
        return factoryMethodName;
    }

    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public boolean isSingleton() {
        return BeanDefinition.SINGLETON.equals(this.scope);
    }

    public boolean isPrototype() {
        return !isSingleton();
    }
}
