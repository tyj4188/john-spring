package pers.john.spring.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClassUtils {
    // 基础类 MAP
    private static final Map<String, Class> primitiveTypeMap = new HashMap<>(8);

    // 基础包装类 MAP
    private static final Map<Class, Class> primitiveWrapperMap = new HashMap<>(8);

    static {
        primitiveWrapperMap.put(Boolean.class, boolean.class);
        primitiveWrapperMap.put(Byte.class, byte.class);
        primitiveWrapperMap.put(Character.class, char.class);
        primitiveWrapperMap.put(Double.class, double.class);
        primitiveWrapperMap.put(Float.class, float.class);
        primitiveWrapperMap.put(Integer.class, int.class);
        primitiveWrapperMap.put(Long.class, long.class);
        primitiveWrapperMap.put(Short.class, short.class);

        for(Iterator it = primitiveWrapperMap.values().iterator(); it.hasNext();) {
            Class primitiveClass = (Class) it.next();
            primitiveTypeMap.put(primitiveClass.getName(), primitiveClass);
        }
    }

    public static Class getPrimitiveClassByWrapper(Class wrapperClass) {
        return primitiveWrapperMap.get(wrapperClass);
    }
}
