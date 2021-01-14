package cn.jt.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Log {

    private int logId;              // 主键

    private int userId;             // 当前操作用户 id

    private String userName;        // 当前操作用户姓名

    private String projectName;     // 项目名称

    private String createDate;      // 操作时间

    private String level;           // 日志等级

    private String fileName;        // 对应文件名

    private String allCategory;     // 对应文件路径

    private String line;            // 对应文件的行数

    private String message;         // 日志描述

}
