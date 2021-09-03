package com.example.demo.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.*;

public class SqlScriptLineRunner {
    public static void main(String[] args) {
        Map<String,List<String>> map = new HashMap<>();
        List<String> list88 = new ArrayList() ;
        list88.add("PATENTMONITOR_XUZHOU     ");
        list88.add("PATENTMONITOR_BAOYING    ");
        list88.add("PATENTMONITOR_CHENYUETING");
        list88.add("PATENTMONITOR_DANTU      ");
        list88.add("PATENTMONITOR_JINHUA     ");
        //list88.add("PATENTMONITOR_LISHUI     ");废弃
        list88.add("PATENTMONITOR_NANNING    ");
        list88.add("PATENTMONITOR_PUKOU      ");
        list88.add("PATENTMONITOR_TAICANG    ");
        list88.add("PATENTMONITOR_XISHANJKQ  ");
        list88.add("PATENTMONITOR_YANCHENG   ");
        list88.add("PATENTMONITOR_YANGZHONG  ");
        list88.add("PATENTMONITOR_YIZHENG    ");

        //list88.add("PATENTMONITOR_JINHUA     ");
        //list88.add("PATENTMONITOR_DANTU      ");
        map.put("12.0.0.88",list88);
        List<String> list254 = new ArrayList() ;
        list254.add("PATENTMONITOR_JINHUA8985  ");
        list254.add("PATENTMONITOR_JINHUA8986  ");
        list254.add("PATENTMONITOR_JINHUA8987  ");
        list254.add("PATENTMONITOR_JINHUA8988  ");
        list254.add("PATENTMONITOR_JINHUA8989  ");
        list254.add("PATENTMONITOR_JINHUA8990  ");
        list254.add("PATENTMONITOR_JINHUA8991  ");
        list254.add("PATENTMONITOR_JINHUA8992  ");
        list254.add("PATENTMONITOR_JINHUA8993  ");
        list254.add("PATENTMONITOR_JINHUA8994  ");
        list254.add("PATENTMONITOR_NJJKQ       ");
        list254.add("PATENTMONITOR_PUKOUGXQ    ");
        list254.add("PATENTMONITOR_WJGXQ       ");
        list254.add("PATENTMONITOR_XUANCHENGGXQ");
        list254.add("PATENTMONITOR_ZJGXQ       ");
        list254.add("PATENTMONITOR_ZJXINQU     ");
        list254.add("PATENTMONITOR_NANTONGJKQ  ");
        map.put("12.0.0.254",list254);

        String driver = "oracle.jdbc.OracleDriver";
        for (Map.Entry entry:map.entrySet()) {
            List<String> list = (List) entry.getValue();
            String url = "jdbc:oracle:thin:@"+entry.getKey()+":1521:orcl";
            String password = "123456";
            for (String db:list) {
                String username = db.trim();
                DataSource dataSource = getDataSource(driver,url,username,password);
                JdbcTemplate jdbcTemplate = getJdbcTemplate(dataSource);
                int count = jdbcTemplate.queryForObject("select 1 from dual",Integer.class);
                //jdbcTemplate.execute("UPDATE PATENT_MAIN set ipc = SUBSTR(ipc, 0, INSTR(ipc, ',',1)-1) where  IPC like '%,%'");
                try {
                    jdbcTemplate.execute("ALTER TABLE PATENT_MAIN MODIFY CLAIM VARCHAR2(2000)");
                    jdbcTemplate.execute("ALTER TABLE PATENT_MAIN MODIFY IPC VARCHAR2(200)");
                } catch (DataAccessException e) {
                    System.out.println("表"+db);
                    e.printStackTrace();
                }

                System.out.println(count);
            }
        }
/*
        String driver1 = "oracle.jdbc.OracleDriver";
        String url1 = "jdbc:oracle:thin:@12.0.0.86:1521:orcl";
        String username1 = "patent_clean_test";
        String password1 = "123456";
        String driver2 = "oracle.jdbc.OracleDriver";
        String url2 = "jdbc:oracle:thin:@12.0.0.88:1521:orcl";
        String username2 = "patentmonitor_jinhua";
        String password2 = "123456";

        try {
            DataSource dataSource1 = getDataSource(driver1,url1,username1,password1);
            JdbcTemplate jdbcTemplate1 = getJdbcTemplate(dataSource1);
            List<String> list1 = jdbcTemplate1.queryForList("select appno from GET_PATENT_MAIN where ",String.class);

            DataSource dataSource2 = getDataSource(driver2,url2,username2,password2);
            JdbcTemplate jdbcTemplate2 = getJdbcTemplate(dataSource2);
            List<String> list2 = jdbcTemplate2.queryForList("select appno from PATENT_MAIN where appno not in (select appno from PATENT_MAIN_ADD) ",String.class);            list1.retainAll(list2);
        } catch (Exception e) {
            e.printStackTrace();
        }

*/
    }
    public static DataSource getDataSource(String driver,String url,String user,String name){
        DataSource dataSource = null;
        try {
            Properties properties = new Properties();
            properties.setProperty(DruidDataSourceFactory.PROP_DRIVERCLASSNAME,driver);
            properties.setProperty(DruidDataSourceFactory.PROP_URL,url);
            properties.setProperty(DruidDataSourceFactory.PROP_USERNAME,user);
            properties.setProperty(DruidDataSourceFactory.PROP_PASSWORD,name);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataSource;
    }
    public static JdbcTemplate getJdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate  = null;
        try {
             jdbcTemplate = new JdbcTemplate(dataSource);
             jdbcTemplate.queryForList("select  1 from dual");
        }catch (Exception e){
            e.printStackTrace();
        }
        return jdbcTemplate;
    }

}
