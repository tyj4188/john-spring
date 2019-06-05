package pers.john.spring.samples;

public class Girl {
    private String name;

    private int age;

    private String cup;

    private int legLength;

    private Boy boy;

    public Girl() {
    }

    public Girl(String name, int age, Boy boy) {
        this.name = name;
        this.age = age;
        this.boy = boy;
    }

    public void doSomething() {
        System.out.println(String.format("Gir : name = %s, age = %s, cup = %s, legLength = %s"
            , name, age, cup, legLength));
        System.out.println("Girl's Boy : ");
        if (null != boy) {
            boy.doSomething();
        }
    }
}
