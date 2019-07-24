package com.example.demo.service;

import com.example.demo.CsvUtils;
import com.example.demo.exception.TimeTableRecordException;
import com.example.demo.model.BillableHours;
import com.example.demo.model.TimeTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProcessorImpl implements Processor<TimeTable> {

    public List<BillableHours> processTimeTable(List<TimeTable> timeTables) {
        List<BillableHours> billableHoursList = new ArrayList<>();
        timeTables.forEach(timeTable -> {
            isValidStartAndEndTIme(timeTable);
            int hoursWorked = timeTable.getEndTime().minusHours(timeTable.getStartTime().getHour()).getHour();
            BigDecimal cost = timeTable.getBillableRatePerHour().multiply(BigDecimal.valueOf(hoursWorked));
            BillableHours billableHours = BillableHours.builder()
                    .cost(cost)
                    .employeeId(timeTable.getEmployeeId())
                    .numberOfHours(hoursWorked)
                    .unitPrice(timeTable.getBillableRatePerHour())
                    .company(timeTable.getProject().trim())
                    .build();
            billableHoursList.add(billableHours);

        });
        return billableHoursList;
    }

    @Override
    public Map<String, List<BillableHours>> processBillAbles(List<TimeTable> timeTables){
        List<BillableHours> billableHours = processTimeTable(timeTables);
        return billableHours.stream().collect(Collectors.groupingBy(BillableHours::getCompany));
    }

    @Override
    public Map<String, List<BillableHours>> getCompaniesBillableHours(MultipartFile file) throws IOException {
        List<TimeTable> response = CsvUtils.read(TimeTable.class, file.getInputStream());
        return processBillAbles(response);
    }

    @Override
    public List<TimeTable> readTimeTableStream(Class clazz, InputStream stream) throws IOException {
        return CsvUtils.read(TimeTable.class, stream);

    }

    private boolean isValidStartAndEndTIme(TimeTable timeTable){
        if (timeTable.getEndTime().compareTo(timeTable.getStartTime()) >= 0){
            return true;
        }
        throw new TimeTableRecordException(String.format("The employee with Id %s record can not have his/her " +
                        "start time of %s greater than end time of %s",
                timeTable.getEmployeeId(),timeTable.getStartTime(),timeTable.getEndTime()));
    }


}
