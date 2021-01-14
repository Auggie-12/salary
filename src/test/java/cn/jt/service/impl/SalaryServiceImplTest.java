package cn.jt.service.impl;

import cn.jt.mapper.SalaryMapper;
import cn.jt.service.SalaryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalaryServiceImplTest {

    @Resource
    SalaryService salaryService;

    @Resource
    SalaryMapper salaryMapper;
    @Test
    void selectPersonBookByUserIdAndSyear() {
        salaryService.selectPersonBookByUserIdAndSyear(1,"2020");
    }

    @Test
    void selectDepartBookByDepartIdAndSyearAndSmonth() {
        salaryService.selectDepartBookByDepartIdAndSyearAndSmonth("A","2019","12");
    }

    @Test
    void testSelectPersonBookByUserIdAndSyear() {
    }

    @Test
    void testSelectDepartBookByDepartIdAndSyearAndSmonth() {
    }

    @Test
    void selectBasicSalaryByDepartIdAndSyearAndSmonth() {
        salaryService.selectBasicSalaryByDepartIdAndSyearAndSmonth("A","2020","12");
    }

    @Test
    void insertBasicSalaryByDepartIdAndSyearAndSmonth() {
        salaryService.insertBasicSalaryByDepartIdAndSyearAndSmonth("A","2020","11");
    }

    @Test
    void isHaveCurrentMonthBasicSalary() {
        System.out.println(salaryService.isHaveCurrentMonthBasicSalary("A"));
    }

    @Test
    void updateBasicSalaryByDepartIdAndUserIdAndDate() {
        String departId = "A";
        int userId = 1;
        double bsalary = 888;
        double jsalary = 888;
        double wsalary =888;
        String smonth = "12";
        String syear = "2020";
        System.out.println(salaryMapper.updateBasicSalaryByDepartIdAndUserIdAndDate(departId,userId,bsalary,jsalary,wsalary,smonth,syear));
    }
}