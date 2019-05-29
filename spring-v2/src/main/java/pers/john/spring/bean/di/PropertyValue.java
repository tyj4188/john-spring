package pers.john.spring.bean.di;

/**
 * DI 注入的参数名和值
 */
public class PropertyValue {
    private String name;

    private Object value;

    public PropertyValue() {
    }

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
