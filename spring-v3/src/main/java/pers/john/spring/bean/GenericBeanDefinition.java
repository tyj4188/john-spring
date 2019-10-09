package pers.john.spring.bean;

import pers.john.spring.bean.di.PropertyValue;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * 基本的 Bean 定义
 */
public class GenericBeanDefinition implements BeanDefinition {

    private Class<?> beanClass;

    private Constructor<?> constructor;

    // 默认单例
    private String scope = BeanDefinition.SINGLETON;

    private String factoryMethodName;

    private String factoryBeanName;

    private String initMethodName;

    private String destroyMethodName;

    // 构造器参数值
    private List<?> constructorArgumentValues;

    private Object[] constructorArgumentRealValues;

    // DI 注入的成员变量
    private List<PropertyValue> propertyValues;

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

    @Override
    public List<?> getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    @Override
    public void setConstructorArgumentRealValues(Object[] constructorArgumentRealValues) {
        this.constructorArgumentRealValues = constructorArgumentRealValues;
    }

    @Override
    public Object[] getConstructorArgumentRealValues() {
        return constructorArgumentRealValues;
    }

    @Override
    public Constructor<?> getConstructor() {
        return constructor;
    }

    @Override
    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }

    public void setConstructorArgumentValues(List<?> constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
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
