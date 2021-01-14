package cn.jt.pojo;

import lombok.Data;

@Data
public class Role {

    private int id;

    private String name;

    private String nameZh;

    private String createTime;

    private int enable; // 是否可用

    private String desc; // 描述

    private int level; // 权限值

}
