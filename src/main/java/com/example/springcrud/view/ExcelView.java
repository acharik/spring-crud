package com.example.springcrud.view;


import com.example.springcrud.entity.LocContract;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractXlsxView {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.DATE_FIELD);

    @Override
    protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {


        response.setHeader("Content-Disposition", "attachment; filename=\"report.xls\"");

        @SuppressWarnings("unchecked")
        List<LocContract> locContracts = (List<LocContract>) model.get("LocContractList");


        Sheet sheet = workbook.createSheet("Отчет");


        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Дата начала действия контракта");
        header.createCell(1).setCellValue("Дата конца действия контракта");
        header.createCell(2).setCellValue("Номер контракта");
        header.createCell(3).setCellValue("Сумма");
        header.createCell(4).setCellValue("Комментарий");



        int rowCount = 1;
        for (LocContract locContract : locContracts) {
            Row locContractRow = sheet.createRow(rowCount++);
            locContractRow.createCell(0).setCellValue(locContract.getDateBegin().toString());
            locContractRow.createCell(1).setCellValue((locContract.getDateEnd()).toString());
            locContractRow.createCell(2).setCellValue(locContract.getNumContract());
            locContractRow.createCell(3).setCellValue(  locContract.getSum().doubleValue());
            locContractRow.createCell(4).setCellValue(locContract.getComment());

        }

    }
}