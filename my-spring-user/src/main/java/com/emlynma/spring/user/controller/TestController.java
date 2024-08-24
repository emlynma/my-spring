package com.emlynma.spring.user.controller;

import com.emlynma.spring.core.util.JsonUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public Object hello() {
        JsonUtils.toJson("");
        return "hello world";
    }

    @RequestMapping("/exception")
    public String exception() {
        throw new RuntimeException("test exception");
    }

    @GetMapping("/download/excel")
    public ResponseEntity<byte[]> download() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("Sheet 1");
        Sheet sheet2 = workbook.createSheet("Sheet 2");

        // 在 Sheet 1 中添加数据
        Row row1 = sheet1.createRow(0);
        row1.createCell(0).setCellValue("Name");
        row1.createCell(1).setCellValue("Age");
        row1.createCell(2).setCellValue("Sex");

        Row row2 = sheet1.createRow(1);
        row2.createCell(0).setCellValue("Alice");
        row2.createCell(1).setCellValue(25);
        row2.createCell(2).setCellValue("Female");

        Row row3 = sheet1.createRow(2);
        row3.createCell(0).setCellValue("Bob");
        row3.createCell(1).setCellValue(30);
        row3.createCell(2).setCellValue("Male");

        // 在 Sheet 2 中添加数据
        Row row4 = sheet2.createRow(0);
        row4.createCell(0).setCellValue("Name");
        row4.createCell(1).setCellValue("Age");
        row4.createCell(2).setCellValue("Sex");

        Row row5 = sheet2.createRow(1);
        row5.createCell(0).setCellValue("Eve");
        row5.createCell(1).setCellValue(28);
        row5.createCell(2).setCellValue("Female");

        // 将 Excel 数据写入 ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        // 设置文件名和响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "data.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }

}
