package cn.jt.pojo.result;

import lombok.Data;

@Data
public class BasicSalary {

    private int userId;

    private String departName;

    private String realname;

    private int isEdit;             // 编辑

    private double bsalary;         // 基本工资

    private double jsalary;         // 职务工资

    private double wsalary;         // 工龄工资

}
