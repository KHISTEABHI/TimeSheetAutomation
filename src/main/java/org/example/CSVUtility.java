package org.example;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVUtility {


    public String CSVFilePath = "C:\\Users\\c.abhishektk\\Documents\\Timesheet Automation\\OA_Time_Sheet__2022_Timesheet_RBS__CS_Macro_report (80).csv";
    CSVReader csvReader;
    FileReader filereader;
    CSVParser parser;

    public Map<String, List<EmployeeDetails>> getCSVData() throws Throwable {
        try {
            filereader = new FileReader(new File(CSVFilePath));
            parser = new CSVParserBuilder().withSeparator('=').build();
            csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String[]> allData = csvReader.readAll();
        int count = 0;
        Map<String, List<EmployeeDetails>> stringEmployeeDetailsMap = new HashMap<>();

        for (String[] row : allData) {
            count++;
            for (String cell : row) {
                List<EmployeeDetails> employeeDetails = new ArrayList<>();
                String[] str = cell.toString().replaceAll(", ", " ").replaceAll("\"", "").split(",");

                if (count != Integer.valueOf(PropertiesFileUtility.prop.getProperty("Unwanted_Lines"))) {
                    EmployeeDetails employeeDetails1 = new EmployeeDetails();
                    employeeDetails1.setEmpPrismCode(str[0]);
                    employeeDetails1.setEmpName(str[1]);
                    employeeDetails1.setResourceType(str[2]);
                    employeeDetails1.setEmpPositionCode(str[3]);
                    employeeDetails1.setEmpDesignation(str[4]);
                    employeeDetails1.setTimesheetName(str[5]);
                    employeeDetails1.setAccountingPeriod(str[6]);
                    employeeDetails1.setApprovalStatus(str[7]);
                    employeeDetails1.setOracleProjectCode(str[8]);
                    employeeDetails1.setProject(str[9]);
                    employeeDetails1.setClientOracleCode(str[10]);
                    employeeDetails1.setBillingType(str[11]);
                    employeeDetails1.setClient(str[12]);
                    employeeDetails1.setClientGroup(str[13]);
                    employeeDetails1.setDate(str[14]);
                    employeeDetails1.setTimeType(str[15]);
                    employeeDetails1.setDeliveryPremise(str[16]);
                    employeeDetails1.setTimeDays(str[17]);
                    employeeDetails1.setYourCurrentWorkLocation(str[18]);
                    employeeDetails1.setTask(str[19]);
                    employeeDetails1.setNotes(str[20]);
                    employeeDetails1.setProjectRegion(str[21]);
                    employeeDetails1.setProjectEngagementType(str[22]);
                    employeeDetails1.setProjectCategory(str[23]);
                    employeeDetails1.setProjectEndDate(str[24]);
                    employeeDetails1.setProjectCostcenter(str[25]);
                    employeeDetails1.setEmpBUHierarchyNode(str[26]);
                    employeeDetails1.setProjectProjectBUHierarchyNode(str[27]);
                    employeeDetails1.setDeliveryManager(str[28]);
                    employeeDetails1.setEmpTimeSheetsApprover(str[29]);
                    employeeDetails1.setTimesheetCreated(str[30]);
                    employeeDetails1.setTimesheetSubmittedDate(str[31]);
                    employeeDetails1.setTimesheetApprovedDate(str[32]);
                    employeeDetails1.setEmpResignationDate(str[33]);
                    employeeDetails1.setClientOracleCode(str[34]);
                    employeeDetails.add(employeeDetails1);

                    if (stringEmployeeDetailsMap.get(employeeDetails1.getEmpPrismCode()) != null) {
                        stringEmployeeDetailsMap.get(employeeDetails1.getEmpPrismCode()).add(employeeDetails1);
                    } else {
                        stringEmployeeDetailsMap.put(employeeDetails1.getEmpPrismCode(), employeeDetails);
                    }
                }
            }
        }

        return stringEmployeeDetailsMap;
    }

     /* public void readCSVFile(){
        try {
            filereader = new FileReader(new File(CSVFilePath));
            parser = new CSVParserBuilder().withSeparator('=').build();
            csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
