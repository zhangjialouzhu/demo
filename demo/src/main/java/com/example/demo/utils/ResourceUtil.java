package com.example.demo.utils;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.ResourcePropertySource;

public class ResourceUtil {
    public static void test() throws Exception{
        Resource resource = new FileSystemResource(PathUtil.getPath()+"/conf/patent_main.properties");
        EncodedResource encodedResource = new EncodedResource(resource,"UTF-8");
        ResourcePropertySource source = new ResourcePropertySource(encodedResource);
        System.out.println(source);

    }

    public static void main(String[] args) throws Exception{
        test();
    }
}
