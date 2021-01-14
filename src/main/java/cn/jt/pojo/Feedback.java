package cn.jt.pojo;

import lombok.Data;

@Data
public class Feedback {

    private int id;

    private int userId;

    private String departId;

    private int changeId;

    private int recordId;

    private String summarize;

    private String description;

    private String submitTime;

    private String dealTime;

    private String feedbackStatus;

    private String resultDesc;

}
