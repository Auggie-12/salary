package cn.jt.mapper;

import cn.jt.pojo.Salary;
import cn.jt.security.pub.ResultJSON;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface SalaryMapper {

    // 获取指定员工在基本工资表中的最大年份和最小年份
    Map selectMaxAndMinYearByUserId(@Param("userId")int userId);

    // 获取指定部门在基本工资表中的最大年份和最小年份
    Map selectMaxAndMinYearByDepartId(@Param("departId")String departId);

    // 获取指定职工，指定年份，指定月份的基本工资，职务工资，工龄工资
    Salary selectSalaryByUserIdAndSyearAndSmonth(
            @Param("userId")int userId,
            @Param("syear")String syear,
            @Param("smonth")String smonth
    );

    // 指定职工，指定年份，指定月份的基本工资，职务工资，工龄工资
    int insertBasicSalaryByDepartIdAndSyearAndSmonth(
            @Param("departId")String departId,
            @Param("userId")int userId,
            @Param("bsalary")double bsalary,
            @Param("jsalary")double jsalary,
            @Param("wsalary")double wsalary,
            @Param("smonth")String smonth,
            @Param("syear")String syear
    );

    // 判断该部门的本月工资是否已经生成
    int isHaveCurrentMonthBasicSalary(@Param("departId")String departId,
                                      @Param("smonth")String smonth,
                                      @Param("syear")String syear);

    // 更新某部门某职工某年某月的基本工资
    int updateBasicSalaryByDepartIdAndUserIdAndDate(
            @Param("departId")String departId,
            @Param("userId")int userId,
            @Param("bsalary")double bsalary,
            @Param("jsalary")double jsalary,
            @Param("wsalary")double wsalary,
            @Param("smonth")String smonth,
            @Param("syear")String syear);


}