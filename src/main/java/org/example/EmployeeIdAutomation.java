package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EmployeeIdAutomation {
	
	public static ArrayList<ArrayList<String>> bookData;
	
	public static void addResultInExcel(ArrayList<String> list) {
		bookData = new ArrayList<>();

	}


    public static void main(String[] args) throws Throwable {
        Properties prop = PropertiesFileUtility.readPropertiesFile();
        System.out.println(prop.getProperty("LAST_DATE_END"));
        ;

    }


    public static void getTimeTypeData() throws Throwable {
        String timeTypeDataSheetPath = "C:\\Users\\c.abhishektk\\Documents\\Timesheet Automation\\TimeTypeDataSheet.xlsx";
        File file = new File(timeTypeDataSheetPath);
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheet("Sheet1");
        int rowCount = sheet.getLastRowNum();
        Map<String, String> leaveType = new LinkedHashMap<>();
        for (int i = 1; i <= rowCount; i++) {
            if (sheet.getRow(i).getLastCellNum() == 1) {
                leaveType.put(sheet.getRow(i).getCell(0).getStringCellValue(), "");
            } else {
                leaveType.put(sheet.getRow(i).getCell(0).getStringCellValue(), sheet.getRow(i).getCell(1).getStringCellValue());
            }
        }
        System.out.println(leaveType);
    }

     /*for(int j = 0 ; j < sheet.getRow(i).getLastCellNum() ; j++){
                System.out.print(sheet.getRow(i).getCell(j)+" ");
                if(i%2==0){
                    if(sheet.getRow(i).getLastCellNum()==1){

                    }
                }
            }
            System.out.println();*/


}
/*
* package org.example;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Code {

    public static void compareXcelData() throws Throwable{
        Map<String, String> leaveType = new LinkedHashMap<>();
        leaveType.put("Billable - Regular","WH");
        leaveType.put("Holiday - Weekend", "");
        leaveType.put("Half Day", "0.5");
        leaveType.put("Leave", "PL");
        leaveType.put("Leave", "SL");
        leaveType.put("Comp Off", "PL");
        leaveType.put("Weekly OFF", "");
        CSVUtility csvUtility = new CSVUtility();
        Map<String, List<EmployeeDetails>> csvDataMap = csvUtility.getCSVData();
        ExcelUtility xl = new ExcelUtility();
        xl.openWorkBook1();
        Map<String, HashMap<Integer, String>> excelDataMap = xl.getExcelSheetData();

        for (Map.Entry<String, List<EmployeeDetails>> stringListEntry : csvDataMap.entrySet()) {

           String empId = stringListEntry.getKey();
           HashMap<Integer, String> excelDataHashMap =  excelDataMap.get(empId);
           List<EmployeeDetails> employeeDetails = stringListEntry.getValue();

            for (EmployeeDetails employeeDetails1: employeeDetails) {
                String sDate1 = employeeDetails1.getDate();
                Date date1 = new SimpleDateFormat("dd-MMM-yy").parse(sDate1);
                LocalDate localDate = convertToLocalDateViaInstant(date1);

                String leaveTyp = excelDataHashMap.get(Integer.valueOf(localDate.getDayOfMonth()));

                if (leaveTyp != null) {
                    try {
                        if (leaveTyp != null && leaveType.get(employeeDetails1.getTimeType()).equalsIgnoreCase(leaveTyp)) {
                            //System.out.println("Equal for date " + employeeDetails1.getDate() + " empId is " + empId);
                        } else {
                            System.out.println(" Not equal date is = " +employeeDetails1.getDate() +" and empId is "+ empId + employeeDetails1.getEmpPrismCode() +" leaveType in excel is " + leaveTyp + " and leaveType in csv is " + employeeDetails1.getTimeType());
                        }
                    } catch (Exception e) {
                       System.out.println("Some issue occurred!!!! leaveType in excel is " + leaveTyp + " and leaveType in csv is " + employeeDetails1.getTimeType());

                    }
                }
            //}
            }
        }
        //xl.makeResultDirectory();

       /* for (Map.Entry<String, List<EmployeeDetails>> set : csvDataMap.entrySet()) {
            String empName = set.getKey();
            for(EmployeeDetails empDetails : set.getValue()){
                String dateInCSV = empDetails.getDate().substring(0,2);
                if(dateInCSV.startsWith("0")){
                    dateInCSV = dateInCSV.replaceAll("0","");
                }
                System.out.println(dateInCSV);

                System.out.println(empDetails.getTimeType());

                switch(empDetails.getTimeType()) {
                    case "Billable - Regular":
                    case "Billable - Weekly OFF":
                    case "Comp Off":
                    case "Holiday - Public/Client":
                    case "Holiday - Weekend":
                    case "Leave":
                    case "LOP":
                    case "Non-Billable - Regular":
                    case "Non-Billable - Weekly Off":


                }
            }
            break;
        }


    }
public static void main(String[] args) throws Throwable {
        compareXcelData();
        }




public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }


        }
        */