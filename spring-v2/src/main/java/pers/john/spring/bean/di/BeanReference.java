package pers.john.spring.bean.di;

/**
 * 引用类型的注入
 */
public class BeanReference {

    private String beanName;

    public BeanReference() {
    }

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
