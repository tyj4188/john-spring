package pers.john.spring.samples;

public class ABeanFactory {
    public static ABean getABeanByStatic() {
        System.out.println("通过静态工厂创建");
        return new ABean();
    }

    public ABean getABean() {
        System.out.println("通过工厂方法创建");
        return new ABean();
    }
}
