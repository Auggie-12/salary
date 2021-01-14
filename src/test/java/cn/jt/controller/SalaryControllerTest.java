package cn.jt.controller;

import cn.jt.service.SalaryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalaryControllerTest {
    @Resource
    SalaryService salaryService;

    @Test
    void selectMaxAndMinYearByUserId() {
        System.out.println(salaryService.selectMaxAndMinYearByUserId(1));
    }
}