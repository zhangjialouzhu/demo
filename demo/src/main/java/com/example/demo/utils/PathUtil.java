package com.example.demo.utils;

public class PathUtil {
    public static String getPath(){
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return path;
    }

}
