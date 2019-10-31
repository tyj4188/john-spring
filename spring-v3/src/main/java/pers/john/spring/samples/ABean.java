package pers.john.spring.samples;

public class ABean {

    public void doSomething() {
        System.out.println(System.currentTimeMillis() + " : " + this);
    }

    public String eating() {
        System.out.println("ABean.eating is executed");
        return "eating...";
    }

    public void init() {
        System.out.println("ABean.init 执行了...");
    }

    public void destroy() {
        System.out.println("ABean.destroy 执行了...");
    }
}
