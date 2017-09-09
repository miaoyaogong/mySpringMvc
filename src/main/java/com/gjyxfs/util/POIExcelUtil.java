package com.gjyxfs.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

public class POIExcelUtil {

    public static void readXls() throws IOException {
        InputStream is = new FileInputStream("D:\\excel\\xls_test2.xls");
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }

            // 循环行Row
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }

                // 循环列Cell
                for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
                    HSSFCell hssfCell = hssfRow.getCell(cellNum);
                    if (hssfCell == null) {
                        continue;
                    }

                    System.out.print("    " + getValue(hssfCell));
                }
                System.out.println();
            }
        }
    }

    public static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    //xlsx
    public static Map<String, List<String>> readXlsx(String fileName){

        Map<String, List<String>> map = new HashMap<>();

        //创建workbook
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook( fileName);

            // 循环工作表Sheet
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            if(xssfSheet == null){
                return map;
            }

            List<String> members = new ArrayList<>();
            String groupName = "";
            // 循环行Row
            for(int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++ ){
                XSSFRow xssfRow = xssfSheet.getRow( rowNum);
                if(xssfRow == null){
                    continue;
                }
                // 循环列Cell
                for(int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++){
                    XSSFCell xssfCell = xssfRow.getCell( cellNum);
                    if(xssfCell == null){
                        continue;
                    }
                    if(cellNum == 0 && !StringUtils.isEmpty(getsValue(xssfCell))){
                        if(!StringUtils.isEmpty(groupName) && !groupName.equals(getsValue(xssfCell))){
                            map.put(groupName, members);
                            members = new ArrayList<>();
                        }
                        groupName = getsValue(xssfCell);
                    }else{
                        members.add(getsValue(xssfCell));
                    }
                }
            }
            map.put(groupName, members);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(xssfWorkbook != null){
                try {
                    xssfWorkbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
    public  static  void main(String[] args){
        String fileName = "C:\\Users\\1\\Desktop\\1.xlsx";
            System.out.print(JsonMapper.getInstance().toJson(readXlsx(fileName)));
    }
    public static String getsValue(XSSFCell xssfCell){
        if(xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN){
            return String.valueOf( xssfCell.getBooleanCellValue());
        }else if(xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC){
            return String.valueOf( xssfCell.getNumericCellValue());
        }else{
            return String.valueOf( xssfCell.getStringCellValue());
        }
    }

}