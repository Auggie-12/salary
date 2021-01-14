package cn.jt.service.impl;

import cn.jt.mapper.DepartMapper;
import cn.jt.mapper.RecordMapper;
import cn.jt.mapper.SalaryMapper;
import cn.jt.mapper.UserMapper;
import cn.jt.pojo.Depart;
import cn.jt.pojo.Salary;
import cn.jt.pojo.User;
import cn.jt.pojo.result.BasicSalary;
import cn.jt.pojo.result.ChangeReason;
import cn.jt.pojo.result.DepartBook;
import cn.jt.pojo.result.PersonBook;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.SalaryService;
import cn.jt.utils.Log4jUtils;
import cn.jt.utils.SalaryUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Resource
    SalaryMapper salaryMapper;

    @Resource
    RecordMapper recordMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    DepartMapper departMapper;

    // 获取指定员工在基本工资表中的最大年份和最小年份
    public ResultJSON selectMaxAndMinYearByUserId(int userId) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            System.out.println(salaryMapper.selectMaxAndMinYearByUserId(userId));
            Map year = salaryMapper.selectMaxAndMinYearByUserId(userId);
            result.put("year", year);
            return new ResultJSON(11, "获取指定员工在基本工资表中的最大年份和最小年份成功", result);
        } catch (Exception e) {
            return new ResultJSON(00, "系统错误，获取指定员工在基本工资表中的最大年份和最小年份失败", null);
        }
    }

    // 获取指定部门在基本工资表中的最大年份和最小年份
    public ResultJSON selectMaxAndMinYearByDepartId(String departId) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            Map year = salaryMapper.selectMaxAndMinYearByDepartId(departId);
            result.put("year", year);
            return new ResultJSON(11, "获取指定部门在基本工资表中的最大年份和最小年份成功", result);
        } catch (Exception e) {
            return new ResultJSON(00, "系统错误，获取指定部门在基本工资表中的最大年份和最小年份失败", null);
        }
    }

    // 根据职工号和年份获取本年度账单
    public ResultJSON selectPersonBookByUserIdAndSyear(int userId, String syear) {
        HashMap<String, Object> result = new HashMap<>();
        List<PersonBook> personBookList = new ArrayList<>();
        try {
            for (int i = 1; i <= 12; i++) {
                PersonBook personBook = new PersonBook();            // 新增加一行
                personBook.setMonth(i + "月");                         // 设置当前月份

                // 获取当前职工，当前年份，当前月份的基本工资，职务工资，工龄工资
                Salary salary = salaryMapper.selectSalaryByUserIdAndSyearAndSmonth(userId, syear, i + "");
                // 存在 salary 为空的情况，设置标志为 0
                if (salary == null) {
                    personBook.setBsalary(0);          // 设置基本工资
                    personBook.setJsalary(0);          // 设置职务工资
                    personBook.setWsalary(0);          // 设置工龄工资
                } else {
                    personBook.setBsalary(salary.getBsalary());          // 设置基本工资
                    personBook.setJsalary(salary.getJsalary());          // 设置职务工资
                    personBook.setWsalary(salary.getWsalary());          // 设置工龄工资
                }

                // 获取津贴列表（录入的）
                List<ChangeReason> changes1 = recordMapper.selectPersonBookChangeReason(
                        userId, 1, syear, i + "");
                // 未录入的（设置金额为 0）
                List<ChangeReason> changes1plus = recordMapper.selectPersonBookChangeReasonPlus(
                        userId, 1, syear, i + "");
                // 合并之后，所有具体类型都包括，没有录入的金额为 0
                changes1.addAll(changes1plus);
                // 合并之后按 reason 编号进行排序，避免位置出错
                changes1 = changes1.stream().sorted(Comparator.comparing(ChangeReason::getReasonId).thenComparing(ChangeReason::getReasonId)).collect(Collectors.toList());
                // 设置津贴列表
                personBook.setChanges1(changes1);

                for (int j = 0; j < changes1.size(); j++) {
                    System.out.println(changes1.get(j).getTotalMoney());
                }

                System.out.println(changes1);

                // 计算津贴总额
                double sum1 = 0;
                for (int j = 0; j < changes1.size(); j++) {
                    sum1 = sum1 + changes1.get(j).getTotalMoney();
                }
                // 设置津贴总额
                personBook.setSum1(sum1);

                // 获取补贴列表
                List<ChangeReason> changes2 = recordMapper.selectPersonBookChangeReason(
                        userId, 2, syear, i + "");
                // 未录入的（设置金额为 0）
                List<ChangeReason> changes2plus = recordMapper.selectPersonBookChangeReasonPlus(
                        userId, 2, syear, i + "");
                // 合并之后，所有具体类型都包括，没有录入的金额为 0
                changes2.addAll(changes2plus);
                // 合并之后按 reason 编号进行排序，避免位置出错
                changes2 = changes2.stream().sorted(Comparator.comparing(ChangeReason::getReasonId).thenComparing(ChangeReason::getReasonId)).collect(Collectors.toList());
                // 设置补贴列表
                personBook.setChanges2(changes2);

                // 计算补贴总额
                double sum2 = 0;
                for (int j = 0; j < changes2.size(); j++) {
                    sum2 = sum2 + changes2.get(j).getTotalMoney();
                }
                // 设置补贴总额
                personBook.setSum2(sum2);

                // 计算应发工资
                double ssalary = personBook.getBsalary() + personBook.getJsalary() + personBook.getWsalary() + personBook.getSum1() + personBook.getSum2();
                // 设置应发工资
                personBook.setSsalary(ssalary);

                // 获取扣款列表
                List<ChangeReason> changes3 = recordMapper.selectPersonBookChangeReason(
                        userId, 3, syear, i + "");
                // 未录入的（设置金额为 0）
                List<ChangeReason> changes3plus = recordMapper.selectPersonBookChangeReasonPlus(
                        userId, 3, syear, i + "");
                // 合并之后，所有具体类型都包括，没有录入的金额为 0
                changes3.addAll(changes3plus);
                // 合并之后按 reason 编号进行排序，避免位置出错
                changes3 = changes3.stream().sorted(Comparator.comparing(ChangeReason::getReasonId).thenComparing(ChangeReason::getReasonId)).collect(Collectors.toList());
                // 设置扣款列表
                personBook.setChanges3(changes3);

                // 计算扣款总额
                double sum3 = 0;
                for (int j = 0; j < changes3.size(); j++) {
                    sum3 = sum3 + changes3.get(j).getTotalMoney();
                }
                // 设置扣款总额
                personBook.setSum3(sum3);

                // 计算实发工资
                double fsalary = personBook.getSsalary() - personBook.getSum3();
                // 设置实发工资
                personBook.setFsalary(fsalary);

                // 计算个人所得税
                double ptax = new SalaryUtil().getPersonalIncomeTax(personBook.getFsalary());
                // 设置个人所得税
                personBook.setPtax(ptax);

                // 计算税后工资
                double tsalary = personBook.getFsalary() - personBook.getPtax();
                // 设置税后工资
                personBook.setTsalary(tsalary);

                 System.out.println(syear + " 年 " + i + " 月 :");
                 System.out.println("基本工资：" + personBook.getBsalary());
                 System.out.println("职务工资：" + personBook.getJsalary());
                 System.out.println("工龄工资：" + personBook.getWsalary());
                 System.out.println("津贴总额：" + personBook.getSum1());
                 System.out.println("补贴总额：" + personBook.getSum2());
                 System.out.println("应发工资：" + personBook.getSsalary());
                 System.out.println("扣款总额：" + personBook.getSum3());
                 System.out.println("实发工资：" + personBook.getFsalary());
                 System.out.println("个人所得税：" + personBook.getPtax());
                 System.out.println("税后工资：" + personBook.getTsalary());
                 System.out.println();

                // 将当前行添加到列表中
                personBookList.add(personBook);
            }
            result.put("personBookList", personBookList);
            return new ResultJSON(11, "根据职工号和年份获取本年度账单成功", result);
        } catch (Exception e) {
            return new ResultJSON(00, "系统错误，根据职工号和年份获取本年度账单失败", result);
        }
    }

    // 根据部门代号，年份，月份获取指定部门指定时间的工资汇总表
    public ResultJSON selectDepartBookByDepartIdAndSyearAndSmonth(
            String departId, String syear,String smonth) {

        // 存储返回结果
        HashMap<String, Object> result = new HashMap<>();
        // 存储部门工资汇总账单
        List<DepartBook> departBookList = new ArrayList<>();

        try {
            // 根据部门代号获取部门员工列表
            List<User> userList = userMapper.selectUsersByDepartId(departId);
            // 根据部门代号获取部门名称
            Depart depart = departMapper.getById(departId);
            // 获取部门名称
            String departName = depart.getName();

            // 遍历该部门的所有用户
            for (int i = 0; i < userList.size(); i++) {

                // 新增一行
                DepartBook departBook = new DepartBook();

                // 设置部门名称
                departBook.setDepartName(departName);

                // 获取职工号
                int userId = userList.get(i).getId();
                // 设置职工号
                departBook.setUserId(userId);

                // 获取职工姓名
                String realname = userList.get(i).getRealname();
                // 设置职工姓名
                departBook.setRealname(realname);

                // 获取当前职工，当前年份，当前月份的基本工资，职务工资，工龄工资
                Salary salary = salaryMapper.selectSalaryByUserIdAndSyearAndSmonth(userId, syear, smonth);
                // 存在 salary 为空的情况，设置标志为 0
                if (salary == null) {
                    departBook.setBsalary(0);          // 设置基本工资
                    departBook.setJsalary(0);          // 设置职务工资
                    departBook.setWsalary(0);          // 设置工龄工资
                } else {
                    departBook.setBsalary(salary.getBsalary());          // 设置基本工资
                    departBook.setJsalary(salary.getJsalary());          // 设置职务工资
                    departBook.setWsalary(salary.getWsalary());          // 设置工龄工资
                }

                // 获取津贴列表（录入的）
                List<ChangeReason> changes1 = recordMapper.selectPersonBookChangeReason(
                        userId, 1, syear, smonth);
                // 未录入的（设置金额为 0）
                List<ChangeReason> changes1plus = recordMapper.selectPersonBookChangeReasonPlus(
                        userId, 1, syear, smonth);
                // 合并之后，所有具体类型都包括，没有录入的金额为 0
                changes1.addAll(changes1plus);
                // 合并之后按 reason 编号进行排序，避免位置出错
                changes1 = changes1.stream().sorted(Comparator.comparing(ChangeReason::getReasonId).thenComparing(ChangeReason::getReasonId)).collect(Collectors.toList());
                // 设置津贴列表
                departBook.setChanges1(changes1);

                // 计算津贴总额
                double sum1 = 0;
                for (int j = 0; j < changes1.size(); j++) {
                    sum1 = sum1 + changes1.get(j).getTotalMoney();
                }
                // 设置津贴总额
                departBook.setSum1(sum1);

                // 获取补贴列表
                List<ChangeReason> changes2 = recordMapper.selectPersonBookChangeReason(
                        userId, 2, syear, smonth);
                // 未录入的（设置金额为 0）
                List<ChangeReason> changes2plus = recordMapper.selectPersonBookChangeReasonPlus(
                        userId, 2, syear, smonth);
                // 合并之后，所有具体类型都包括，没有录入的金额为 0
                changes2.addAll(changes2plus);
                // 合并之后按 reason 编号进行排序，避免位置出错
                changes2 = changes2.stream().sorted(Comparator.comparing(ChangeReason::getReasonId).thenComparing(ChangeReason::getReasonId)).collect(Collectors.toList());
                // 设置补贴列表
                departBook.setChanges2(changes2);

                // 计算补贴总额
                double sum2 = 0;
                for (int j = 0; j < changes2.size(); j++) {
                    sum2 = sum2 + changes2.get(j).getTotalMoney();
                }
                // 设置补贴总额
                departBook.setSum2(sum2);

                // 计算应发工资
                double ssalary = departBook.getBsalary() + departBook.getJsalary() + departBook.getWsalary() + departBook.getSum1() + departBook.getSum2();
                // 设置应发工资
                departBook.setSsalary(ssalary);

                // 获取扣款列表
                List<ChangeReason> changes3 = recordMapper.selectPersonBookChangeReason(
                        userId, 3, syear, smonth);
                // 未录入的（设置金额为 0）
                List<ChangeReason> changes3plus = recordMapper.selectPersonBookChangeReasonPlus(
                        userId, 3, syear, smonth);
                // 合并之后，所有具体类型都包括，没有录入的金额为 0
                changes3.addAll(changes3plus);
                // 合并之后按 reason 编号进行排序，避免位置出错
                changes3 = changes3.stream().sorted(Comparator.comparing(ChangeReason::getReasonId).thenComparing(ChangeReason::getReasonId)).collect(Collectors.toList());
                // 设置扣款列表
                departBook.setChanges3(changes3);

                // 计算扣款总额
                double sum3 = 0;
                for (int j = 0; j < changes3.size(); j++) {
                    sum3 = sum3 + changes3.get(j).getTotalMoney();
                }
                // 设置扣款总额
                departBook.setSum3(sum3);

                // 计算实发工资
                double fsalary = departBook.getSsalary() - departBook.getSum3();
                // 设置实发工资
                departBook.setFsalary(fsalary);

                // 计算个人所得税
                double ptax = new SalaryUtil().getPersonalIncomeTax(departBook.getFsalary());
                // 设置个人所得税
                departBook.setPtax(ptax);

                // 计算税后工资
                double tsalary = departBook.getFsalary() - departBook.getPtax();
                // 设置税后工资
                departBook.setTsalary(tsalary);

                System.out.println(syear + " 年 " + smonth + " 月 ，职工 " + departBook.getRealname() + " 工资信息：");
                System.out.println("基本工资：" + departBook.getBsalary());
                System.out.println("职务工资：" + departBook.getJsalary());
                System.out.println("工龄工资：" + departBook.getWsalary());
                System.out.println("津贴总额：" + departBook.getSum1());
                System.out.println("补贴总额：" + departBook.getSum2());
                System.out.println("应发工资：" + departBook.getSsalary());
                System.out.println("扣款总额：" + departBook.getSum3());
                System.out.println("实发工资：" + departBook.getFsalary());
                System.out.println("个人所得税：" + departBook.getPtax());
                System.out.println("税后工资：" + departBook.getTsalary());
                System.out.println();

                // 将当前行添加到列表中
                departBookList.add(departBook);
            }

            result.put("departBookList",departBookList);
            return new ResultJSON(11, "根据部门代号，年份，月份获取指定部门指定时间的工资汇总表成功", result);

        }
        catch(Exception e) {
            return new ResultJSON(00, "系统错误，根据部门代号，年份，月份获取指定部门指定时间的工资汇总表失败", result);
        }

    }

    // 获取部门基本工资汇总表
    public ResultJSON selectBasicSalaryByDepartIdAndSyearAndSmonth(
            String departId, String syear,String smonth) {
        // 存储返回结果
        HashMap<String, Object> result = new HashMap<>();
        // 存储部门基本工资汇总账单
        List<BasicSalary> basicSalaryList = new ArrayList<>();

        // 根据部门代号获取部门员工列表
        List<User> userList = userMapper.selectUsersByDepartId(departId);
        // 根据部门代号获取部门名称
        Depart depart = departMapper.getById(departId);
        // 获取部门名称
        String departName = depart.getName();

        // 遍历该部门的所有用户
        for (int i = 0; i < userList.size(); i++) {

            // 新增一行
            BasicSalary basicSalary = new BasicSalary();

            // 设置部门名称
            basicSalary.setDepartName(departName);

            // 获取职工号
            int userId = userList.get(i).getId();
            // 设置职工号
            basicSalary.setUserId(userId);

            // 获取职工姓名
            String realname = userList.get(i).getRealname();
            // 设置职工姓名
            basicSalary.setRealname(realname);

            // 获取当前职工，当前年份，当前月份的基本工资，职务工资，工龄工资
            try { // 存在多条记录会报错
                Salary salary = salaryMapper.selectSalaryByUserIdAndSyearAndSmonth(userId, syear, smonth);
                // 存在 salary 为空的情况，设置标志为 0
                if (salary == null) {
                    basicSalary.setBsalary(0);          // 设置基本工资
                    basicSalary.setJsalary(0);          // 设置职务工资
                    basicSalary.setWsalary(0);          // 设置工龄工资
                } else {
                    basicSalary.setBsalary(salary.getBsalary());          // 设置基本工资
                    basicSalary.setJsalary(salary.getJsalary());          // 设置职务工资
                    basicSalary.setWsalary(salary.getWsalary());          // 设置工龄工资
                }

                basicSalary.setIsEdit(0);   // 设置状态为不可编辑

            } catch (Exception e) {
                return new ResultJSON(00, "系统错误，存在多条记录", result);
            }
            // 将当前行添加到列表中
            basicSalaryList.add(basicSalary);

            System.out.println(syear + " 年 " + smonth + " 月 ，职工 " + basicSalary.getRealname() + " 工资信息：");
            System.out.println("基本工资：" + basicSalary.getBsalary());
            System.out.println("职务工资：" + basicSalary.getJsalary());
            System.out.println("工龄工资：" + basicSalary.getWsalary());

        }
        result.put("basicSalaryList",basicSalaryList);
        return new ResultJSON(11, "获取基本工资汇总表成功", result);
    }

    // 根据指定年份月份和部门代号生成本月工资，并返回生成的结果
    public ResultJSON  insertBasicSalaryByDepartIdAndSyearAndSmonth(String departId, String syear,String smonth) {
        // 存储返回结果
        HashMap<String, Object> result = new HashMap<>();
        // 存储部门基本工资汇总账单
        List<BasicSalary> basicSalaryList = new ArrayList<>();
        // 插入成功？
        int isInsert = 1;

        // 根据部门代号获取部门员工列表
        List<User> userList = userMapper.selectUsersByDepartId(departId);
        // 根据部门代号获取部门名称
        Depart depart = departMapper.getById(departId);
        // 获取部门名称
        String departName = depart.getName();
        // 遍历该部门的所有用户
        for (int i = 0; i < userList.size(); i++) {

            // 新增一行
            BasicSalary basicSalary = new BasicSalary();

            // 设置部门名称
            basicSalary.setDepartName(departName);

            // 获取职工号
            int userId = userList.get(i).getId();
            // 设置职工号
            basicSalary.setUserId(userId);

            // 获取职工姓名
            String realname = userList.get(i).getRealname();
            // 设置职工姓名
            basicSalary.setRealname(realname);

            // 获取当前职工，当前年份，当前月份的基本工资，职务工资，工龄工资
            try { // 存在多条记录会报错
                Salary salary = salaryMapper.selectSalaryByUserIdAndSyearAndSmonth(userId, syear, smonth);
                // 存在 salary 为空的情况，设置标志为 0
                if (salary == null) {
                    basicSalary.setBsalary(0);          // 设置基本工资
                    basicSalary.setJsalary(0);          // 设置职务工资
                    basicSalary.setWsalary(0);          // 设置工龄工资
                } else {
                    basicSalary.setBsalary(salary.getBsalary());          // 设置基本工资
                    basicSalary.setJsalary(salary.getJsalary());          // 设置职务工资
                    basicSalary.setWsalary(salary.getWsalary());          // 设置工龄工资
                }

                basicSalary.setIsEdit(0);   // 设置状态为不可编辑

            } catch (Exception e) {
                return new ResultJSON(00, "系统错误，存在多条记录", result);
            }
            // 将当前行添加到列表中
            basicSalaryList.add(basicSalary);

            System.out.println(syear + " 年 " + smonth + " 月 ，职工 " + basicSalary.getRealname() + " 工资信息：");
            System.out.println("基本工资：" + basicSalary.getBsalary());
            System.out.println("职务工资：" + basicSalary.getJsalary());
            System.out.println("工龄工资：" + basicSalary.getWsalary());

            // 获取待插入的年月份
            Calendar cal = Calendar.getInstance();
            String smonth1 = (cal.get(Calendar.MONTH) + 1) + "";
            String syear1 = (cal.get(Calendar.YEAR)) + "";
            System.out.println(departId);
            System.out.println();
            System.out.println(smonth1);
            System.out.println(syear1);

            isInsert = salaryMapper.insertBasicSalaryByDepartIdAndSyearAndSmonth(
                    departId, basicSalary.getUserId(), basicSalary.getBsalary(),
                    basicSalary.getJsalary(), basicSalary.getWsalary(), smonth1,syear1);

            if (isInsert == 0) break;
        }
        result.put("basicSalaryList",basicSalaryList);
        result.put("isInsert",isInsert);
        if (isInsert == 0) {
            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId", user.getId());
            MDC.put("userName", user.getRealname());
            Log4jUtils.getLogger().error("生成基本工资失败。部门代号："+departId+" 年份："+syear+" 月份："+smonth);
            return new ResultJSON(00, "插入基本工资失败", result);
        }
        // 写入日志
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User user = (User) request.getSession().getAttribute("user");
        MDC.put("userId", user.getId());
        MDC.put("userName", user.getRealname());
        Log4jUtils.getLogger().info("生成基本工资失败。部门代号："+departId+" 年份："+syear+" 月份："+smonth);
        return new ResultJSON(11, "插入基本工资成功", result);
    }

    // 判断该部门的本月工资是否已经生成
    public ResultJSON  isHaveCurrentMonthBasicSalary(String departId) {
        // 存储返回结果
        HashMap<String, Object> result = new HashMap<>();
        // 获取当前年月份
        Calendar cal = Calendar.getInstance();
        String smonth = (cal.get(Calendar.MONTH) + 1) + "";
        String syear = (cal.get(Calendar.YEAR)) + "";
        int isHave = 0;
        isHave = salaryMapper.isHaveCurrentMonthBasicSalary(departId,smonth,syear);

        // 没有生成
        if (isHave == 0) {
            return new ResultJSON(10, "没有生成本月工资", result);
        }
        else {
            return new ResultJSON(11, "已经生成本月工资", result);
        }
    }

    // 更新某部门某职工某年某月的基本工资
    public ResultJSON updateBasicSalaryByDepartIdAndUserIdAndDate(String departId, int userId,double bsalary, double jsalary, double wsalary, String smonth,String syear) {

        HashMap<String, Object> result = new HashMap<>();
        System.out.println( departId+" " + userId + " " +bsalary+ " " +jsalary+ " " +wsalary+ " " + smonth+ " " +syear);

        int isUpdate = salaryMapper.updateBasicSalaryByDepartIdAndUserIdAndDate(departId,userId,bsalary,jsalary,wsalary,smonth,syear);

        // 写入日志
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User user = (User) request.getSession().getAttribute("user");
        MDC.put("userId", user.getId());
        MDC.put("userName", user.getRealname());
        Log4jUtils.getLogger().info("更新职工工资。职工号："+userId+" 基本工资："+bsalary+" 职务工资："+jsalary+" 工龄工资："+ wsalary);

        result.put("isUpdate", isUpdate);
        return new ResultJSON(11, "更新某部门某职工某年某月的基本工资成功", result);

    }

}
