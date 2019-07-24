package com.example.demo.service;

import com.example.demo.model.BillableHours;
import com.example.demo.model.TimeTable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface Processor<T> {

    List<BillableHours> processTimeTable(List<TimeTable> timeTables);
    List<TimeTable> readTimeTableStream(Class<T> clazz, InputStream stream) throws IOException;
    Map<String, List<BillableHours>> processBillAbles(List<TimeTable> timeTables);
    Map<String, List<BillableHours>> getCompaniesBillableHours(MultipartFile file) throws IOException;
}
