package com.example.demo.bean;

import lombok.Data;

import java.util.List;
@Data
public class SqlBean {
    /**
     * 数据库字段
     */

    private List<String> fields;
    /**
     * 城市
     */
    private String city;
    /**
     * 区域
     */
    private String district;
    /**
     * 街道
     */
    private String township;
    /**
     * 申请日
     */
    private String apd;
    /**
     * 发布日
     */
    private String grpd;



}
