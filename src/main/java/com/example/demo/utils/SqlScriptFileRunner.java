package com.example.demo.utils;
 
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
 
public class SqlScriptFileRunner {
    private static String dbHost="127.0.0.1";               // 数据库地址
    private static String dbName="test";                      // 数据库名称
    private static String userName = "test";                // 登录名称
    private static String userPassword = "test111";         // 登录密码
    private static String dbPort="3306";                      // 数据库端口号
 
    public static void main(String[] args) {
        try {
            Connection conn =getMySqlConnection();
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(Charset.forName("UTF-8")); //设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);//设置是否输出日志
            // 绝对路径读取
//          Reader read = new FileReader(new File("f:\\test.sql"));
            // 从class目录下直接读取
            Reader read = Resources.getResourceAsReader("test.sql");
            runner.runScript(read);
            runner.closeConnection();
            conn.close();
            System.out.println("sql脚本执行完毕");
        } catch (Exception e) {
            System.out.println("sql脚本执行发生异常");
            e.printStackTrace();
        }
    }
 
    /**
     * @功能描述：   获取数据库连接
     *
     * @return
     * @throws Exception 
     */
    public static Connection getMySqlConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url="jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?useUnicode=true&characterEncoding=utf-8&port="+dbPort+"&autoReconnect=true";
        //return DriverManager.getConnection(url,userName,userPassword);
        return SqlScriptLineRunner.getDataSource("","","","").getConnection();
    }
}