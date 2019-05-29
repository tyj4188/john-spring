package pers.john.spring.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 提前初始化
 */
public class PreBuildBeanFactory extends DefaultBeanFactory {

    private List<String> BEAN_NAME_LIST = new ArrayList<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
        throws BeanDefinitionRegistryException {
        super.registerBeanDefinition(beanName, beanDefinition);

        synchronized (BEAN_NAME_LIST) {
            BEAN_NAME_LIST.add(beanName);
        }
    }

    /**
     * 预实例化单例对象
     */
    public void preInstantiateSingleton() throws Exception {
        synchronized (BEAN_NAME_LIST) {
            for(String beanName : BEAN_NAME_LIST) {
                BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
                if(beanDefinition.isSingleton()) {
                    this.doGetBean(beanName);
                }
            }
        }
    }
}
