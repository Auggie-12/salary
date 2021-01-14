package cn.jt.pojo;

import lombok.Data;

@Data
public class Record {

    private int id;

    private int userId;

    private int checkerId;

    private String departId;

    private int changeId;

    private int isEdit;

    private int reasonId;

    private String checkerName;

    private String recordTime;

    private double money;

    private String rmonth;

    // 数据库中为 Year 类型
    private String ryear;

}
