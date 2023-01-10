package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Code {

	public static String notPresentInCSV;
	public static String notPresentInExcel;

	
	static ArrayList<ArrayList<String>> result = new ArrayList<>();

	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Map<String, String> getTimeTypeData() throws Throwable {
		Properties prop = PropertiesFileUtility.readPropertiesFile();

		String timeTypeDataSheetPath = PropertiesFileUtility.prop.getProperty("TimeTypeDataSheetPath");
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
				leaveType.put(sheet.getRow(i).getCell(0).getStringCellValue(),
						sheet.getRow(i).getCell(1).getStringCellValue());
			}
		}
		return leaveType;
	}

	public static void compareXcelData() throws Throwable {
		Map<String, String> leaveType = getTimeTypeData();
		CSVUtility csvUtility = new CSVUtility();
		Map<String, List<EmployeeDetails>> csvDataMap = csvUtility.getCSVData();
		ExcelUtility xl = new ExcelUtility();
		xl.openWorkBook1();
		Map<String, HashMap<Integer, String>> excelDataMap = xl.getExcelSheetData();

		Set<String> empIdInExcelSet = excelDataMap.keySet();
		Set<String> empIdInCSVSet = csvDataMap.keySet();

		notPresentInCSV = "Following employee Id are present in excel but not in CSV file = ";
		for (String empIdInExcel : empIdInExcelSet) {
			if (!empIdInCSVSet.contains(empIdInExcel)) {
				notPresentInCSV = notPresentInCSV.concat(empIdInExcel + ", ");
			}
		}

		notPresentInExcel = "Following employee Id are present in CSV but not in excel file = ";
		for (String empIdInCSV : empIdInCSVSet) {
			if (!empIdInExcelSet.contains(empIdInCSV)) {
				if (!empIdInCSV.equals("Employee - Employee Prism Code")) {
					notPresentInExcel = notPresentInExcel.concat(empIdInCSV + ", ");
				}
			}
		}
		System.out.println(notPresentInExcel);

		for (Map.Entry<String, List<EmployeeDetails>> stringListEntry : csvDataMap.entrySet()) {

			String empId = stringListEntry.getKey();
			HashMap<Integer, String> excelDataHashMap = excelDataMap.get(empId);
			List<EmployeeDetails> employeeDetails = stringListEntry.getValue();
			if (excelDataHashMap == null)
				continue;
			for (EmployeeDetails employeeDetails1 : employeeDetails) {
				ArrayList<String> list = new ArrayList<>();

				String sDate1 = employeeDetails1.getDate();
				Date date1 = new Date();
				try {
					date1 = new SimpleDateFormat("dd-MMM-yy").parse(sDate1);
				} catch (Exception e) {
					System.out.println("Date format exception");
					e.printStackTrace();
				}
				LocalDate localDate = convertToLocalDateViaInstant(date1);
				String leaveTyp = excelDataHashMap.get(Integer.valueOf(localDate.getDayOfMonth()));

				if (leaveTyp != null) {
					try {
						if (leaveTyp != null && leaveType.get(employeeDetails1.getTimeType()).contains(leaveTyp)) {
							list.add("Equal");
							list.add(empId);
							list.add(sDate1);
							list.add("\"" + leaveTyp + "\"");
							list.add("\"" + employeeDetails1.getTimeType() + "\"");
							System.out.println("Equal for date " + employeeDetails1.getDate() + " empId is " + empId
									+ " Leave type in excel = \"" + leaveTyp + "\" Leave type in csv = \""
									+ employeeDetails1.getTimeType() + "\"");
						} else {
							list.add("Not Equal");
							list.add(empId);
							list.add(sDate1);
							list.add(leaveTyp);
							list.add(employeeDetails1.getTimeType());
							System.err.println(" Not equal date is = " + employeeDetails1.getDate() + " and empId is "
									+ empId + employeeDetails1.getEmpPrismCode() + " leaveType in excel is " + leaveTyp
									+ " and leaveType in csv is \"" + employeeDetails1.getTimeType() + "\"");
						}
					} catch (Exception e) {
						list.add("Error");
						list.add(empId);
						list.add(sDate1);
						list.add("\"" + leaveTyp + "\"");
						list.add("\"" + employeeDetails1.getTimeType() + "\"");
						System.out.println(empId + " " + employeeDetails1.getDate()
								+ " Some issue occurred!!!! leaveType in excel is " + leaveTyp
								+ " and leaveType in csv is " + employeeDetails1.getTimeType());
						System.err.println("Please add " + employeeDetails1.getTimeType() + " ");
					}
				}
				result.add(list);
			}
		}
		System.out.println(result);
	}

	public static Cell changeCellBackgroundColor(Cell cell, String indexedColourCode) throws Exception {
		CellStyle cellStyle = cell.getCellStyle();
		cellStyle = cell.getSheet().getWorkbook().createCellStyle();

		switch (indexedColourCode) {
		case "GREY":
			cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			break;

		case "ORANGE":
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());

			break;

		default:
			System.out.println("Enter correct cell style");
		}
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(cellStyle);
		return cell;
	}

	public static void writeResult() throws Throwable {

		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet resultSheet = wb.createSheet("ReultSheet");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(
					PropertiesFileUtility.prop.getProperty("RESULT_DIRECTORY_PATH") + "\\ResultSheet.xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		changeCellBackgroundColor(resultSheet.createRow(0).createCell(0, CellType.STRING), "ORANGE")
				.setCellValue(notPresentInCSV);
		changeCellBackgroundColor(resultSheet.createRow(1).createCell(0, CellType.STRING), "ORANGE")
				.setCellValue(notPresentInExcel);

		changeCellBackgroundColor(resultSheet.createRow(2).createCell(0, CellType.STRING), "GREY")
				.setCellValue("Condition");
		changeCellBackgroundColor(resultSheet.getRow(2).createCell(1, CellType.STRING), "GREY")
				.setCellValue("Employee Id");
		changeCellBackgroundColor(resultSheet.getRow(2).createCell(2, CellType.STRING), "GREY").setCellValue("Date");
		changeCellBackgroundColor(resultSheet.getRow(2).createCell(3, CellType.STRING), "GREY")
				.setCellValue("Leave type in maveric sheet");
		changeCellBackgroundColor(resultSheet.getRow(2).createCell(4, CellType.STRING), "GREY")
				.setCellValue("Leave type in OA sheet");
		int rowIdx = 3;
		for (ArrayList<String> oneEntry : result) {
			Row currentRow = resultSheet.createRow(rowIdx++);
			int colIdx = 0;
			for (String field : oneEntry) {
				if (oneEntry.get(0).equals("Equal")) {
					Cell currentCell = currentRow.createCell(colIdx++, CellType.STRING);
					currentCell.setCellValue(field);
				} else {
					Cell currentCell = currentRow.createCell(colIdx++, CellType.STRING);
					changeCellBackgroundColor(currentCell, "ORANGE").setCellValue(field);
				}
			}
		}
		wb.write(fos);
		fos.close();
	}

	public static void main(String[] args) throws Throwable {
		compareXcelData();
		writeResult();
	}

}
