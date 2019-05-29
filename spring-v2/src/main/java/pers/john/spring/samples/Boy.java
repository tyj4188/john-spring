package pers.john.spring.samples;

public class Boy {
    private String name;

    private int age;

    private int length;

    public Boy() {
    }

    public Boy(String name, int age, int length) {
        this.name = name;
        this.age = age;
        this.length = length;
    }

    public void doSomething() {
        System.out.println(String.format("Boy : name = %s, age = %s, length = %s", name, age, length));
    }
}
