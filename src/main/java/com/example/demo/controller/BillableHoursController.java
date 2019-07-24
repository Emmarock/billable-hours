package com.example.demo.controller;

import com.example.demo.service.Processor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@Slf4j
public class BillableHoursController {

    private Processor processor;

    public BillableHoursController(Processor processor) {
        this.processor = processor;
    }

    @GetMapping("/index")
    public String showSignUpForm(Model model) {
        return "index";
    }

    @PostMapping(value = "/index", consumes = "multipart/form-data")
    public Map uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        Map companyBillableHours =  processor.getCompaniesBillableHours(file);
        model.addAttribute("companyBillableHours",companyBillableHours);
        return companyBillableHours;
    }
}
