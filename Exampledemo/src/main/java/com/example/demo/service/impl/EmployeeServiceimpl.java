package com.example.demo.service.impl;

import com.example.demo.UserEntity.UserEntity;
import com.example.demo.UserReposity.UserRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class EmployeeServiceimpl implements UserService {


    public static int id = 1;

    @Autowired
    private UserRepository userRepository;


    @Override
    public String readExcel(MultipartFile file) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        XSSFSheet sheet = workbook.getSheetAt(0);

        int rowcount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        for (int i = 1; i < rowcount; i++) {

            int cellcount = sheet.getRow(i).getLastCellNum();

            UserEntity user = new UserEntity();

            user.setExcel_id(id);
            user.setName(sheet.getRow(i).getCell(1).getStringCellValue());
            user.setLastname(sheet.getRow(i).getCell(2).getStringCellValue());
            user.setDesignation(sheet.getRow(i).getCell(3).getStringCellValue());
            user.setSalary(sheet.getRow(i).getCell(4).getNumericCellValue() + "");

            userRepository.save(user);

        }
        return "Insert Data success database from Excel sheet";
    }

    @Override
    public String getdata() throws Exception {

        List<UserEntity> user = userRepository.findAll();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Employee data");
            XSSFRow row = sheet.createRow(0);

            row.createCell(0).setCellValue("EXCEL_ID");
            row.createCell(1).setCellValue("NAME");
            row.createCell(2).setCellValue("LASTNAME");
            row.createCell(3).setCellValue("DESIGNATION");
            row.createCell(4).setCellValue("SALARY");

            int datarowindex = 1;
            for (UserEntity entity : user) {

                XSSFRow datarow = sheet.createRow(datarowindex);
                datarow.createCell(0).setCellValue(entity.getExcel_id());
                datarow.createCell(1).setCellValue(entity.getName());
                datarow.createCell(2).setCellValue(entity.getLastname());
                datarow.createCell(3).setCellValue(entity.getDesignation());
                datarow.createCell(4).setCellValue(entity.getSalary());

                datarowindex++;
            }

            FileOutputStream out = new FileOutputStream(new File("G:\\FileUload\\Employee_Record.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("Excel file written successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Successfully EmployeeData get from database";
    }

    @Override
    public String getpdf() throws Exception {

        List<UserEntity> user = userRepository.findAll();

        FileOutputStream fos = new FileOutputStream("G:\\FileUload\\EmployeeDatapdf2.pdf");

        Document doc = new Document();

        PdfWriter writer = PdfWriter.getInstance(doc, fos);

        doc.open();

        PdfPTable table = new PdfPTable(5);

        table.addCell(String.valueOf("Excel_ID"));
        table.addCell(String.valueOf("Name"));
        table.addCell(String.valueOf("LastName"));
        table.addCell(String.valueOf("Designation"));
        table.addCell(String.valueOf("Salary"));

        for (UserEntity userEntity : user) {

            table.addCell(String.valueOf(userEntity.getExcel_id()));
            table.addCell(userEntity.getName());
            table.addCell(userEntity.getLastname());
            table.addCell(userEntity.getDesignation());
            table.addCell(userEntity.getSalary() + "");
        }
        doc.add(table);

        doc.close();
        writer.close();
        fos.close();

        return "Pdf file is created.!";
    }

}