package com.example.demo.utils;

import com.example.demo.bean.ExcelColumn;
import com.example.demo.bean.SqlBean;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLUtil {
    public static String createSql(SqlBean sqlBean) throws IOException {



        return "";
    }
    public static void readLine(String fileName,String newFileName) throws IOException {
        BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
        String line="";
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(newFileName));
        while ((line=bufferedReader.readLine())!=null) {
            line = "insert into TEMP_APPL (name) values ('"+line+"');\n";
            bufferedWriter.write(line);
        }
        bufferedReader.close();
        bufferedWriter.close();
    }
    public static void readLine(String fileName,String fileName2,String newFileName) throws IOException {
        BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
        BufferedReader bufferedReader2=new BufferedReader(new FileReader(fileName2));
        String line="";
        String line2 = "";
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(newFileName));
        while ((line=bufferedReader.readLine())!=null) {

        }
        bufferedReader.close();
        bufferedWriter.close();
    }
    public static void writeSql(String newFileName,String tableName,Map<String,Object> columns,List<Map<String,Object>> rows) throws IOException {
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(newFileName));
        StringBuffer sqlTemp = new StringBuffer()
                .append("insert into ")
                .append(tableName)
                .append(" (");
        for (String column:columns.keySet()) {
            sqlTemp.append(column).append(",");
        }
        sqlTemp.deleteCharAt(sqlTemp.length()-1);
        sqlTemp.append(") values (");
        for (Map<String,Object> row:rows) {
            if(isBlank(row)){
                continue;
            }
            StringBuffer sql = new StringBuffer(sqlTemp);
            for (String column:columns.keySet()) {
                if(ExcelColumn.class.equals(columns.get(column).getClass())){
                    sql.append(" '")
                        .append(row.get(column))
                        .append("',");
                }else {
                    sql.append(" '")
                        .append(columns.get(column))
                        .append("',");
                }

            }
            sql.deleteCharAt(sql.length()-1);
            sql.append(");\n");
            System.out.println(sql);
            bufferedWriter.write(sql.toString());
        }

        bufferedWriter.close();
    }

    public  static boolean isBlank(Map map){
        boolean isBlank = true;
        for(Object o:map.values()){
            if(o!=null&&!"".equals(o.toString())){
                isBlank = false;
                continue;
            }
        }
        return isBlank;
    }

    //测试方法
    public static void main(String[] args) throws Exception{

        String excelPath = "E:\\IdeaProjects\\demo\\src\\main\\resources\\excel\\ad.xls";
        String tableName = "TEMP_ADDRESS";
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("ADDRESS",new ExcelColumn(1));
        /*columnMap.put("TIME",new ExcelColumn(3));
        columnMap.put("APPL",new ExcelColumn(4));
        columnMap.put("FEE_TYPE",new ExcelColumn(5));*/
        //columnMap.put("AREA","金东区");
        List<Map<String,Object>> rows = ExcelUtil.readSqlData(excelPath,1,0,columnMap);

        writeSql("E:\\IdeaProjects\\demo\\src\\main\\resources\\sql\\"+tableName+".sql",tableName,columnMap,rows);
        System.out.println("ok");
    }
}
