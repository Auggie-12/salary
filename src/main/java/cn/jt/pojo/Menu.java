package cn.jt.pojo;

import lombok.Data;

@Data
public class Menu {

    private int id;             // 主键

    private String url;         // 匹配路径

    private String path;        // 访问地址

    private String component;   // 组件名称

    private String name;        // 权限名称

    private int keepAlive;      // 是否可用

    private int requireAuth;    // 是否需要验证

    private int parentId;       // 父级权限

}
