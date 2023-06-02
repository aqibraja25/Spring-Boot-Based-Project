package com.example.demo.controller;

import com.example.demo.service.impl.UserService;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
public class Testcontroller {

    @Autowired
    private UserService employeeService;

    @PostMapping("/getExcel-file")
    public String getdatad(@RequestBody MultipartFile file) throws Exception, IOException {
        return employeeService.readExcel(file);
    }

    @GetMapping("/getWrite-data")
    public String data() throws Exception, IOException {
        return employeeService.getdata();
    }

    @GetMapping("/getpdf")
    public String pdf() throws Exception, IOException {
        return employeeService.getpdf();
    }

}