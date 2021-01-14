package cn.jt.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
public class User implements UserDetails {

    private int id;                 // 职工号

    private String pwd;             // 密码

    private String departId;        // 部门代号

    private String ident;           // 身份证号

    private String realname;        // 真实姓名

    private String nickname;        // 昵称

    private String tell;            // 电话

    private int age;                // 年龄

    private String sex;             // 性别

    private String introduction;    // 介绍

    private String email;           // 邮箱

    private String headImg;         // 头像

    private int workAge;            // 工龄

    private List<Menu> menus;       // 权限集合


    /* 最关键的一个，返回用户所有权限 */
    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Menu menu : menus) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + menu.getName()));
        }
        return authorities;
    }

    /* 返回密码 */
    @Override
    public String getPassword() {
        return pwd;
    }

    /* 返回用户名（登录字段） */
    @Override
    public String getUsername() {
        return id+"";
    }


    /* 下面 4 个方法，如果系统没有要求，直接返回 true，表示不检查这些东西 */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
