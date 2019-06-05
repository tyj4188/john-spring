package pers.john.spring.test.v1;

import org.junit.Test;
import pers.john.spring.bean.BeanDefinition;
import pers.john.spring.bean.DefaultBeanFactory;
import pers.john.spring.bean.GenericBeanDefinition;
import pers.john.spring.samples.ABean;
import pers.john.spring.samples.ABeanFactory;

public class AppTest {

    static DefaultBeanFactory factory = new DefaultBeanFactory();

    // 类型创建
    @Test
    public void testClassCreate() throws Exception {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(ABean.class);
        beanDefinition.setScope(BeanDefinition.PROTOTYPE);
        factory.registerBeanDefinition("aBean", beanDefinition);

        ABean aBean = (ABean) factory.getBean("aBean");
        ABean aBean2 = (ABean) factory.getBean("aBean");

        aBean.doSomething();
        aBean2.doSomething();
    }

    // 静态工厂
    @Test
    public void testStaticFactoryCreate() throws Exception {
        GenericBeanDefinition factoryBeanDefinition = new GenericBeanDefinition();
        factoryBeanDefinition.setBeanClass(ABeanFactory.class);
        factoryBeanDefinition.setFactoryMethodName("getABeanByStatic");
        factory.registerBeanDefinition("aBeanFactory", factoryBeanDefinition);

        ABean aBean = (ABean) factory.getBean("aBeanFactory");
        aBean.doSomething();
    }

    // 工厂 Bean 创建
    @Test
    public void testFactoryBeanCreate() throws Exception {
        GenericBeanDefinition factoryBeanDefinition = new GenericBeanDefinition();
        factoryBeanDefinition.setBeanClass(ABeanFactory.class);
        factory.registerBeanDefinition("aBeanFactory", factoryBeanDefinition);
        GenericBeanDefinition aBeanDefinition = new GenericBeanDefinition();
        aBeanDefinition.setFactoryBeanName("aBeanFactory");
        aBeanDefinition.setFactoryMethodName("getABean");
        factory.registerBeanDefinition("aBean", aBeanDefinition);

        ABean aBean = (ABean) factory.getBean("aBean");
        aBean.doSomething();
    }

    @Test
    public void testInitAndDestroy() throws Exception {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(ABean.class);
        beanDefinition.setInitMethodName("init");
        beanDefinition.setDestroyMethodName("destroy");
        factory.registerBeanDefinition("aBean", beanDefinition);
        ABean aBean = (ABean) factory.getBean("aBean");
        aBean.doSomething();
    }
}
