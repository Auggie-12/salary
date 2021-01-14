package cn.jt.pojo.result;

import lombok.Data;

import java.util.*;

// 个人台账
@Data
public class PersonBook {

    private String month;       // 月份

    private double bsalary;     // 基本工资

    private double jsalary;     // 职务工资

    private double wsalary;     // 工龄工资

    private List<ChangeReason> changes1;  // 津贴列表

    private double sum1;        // 津贴总计

    private List<ChangeReason> changes2;  // 补贴列表

    private double sum2;        // 补贴总计

    private double ssalary;     // 应发工资

    private List<ChangeReason> changes3;  // 扣款列表

    private double sum3;        // 扣款总计

    private double fsalary;     // 实发工资

    private double ptax;        // 个人所得税

    private double tsalary;      // 税后工资

}
