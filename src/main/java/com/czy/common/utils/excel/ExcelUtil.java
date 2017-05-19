package com.czy.common.utils.excel;

import com.czy.common.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;

/**
 * Created by Comup on 2017-05-16.
 * Excel工具类
 */
public final class ExcelUtil<T> {

    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    //    public static <T, K> Collector<T, ?, Map<K, List<T>>>
//    groupingBy(Function<? super T, ? extends K> classifier) {
//        return groupingBy(classifier, toList());
//    }
    public void insertToExcel(List<T> list, Class<?> entityType, Path excel, ExcelType excelType, Collector<T, ?, Map<String, List<T>>> collector) throws IOException, IllegalAccessException {
        if (list == null || list.size() == 0 || list.get(0) == null) {
            throw new IllegalArgumentException("list may not be null or empty");
        }
        if (entityType == null) {
            throw new IllegalArgumentException("entityType may not be null");
        }
        if (!entityType.isInstance(list.get(0))) {
            throw new IllegalArgumentException("list type not mach entityType");
        }
        if (collector != null) {
            Map<String, List<T>> grouped = list.stream().collect(collector);
            Set<String> strings = grouped.keySet();
            for (String key : strings) {
                insertToExcel(grouped.get(key), entityType, excel, excelType, key);
            }
        } else {
            insertToExcel(list, entityType, excel, excelType, yyyyMMdd.format(LocalDate.now()));
        }
    }

    private synchronized void insertToExcel(List<?> list, Class<?> entityType, Path excel, ExcelType excelType, String sheetName) throws IOException, IllegalAccessException {
        if (list == null || list.size() == 0 || list.get(0) == null) {
            throw new IllegalArgumentException("list may not be null or empty");
        }
        if (entityType == null) {
            throw new IllegalArgumentException("entityType may not be null");
        }
        if (!entityType.isInstance(list.get(0))) {
            throw new IllegalArgumentException("list type not mach entityType");
        }
        if (StringUtil.isNullOrEmptyWithSpace(sheetName)) {
            throw new IllegalArgumentException("sheetName may not be null or empty");
        }
        Workbook workbook;

        File file = new File(excel.toUri());
        switch (excelType) {
            case XLS:
                if (!file.exists()) {
                    workbook = new HSSFWorkbook();
                } else {
                    workbook = new HSSFWorkbook(new FileInputStream(file));
                }
                break;
            case XLSX:
                if (!file.exists()) {
                    workbook = new XSSFWorkbook();
                } else {
                    workbook = new XSSFWorkbook(new FileInputStream(file));
                }
                break;
            default:
                throw new IllegalArgumentException("error excelType");
        }
        Sheet sheet = workbook.getSheet(sheetName);
        boolean hasTitle = true;
        if (sheet == null) {
            hasTitle = false;
            sheet = workbook.createSheet(sheetName);
        }


        int offset = sheet.getLastRowNum();
        Row title=null;
        if (!hasTitle) {
            title = sheet.createRow(offset);
        }
        List<Row> rows = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            rows.add(sheet.createRow(offset + i + 1));
        }

        Field[] fields = entityType.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Cell.class)) {
                Cell cellAnnotation = field.getDeclaredAnnotation(Cell.class);
                if(!hasTitle){
                    org.apache.poi.ss.usermodel.Cell cell = title.createCell(cellAnnotation.order());
                    cell.setCellValue(cellAnnotation.column());
                }
                for (int i = 0; i < list.size(); i++) {
                    org.apache.poi.ss.usermodel.Cell contentCell = rows.get(i).createCell(cellAnnotation.order());
                    Object o = field.get(list.get(i));
                    contentCell.setCellValue(o == null ? "null" : o.toString());
                }
            }
        }

        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
