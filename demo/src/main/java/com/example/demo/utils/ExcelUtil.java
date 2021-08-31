package com.example.demo.utils;

import com.example.demo.bean.ExcelColumn;
import com.example.demo.configs.ExportConfig;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    private Sheet sheet;
    private Row row;

    /**
     * 构造函数，初始化excel数据
     * @param filePath  excel路径
     * @param sheetName sheet表名
     */
    ExcelUtil(String filePath, String sheetName){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            sheet = sheets.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Sheet getSheet(String filePath, int sheet){
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(filePath);
            if (filePath.indexOf(".xlsx") != -1) {
                XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
                return wb.getSheetAt(sheet-1);
            } else {
                HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);
                return wb.getSheetAt(sheet-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 根据行和列的索引获取单元格的数据
     * @param row
     * @param column
     * @return
     */
    public String getExcelDateByIndex(int row,int column){
        Row row1 = sheet.getRow(row);
        String cell = row1.getCell(column).toString();
        return cell;
    }

    /**
     * 根据某一列值为“******”的这一行，来获取该行第x列的值
     * @param caseName
     * @param currentColumn 当前单元格列的索引
     * @param targetColumn 目标单元格列的索引
     * @return
     */
    public String getCellByCaseName(String caseName,int currentColumn,int targetColumn){
        String operateSteps="";
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for(int i=0;i<rows;i++){
            Row row = sheet.getRow(i);
            String cell = row.getCell(currentColumn).toString();
            if(cell.equals(caseName)){
                operateSteps = row.getCell(targetColumn).toString();
                break;
            }
        }
        return operateSteps;
    }

    /**
     * @param filePath 文件名
     * @param sheet sheet
     * @param n 第n行开始
     * @param columnMap 需要哪些列的数据
     * @returnn
     */
    public static List<Map<String,Object>> readSqlData(String filePath, int sheet,int n, Map<String,Object> columnMap) throws Exception{
        List<Map<String,Object>> rows = new ArrayList<>();
        //获取行数
        int rowNum = getSheet(filePath,sheet).getPhysicalNumberOfRows();
        for(int i=n-1;i<rowNum;i++){
            //获取列数
            Row row = getSheet(filePath,sheet).getRow(i);
            if(row==null)
                continue;
            int max = row.getPhysicalNumberOfCells();
            // (列1, 列2,...) VALUES (值1, 值2,....)
            Map<String,Object> columns = new HashMap<>();

            for (String  key:columnMap.keySet()) {
                if(ExcelColumn.class.equals(columnMap.get(key).getClass())){
                    int col = ((ExcelColumn) columnMap.get(key)).column;
                    if(col>max)
                        continue;
                    if(row.getCell(col)==null){
                        columns.put(key,"");
                        continue;
                    }
                    row.getCell(col).setCellType(CellType.STRING);
                    String cell = row.getCell(col).getStringCellValue();
                    columns.put(key,cell);
                }
            }
            rows.add(columns);
        }
        return rows;
    }
    public static Map<String,List<String>> readExcelData(String filePath,int sheetNum,int skip) throws Exception{
        Map<String,List<String>> rows = new HashMap<>();
        //获取行数
        Sheet sheet = getSheet(filePath,sheetNum);
        int rowNum = sheet.getPhysicalNumberOfRows();
        for(int i=skip-1;i<rowNum;i++){
            //获取列数
            Row row = sheet.getRow(i);
            if(row==null)
                continue;
            String key = row.getCell(0).getStringCellValue();
            List<String> list = new ArrayList<>();
            for (int j = 1; j < row.getLastCellNum(); j++) {
                if(row.getCell(j)!=null)
                    row.getCell(j).setCellType(CellType.STRING);
                String cell = row.getCell(j)==null?"0":row.getCell(j).getStringCellValue();
                list.add(cell);
            }
            while (list.size()<3){
                list.add("0");
            }
            int sum = 0;
            for (String str:list) {
                sum = sum+Integer.parseInt(str);
            }
            List<String> newList = new ArrayList<>();
            newList.add(Integer.toString(sum));
            newList.addAll(list);
            rows.put(key,newList);
        }
        return rows;
    }

    public static List<Map<String,List<String>>> readExcels(int line,String ... filePaths) throws Exception{
        List<Map<String,List<String>>> list = new ArrayList<>();
        for (String filePath:filePaths) {
            Map<String,List<String>> map=readExcelData(filePath,1,line);
            list.add(map);
        }
        return list;
    }
    public static Map<String,List<String>> getResult(List<Map<String,List<String>>> list) throws Exception{
        Map<String,List<String>> map = list.get(0);
        int count = list.size();
        list.remove(0);
        int num = 1;
        for (Map<String,List<String>> temp:list) {
            for(String cell:temp.keySet()){
                List<String> tempList;
                if(map.containsKey(cell)){
                    tempList = map.get(cell);
                }else {
                    tempList = new ArrayList<>();
                    for (int i = 0; i <= num*4; i++) {
                        tempList.add("0");
                    }
                }
                tempList.addAll(temp.get(cell));
            }
            num++;
        }
        Map<String,List<String>> result = new HashMap<>();
        for (String key:map.keySet()) {
            List<String> temp = map.get(key);
            while(temp.size()<count*4){
                temp.add("0");
            }
            result.put(key,temp);
        }
        System.out.println(result);
        return result;
    }
    public static void writeExcel(String filePath,Map<String,List<String>> map) throws Exception{
        //获取行数
        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
        HSSFWorkbook sheets = new HSSFWorkbook();
        HSSFSheet sheet = sheets.createSheet();
        int i = 0;
        for (String temp:map.keySet()){
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(temp);
            int j=1;
            for(String cell:map.get(temp)){
                row.createCell(j).setCellValue(cell);
                j++;
            }

            i++;
        }

        sheets.write(fileOutputStream);
        fileOutputStream.close();
        sheets.close();
    }

    public static void writeExcel(HttpServletResponse response, String filePath, List<Map<String, Object>> list) throws Exception{
        //获取行数
        OutputStream outputStream = response.getOutputStream();
        HSSFWorkbook sheets = new HSSFWorkbook();
        HSSFSheet sheet = sheets.createSheet();

        for (int i = 0; i <list.size() ; i++) {
            Map<String,Object> map = list.get(i);
            int j = 0;
            HSSFRow row = sheet.createRow(i);
            for (Map.Entry<String, Object> temp:map.entrySet()){
                System.out.println(temp.getValue());
                row.createCell(j).setCellValue(temp.getValue()==null?"":temp.getValue().toString());
                j++;
            }
        }
        ExportConfig.setResponseHeader(response,filePath);
        sheets.write(outputStream);
        outputStream.flush();
        outputStream.close();
        sheets.close();
    }


    public static void main(String[] args) {
        try {
            List<Map<String,List<String>>> list = readExcels(2,"E:\\IdeaProjects\\demo\\src\\main\\resources\\excel\\申请.xls","E:\\IdeaProjects\\demo\\src\\main\\resources\\excel\\授权.xls","E:\\IdeaProjects\\demo\\src\\main\\resources\\excel\\有效.xls");
            Map<String,List<String>> map = getResult(list);
            writeExcel("E:\\IdeaProjects\\demo\\src\\main\\resources\\excel\\test.xls",map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
