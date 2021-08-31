package com.example.demo.utils;

import java.lang.reflect.Field;

public class SetUtil {
    public  static   <T> T   dynamicSet(Class o,String propertyName, Object val){
        Object obj = null;
        try {
            obj = o.newInstance();
            Field field  = o.getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(obj, val);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T)obj;
    }


}
