package pers.john.spring.test.v2;

import org.junit.Test;
import pers.john.spring.bean.DefaultBeanFactory;
import pers.john.spring.bean.GenericBeanDefinition;
import pers.john.spring.bean.di.BeanReference;
import pers.john.spring.bean.di.PropertyValue;
import pers.john.spring.samples.Boy;
import pers.john.spring.samples.Girl;

import java.util.*;

public class DITest {

    static DefaultBeanFactory factory = new DefaultBeanFactory();

    // DI 注入
    @Test
    public void testDI() throws Exception {
        GenericBeanDefinition girlDefinition = new GenericBeanDefinition();
        girlDefinition.setBeanClass(Girl.class);
        List<Object> girlArgs = Arrays.asList("小丽", 18, new BeanReference("boy"));
        girlDefinition.setConstructorArgumentValues(girlArgs);
        factory.registerBeanDefinition("girl", girlDefinition);

        GenericBeanDefinition boyDefinition = new GenericBeanDefinition();
        boyDefinition.setBeanClass(Boy.class);
        List<Object> boyArgs = Arrays.asList("小明", 19, 20);
        boyDefinition.setConstructorArgumentValues(boyArgs);
        factory.registerBeanDefinition("boy", boyDefinition);

        Girl girl = (Girl) factory.getBean("girl");
        girl.doSomething();
    }

    @Test
    public void testPropertyDI() throws Exception {
        GenericBeanDefinition girlDefinition = new GenericBeanDefinition();
        girlDefinition.setBeanClass(Girl.class);
        List<Object> girlArgs = Arrays.asList("小丽", 18, new BeanReference("boy"));
        girlDefinition.setConstructorArgumentValues(girlArgs);
        PropertyValue propertyValue = new PropertyValue("legLength", 110);
        girlDefinition.setPropertyValues(Arrays.asList(propertyValue));
        factory.registerBeanDefinition("girl", girlDefinition);

        GenericBeanDefinition boyDefinition = new GenericBeanDefinition();
        boyDefinition.setBeanClass(Boy.class);
        List<Object> boyArgs = Arrays.asList("小明", 19, 20);
        boyDefinition.setConstructorArgumentValues(boyArgs);
        factory.registerBeanDefinition("boy", boyDefinition);

        Girl girl = (Girl) factory.getBean("girl");
        girl.doSomething();
    }

    @Test
    public void testCircleDI() {

    }

    @Test
    public void test() {
        DoubleSummaryStatistics ds = new DoubleSummaryStatistics();
        ds.accept(4.94);
        ds.accept(4.94);
        ds.accept(5);
        ds.accept(5);
        ds.accept(5);
        ds.accept(5);
        ds.accept(5);
        System.out.println(ds.getAverage());
    }
}
