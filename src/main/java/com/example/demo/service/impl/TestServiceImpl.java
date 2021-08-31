package com.example.demo.service.impl;

import com.example.demo.service.TestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public void test1() {
        System.out.println("1");
        List<String> list = null;
         list.get(1);
    }

    @Override
    public void test2() throws Exception {
        System.out.println("2");
        List<String> list = null;
         list.get(1);
    }

    @Override
    public void test3() {
        try {
            List<String> list = null;
             list.get(1);
        } catch (Exception e) {
            System.out.println("3");
        }
    }

    @Override
    public void test4() throws Exception {
        try {
            List<String> list = null;
             list.get(1);
        } catch (Exception e) {
            System.out.println("4");
            throw e;
        }
    }
}
