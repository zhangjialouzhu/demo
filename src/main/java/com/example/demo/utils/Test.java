package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;

public class Test {
    public static void test(){
        Sheet sheet = ExcelUtil.getSheet("E:\\IdeaProjects\\demo\\src\\main\\resources\\excel\\1.xls",1);
        int rowNum = sheet.getPhysicalNumberOfRows();
        String type = "";
        String type1 = "";

        for(int i=0;i<rowNum;i++) {
            //获取列数
            Row row = sheet.getRow(i);
            int max = row.getPhysicalNumberOfCells();

            for (int j = 0; j < max; j++) {
                row.getCell(j).setCellType(CellType.STRING);
            }

            if(StringUtils.isNotEmpty(row.getCell(0).getStringCellValue())){
                type = row.getCell(0).getStringCellValue();
            }if(StringUtils.isNotEmpty(row.getCell(1).getStringCellValue())){
                type1 = row.getCell(1).getStringCellValue();
            }

            String cell3 = row.getCell(2).getStringCellValue();
            Map<String,List<String>> result = sub(getStr(cell3));
            for (String key:result.keySet()){
                List<String> temp = result.get(key);
                if(temp.size()>0){
                    for (int j=0;j<temp.size();j++){
                        StringBuffer buffer = new StringBuffer("insert into EMERGING_INDUSTRIES (TYPE,TYPE_NAME,IPC,IPC_CHILDREN,REMARK) values (");
                        buffer.append("'").append(type).append("','").append(type1).append("',");
                        buffer.append("'").append(key).append("','").append(temp.get(j)).append("',");
                        String cell4 = row.getCell(3).getStringCellValue();
                        buffer.append("'").append(cell4).append("');");
                        System.out.println(buffer);
                    }
                }else {
                    StringBuffer buffer = new StringBuffer("insert into EMERGING_INDUSTRIES (TYPE,TYPE_NAME,IPC,IPC_CHILDREN,REMARK) values (");
                    buffer.append("'").append(type).append("','").append(type1).append("',");
                    buffer.append("'").append(key).append("','").append("',");
                    String cell4 = row.getCell(3).getStringCellValue();
                    buffer.append("'").append(cell4).append("');");
                    System.out.println(buffer);
                }
            }



        }
    }
    //"G01S1*(不含G01S1/02)、G06F1*(不含G06F1/16)、G08C19*、H04B1*、H04B3*(不含H04B3/54)、H04B5*、H04B7*(不含H04B7/14、H04B7/15、H04B7/155、H04B7/185、H04B7/19、H04B7/195、H04B7/204、H04B7/212、H04B7/216、H04B7/26)、H04B11*、H04B13*、H04B14*、H04B15*、H04B17*、H04H20*、H04J1*、H04J3*(不含H04J3/06)、H04J4*、H04J7*、H04J9*、H04J11*、H04J13*、H04J14*(不含H04J14/02)、H04L12*(不含H04L12/02、H04L12/24、H04L12/28、H04L12/46、H04L12/66)、H04L27/26、H04L29*(不含H04L29/06、H04L29/08)、H04Q3*、H04Q5*、H04Q9*、H04Q11*(不含H04Q11/00)、H04W36*、H04W68*、H04W88*、H04W92*"
    public static void main(String[] args) {
        test();

    }
    public static List<String> sub(String str,List<String> result){
        String temp = str.substring(str.indexOf("("),str.indexOf(")")+1);
        str = str.replace(temp,"");
        result.add(temp);
        if(str.contains("(")){
            sub(str,result);
        }
        return result;
    }
    public static String getStr(String str){
        if(str.contains("(")){
            List<String> result = new ArrayList<>();
            result = sub(str,result);
            for (int i = result.size() - 1; i >= 0; i--) {
                str=str.replace(result.get(i),result.get(i).replace("、",",").replace("不含",""));
            }
        }
        return str;
    }

    public static Map<String,List<String>> sub(String str){
      Map<String,List<String>> map = new TreeMap<>();
      str= str.replace("*","%");
      String[] cons  =  str.split("、");
      for (String key:cons){
          if(key.contains("(")){
              String tempKey = key.substring(key.indexOf("("));
              key = key.substring(0,key.indexOf("("));
              List<String> temp = Arrays.asList(tempKey.replace("(","").replace(")","").split(","));
              map.put(key,temp);
          }else {
              map.put(key,new ArrayList<>());
          }
      }
      return map;
    }
    public static Map<String,String> sub1(String str){
        Map<String,String> map = new TreeMap<>();
        String[] cons  =  str.split("、");
        for (String key:cons){
            if(key.contains("(")){
                String tempKey = key.substring(key.indexOf("("));
                key = key.substring(0,key.indexOf("("));
                tempKey= tempKey.replace("(","").replace(")","");
                map.put(key,tempKey);
            }else {
                map.put(key,"");
            }
        }
        return map;
    }

}
