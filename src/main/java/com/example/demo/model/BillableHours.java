package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillableHours {
    private String employeeId;
    private int numberOfHours;
    private BigDecimal unitPrice;
    private BigDecimal cost;
    private String company;
}
