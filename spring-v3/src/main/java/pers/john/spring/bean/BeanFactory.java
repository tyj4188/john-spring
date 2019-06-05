
package pers.john.spring.bean;

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
}
