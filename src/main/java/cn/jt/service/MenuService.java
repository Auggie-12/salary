package cn.jt.service;

import cn.jt.pojo.MenuRole;
import cn.jt.security.pub.ResultJSON;
import org.springframework.stereotype.Service;

@Service
public interface MenuService {

    // 查询所有权限
    ResultJSON findAllMenus();

    // 根据权限 id 查询指定权限
    ResultJSON findMenuByMenuId(int mid);

    // 查询所有一级权限
    ResultJSON findAllParentMenus();

    // 查询指定角色的权限
    ResultJSON findMenusByRoleId(int rid);

    // 查询指定用户的权限
    ResultJSON findMenusByUserId(int uid);

    // 为指定角色分配一个权限
    ResultJSON saveMenus(MenuRole[] menuRole);

    // 删除角色的所有权限
    ResultJSON deleteAllMenusByRoleId(int rid);

    // 查询角色权限数量
    ResultJSON countMenusByRoleId(int rid);

    // 查询所有角色权限数量
    ResultJSON countMenus();


}
