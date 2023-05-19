package com.interviews.lseg.taskfour.model;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.RoundingMode.HALF_UP;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ElectricityImbalancesMapper {

    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String DATE_DELIMITER = "- ";
    public static final int HEADERS_ROW_INDEX = 5;
    public static final int DATE_ROW_INDEX = 2;

    public ElectricityImbalances parse(byte[] data) {
        try (var workbook = new HSSFWorkbook(new ByteArrayInputStream(data))) {
            var sheet = workbook.getSheetAt(0);
            var date = getDate(sheet);
            var headersMap = getHeadersMap(sheet);
            var dateTimeMap = getDateTimeMap(sheet, date);
            var imbalances = getAllRows(sheet, headersMap.size(), dateTimeMap.size());
            return new ElectricityImbalances(headersMap, dateTimeMap, imbalances);
        } catch (Exception ex) {
            System.out.println("Can't parse electricity imbalances due to: " + ex);
            throw new RuntimeException(ex);
        }
    }

    private static LocalDate getDate(HSSFSheet sheet) {
        var dateCell = sheet.getRow(DATE_ROW_INDEX).getCell(0).getStringCellValue();
        String stringedDate = dateCell.split(DATE_DELIMITER)[1];
        return LocalDate.parse(stringedDate, ofPattern(DATE_PATTERN));
    }

    private static Map<Integer, String> getHeadersMap(HSSFSheet sheet) {
        var headersMap = new HashMap<Integer, String>();
        var headersRow = sheet.getRow(HEADERS_ROW_INDEX);
        int headersRowCounter = 1;
        var cell = headersRow.getCell(headersRowCounter);
        while (cell != null) {
            String columnName = cell.getStringCellValue().split("(\\()")[0].trim();
            headersMap.put(headersRowCounter, columnName);
            headersRowCounter++;
            cell = headersRow.getCell(headersRowCounter);
        }
        return headersMap;
    }

    private static Map<Integer, LocalDateTime> getDateTimeMap(HSSFSheet sheet, LocalDate date) {
        var dateTimeMap = new HashMap<Integer, LocalDateTime>();
        int dateRowCounter = 0;
        var dateRow = sheet.getRow(HEADERS_ROW_INDEX + 1 + dateRowCounter);
        while (dateRow != null) {
            int hour = (int) dateRow.getCell(0).getNumericCellValue() - 1;
            dateTimeMap.put(dateRowCounter, LocalDateTime.of(date, LocalTime.of(hour, 0)));
            dateRowCounter++;
            dateRow = sheet.getRow(HEADERS_ROW_INDEX + 1 + dateRowCounter);
        }
        return dateTimeMap;
    }

    private static List<List<BigDecimal>> getAllRows(HSSFSheet sheet, Integer rowLength, Integer columnLength) {
        var rowsData = new ArrayList<List<BigDecimal>>();
        for (int rowsCounter = 0; rowsCounter < columnLength; rowsCounter++) {
            var row = sheet.getRow(HEADERS_ROW_INDEX + 1 + rowsCounter);
            rowsData.add(getRowsData(row, rowLength));
        }
        return rowsData;
    }

    private static List<BigDecimal> getRowsData(HSSFRow row, Integer rowLength) {
        var rowData = new ArrayList<BigDecimal>();
        for (int column = 1; column <= rowLength; column++) {
            rowData.add(BigDecimal.valueOf(row.getCell(column).getNumericCellValue()).setScale(3, HALF_UP));
        }
        return rowData;
    }
}
