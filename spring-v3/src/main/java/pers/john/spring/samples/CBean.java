package pers.john.spring.samples;

public class CBean {
    private DBean dBean;

    public CBean(DBean dBean) {
        this.dBean = dBean;
    }

    public void doSomething() {
        System.out.println("CBean.doSomething");
    }
}
