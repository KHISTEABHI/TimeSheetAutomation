package org.example;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExcelUtility {
    public static String File_Name1 = "C:\\Users\\c.abhishektk\\Documents\\Timesheet Automation\\Maveric Timesheet_Nov 2022.xlsx";
    Row headerRowSheet1;
    FileInputStream fis1;
    XSSFWorkbook workbook1;
    XSSFSheet sheet1;


    public void makeResultDirectory() throws Throwable {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-YY HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now).toString());
        String resultDirectoryPath = "D:\\" + dtf.format(now).toString();
        File theDir = new File(resultDirectoryPath);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        FileOutputStream fos = new FileOutputStream(resultDirectoryPath + "\\result.xlsx");
    }

    public Map<String, HashMap<Integer, String>> getExcelSheetData() throws Throwable {
        Properties prop = PropertiesFileUtility.prop;

        XSSFSheet xlSheet = openWorkBook1();

        Map<String, HashMap<Integer, String>> empAttendaceDetails = new HashMap<>();

        for (int i = Integer.valueOf(prop.getProperty("ROW_STARTS_FROM"))-1; i < Integer.valueOf(prop.getProperty("ROW_END_AT")); i++) {
            HashMap<Integer, String> rowData = new HashMap<>();
            HashMap<Integer, String> integerStringHashMap = new HashMap<>();
            int count = 0;
            for (int k = 6; k < 36; k++) {
                count++;
                integerStringHashMap.put(count, getCellValueFromSheet1(i, k));
            }
            String empId = getCellValueFromSheet1(i, 1);
            empAttendaceDetails.put(empId.contains(".") ? empId.substring(0, empId.indexOf(".")) : empId, integerStringHashMap);
        }
        return empAttendaceDetails;

    }

    public XSSFSheet openWorkBook1() {
        try {
            fis1 = new FileInputStream(new File(File_Name1));
            workbook1 = new XSSFWorkbook(fis1);
            sheet1 = workbook1.getSheet("Sheet1");
            return sheet1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheet1;
    }


    public String getCellValueFromSheet1(int row, int col) {
        String cellValue = "";
        XSSFRow curRow = sheet1.getRow(row);
        XSSFCell cell = curRow.getCell(col);

        if (cell != null) {
            CellType type = cell.getCellType();

            switch (type) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;

                case NUMERIC:
                    cellValue = "" + cell.getNumericCellValue();
                    break;

                case _NONE:
                    cellValue = "";
                    break;

                case ERROR:
                    cellValue = cell.getErrorCellString();
                    break;
            }
        }
        return cellValue;
    }

   /* public void selectSheet(){

    }

    public int getIndexOfFromHeader1(String field){
        Iterator<Cell> cellIterator1 = headerRowSheet1.cellIterator();
        int idx = 0;
        while(cellIterator1.hasNext()){
            if(cellIterator1.next().equals(field)){
                return idx;
            }
            idx++;
        }
        return -1;
    }
*/
    /*public Row getHeaderRowFromXcel(){
        Iterator<Row> rowItrSheet1 = sheet1.iterator();
        String cellValue = "";

        while(rowItrSheet1.hasNext()){
            Row row = rowItrSheet1.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                if(cell!=null){
                    CellType type = cell.getCellType();
                    switch (type){
                        case STRING :
                            cellValue=cell.getStringCellValue();
                            if(cellValue.equals("Sl. No.")){
                                return row;
                            }
                    }
                }
            }

        }
        return  null;
    }*/

}