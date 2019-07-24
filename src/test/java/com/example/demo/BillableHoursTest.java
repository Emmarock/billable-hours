package com.example.demo;

import com.example.demo.exception.TimeTableRecordException;
import com.example.demo.model.BillableHours;
import com.example.demo.model.TimeTable;
import com.example.demo.service.ProcessorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProcessorImpl.class)
public class BillableHoursTest {

    @Autowired
    ProcessorImpl processor;

    @Test(expected = TimeTableRecordException.class)
    public void testBillableHoursShouldFailWhenTheStartTimeIsGreaterThanEndTime(){
        TimeTable timeTable = getTimeTable("19:00");
        List<BillableHours> billableHoursList = processor.processTimeTable(Collections.singletonList(timeTable));
        assertThat(billableHoursList.size()).isEqualTo(1l);
        int hoursWorked = timeTable.getEndTime().minusHours(timeTable.getStartTime().getHour()).getHour();
        BigDecimal cost = timeTable.getBillableRatePerHour().multiply(BigDecimal.valueOf(hoursWorked));
        assertThat(cost).isEqualByComparingTo(billableHoursList.get(0).getCost());
    }

    @Test
    public void testBillableShouldPassWhenStartTimeIsLessThanEndTime(){
        TimeTable timeTable = getTimeTable("09:00");
        List<BillableHours> billableHoursList = processor.processTimeTable(Collections.singletonList(timeTable));
        assertThat(billableHoursList.size()).isEqualTo(1l);
        int hoursWorked = timeTable.getEndTime().minusHours(timeTable.getStartTime().getHour()).getHour();
        BigDecimal cost = timeTable.getBillableRatePerHour().multiply(BigDecimal.valueOf(hoursWorked));
        assertThat(cost).isEqualByComparingTo(billableHoursList.get(0).getCost());
        assertThat(hoursWorked).isEqualByComparingTo(billableHoursList.get(0).getNumberOfHours());

    }

    @Test
    public void testGroupingByCompanyBillableHoursShouldReturnListWithTwoItems(){
        TimeTable employee1 = getTimeTable("09:00");
        TimeTable employee2 = getTimeTable("10:00");
        List<TimeTable> timeTableList = new ArrayList<>();
        timeTableList.add(employee1);
        timeTableList.add(employee2);

        int employee1HoursWorked = employee1.getEndTime().minusHours(employee1.getStartTime().getHour()).getHour();
        BigDecimal employee1Cost = employee1.getBillableRatePerHour().multiply(BigDecimal.valueOf(employee1HoursWorked));

        int employee2HoursWorked = employee2.getEndTime().minusHours(employee2.getStartTime().getHour()).getHour();
        BigDecimal employee2Cost = employee2.getBillableRatePerHour().multiply(BigDecimal.valueOf(employee2HoursWorked));


        Map<String, List<BillableHours>> companiesBillableHours =  processor.processBillAbles(timeTableList);
        List<BillableHours> billableHoursList = companiesBillableHours.get("Google");
        assertThat(companiesBillableHours.keySet()).size().isEqualTo(1);
        assertThat(companiesBillableHours).hasSize(1);
        assertThat(billableHoursList.get(0).getCost()).isEqualTo(employee1Cost);
        assertThat(billableHoursList.get(1).getCost()).isEqualTo(employee2Cost);
    }

    private TimeTable getTimeTable(String startTime) {
        return TimeTable.builder()
                    .billableRatePerHour(BigDecimal.valueOf(300))
                    .date(LocalDate.now())
                    .employeeId("01")
                    .startTime(LocalTime.parse(startTime))
                    .endTime(LocalTime.parse("17:00"))
                    .project("Google")
                    .build();
    }
}
