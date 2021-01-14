package cn.jt.controller;

import cn.jt.service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecordControllerTest {

    @Resource
    RecordService recordService;

    @Test
    void selectRecordById() {
        System.out.println(recordService.selectRecordById(1));
    }

    @Test
    void selectReasonsByUserIdAndReasonId() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String rmonth = month + "";
        String ryear = year +"";
        System.out.println(rmonth);
        System.out.println(ryear);
        System.out.println(recordService.selectReasonsByUserIdAndReasonId(1,11,null,null));
    }
}