package com.example.demo.controller;

import com.example.demo.service.Processor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
public class BillableHoursApiController {

    private Processor processor;

    @Autowired
    public BillableHoursApiController(Processor processor) {
        this.processor = processor;
    }

    @PostMapping("/upload")
    public Map getCompaniesBillableHours(@RequestParam("file") MultipartFile file) throws IOException {
        return getBillableHours(file);
    }

    @Async
    public Map getBillableHours(MultipartFile file) throws IOException {
        return processor.getCompaniesBillableHours(file);
    }

}
