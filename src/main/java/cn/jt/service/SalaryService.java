package cn.jt.service;

import cn.jt.security.pub.ResultJSON;
import org.springframework.stereotype.Service;

@Service
public interface SalaryService {

    // 获取指定员工在基本工资表中的最大年份和最小年份
    ResultJSON selectMaxAndMinYearByUserId(int userId);

    // 获取指定部门在基本工资表中的最大年份和最小年份
    ResultJSON selectMaxAndMinYearByDepartId(String departId);

    // 根据职工号和年份获取本年度账单
    ResultJSON selectPersonBookByUserIdAndSyear(int userId, String syear);

    // 根据部门代号，年份，月份获取指定部门指定时间的工资汇总表
    ResultJSON selectDepartBookByDepartIdAndSyearAndSmonth(String departId, String syear,String smonth);

    // 获取部门工资汇总表
    ResultJSON selectBasicSalaryByDepartIdAndSyearAndSmonth(String departId, String syear,String smonth);

    // 根据指定年份月份和部门代号生成本月工资，并返回生成的结果
    ResultJSON  insertBasicSalaryByDepartIdAndSyearAndSmonth(String departId, String syear,String smonth);

    // 判断该部门的本月工资是否已经生成
    ResultJSON  isHaveCurrentMonthBasicSalary(String departId);

    // 更新某部门某职工某年某月的基本工资
    ResultJSON updateBasicSalaryByDepartIdAndUserIdAndDate(String departId, int userId, double bsalary, double jsalary, double wsalary, String smonth,String syear);
}
