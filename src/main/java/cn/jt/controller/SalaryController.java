package cn.jt.controller;

import cn.jt.security.pub.ResultJSON;
import cn.jt.service.SalaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/salary/")
public class SalaryController {

    @Resource
    SalaryService salaryService;

    // 获取指定员工在基本工资表中的最大年份和最小年份
    @RequestMapping("selectMaxAndMinYearByUserId")
    ResultJSON selectMaxAndMinYearByUserId(@RequestParam("userId")int userId) {
        return salaryService.selectMaxAndMinYearByUserId(userId);
    }

    // 获取指定部门在基本工资表中的最大年份和最小年份
    @RequestMapping("selectMaxAndMinYearByDepartId")
    ResultJSON selectMaxAndMinYearByDepartId(@RequestParam("departId")String departId) {
        return salaryService.selectMaxAndMinYearByDepartId(departId);
    }

    // 根据职工号和年份获取本年度账单
    @RequestMapping("selectPersonBookByUserIdAndSyear")
    ResultJSON selectPersonBookByUserIdAndSyear(@RequestParam("userId")int userId, @RequestParam("syear")String syear) {
        return salaryService.selectPersonBookByUserIdAndSyear(userId, syear);
    }

    // 根据部门代号，年份，月份获取指定部门指定时间的工资汇总表
    @RequestMapping("selectDepartBookByDepartIdAndSyearAndSmonth")
    ResultJSON selectDepartBookByDepartIdAndSyearAndSmonth(
            @RequestParam("departId")String departId,
            @RequestParam("syear")String syear,
            @RequestParam("smonth")String smonth) {
        return salaryService.selectDepartBookByDepartIdAndSyearAndSmonth(departId, syear, smonth);
    }

    // 获取部门基本工资汇总表
    @RequestMapping("selectBasicSalaryByDepartIdAndSyearAndSmonth")
    public ResultJSON selectBasicSalaryByDepartIdAndSyearAndSmonth(
            @RequestParam("departId")String departId,
            @RequestParam("syear")String syear,
            @RequestParam("smonth")String smonth) {
        return salaryService.selectBasicSalaryByDepartIdAndSyearAndSmonth(departId, syear, smonth);
    }

    // 根据指定年份月份和部门代号生成本月工资，并返回生成的结果
    @RequestMapping("insertBasicSalaryByDepartIdAndSyearAndSmonth")
    public ResultJSON  insertBasicSalaryByDepartIdAndSyearAndSmonth(
            @RequestParam("departId")String departId,
            @RequestParam("syear")String syear,
            @RequestParam("smonth")String smonth) {
        return salaryService.insertBasicSalaryByDepartIdAndSyearAndSmonth(departId, syear, smonth);
    }

    // 判断该部门的本月工资是否已经生成
    @RequestMapping("isHaveCurrentMonthBasicSalary")
    public ResultJSON  isHaveCurrentMonthBasicSalary(@RequestParam("departId")String departId) {
        return salaryService.isHaveCurrentMonthBasicSalary(departId);
    }

    // 更新某部门某职工某年某月的基本工资
    @RequestMapping("updateBasicSalaryByDepartIdAndUserIdAndDate")
    ResultJSON updateBasicSalaryByDepartIdAndUserIdAndDate(
            @RequestParam("departId")String departId,
            @RequestParam("userId")int userId,
            @RequestParam("bsalary")double bsalary,
            @RequestParam("jsalary")double jsalary,
            @RequestParam("wsalary")double wsalary,
            @RequestParam("smonth")String smonth,
            @RequestParam("syear")String syear
            ) {
        return salaryService.updateBasicSalaryByDepartIdAndUserIdAndDate(departId,userId,bsalary,jsalary,wsalary,smonth,syear);
    }

}
