package cn.jt.service;

import cn.jt.security.pub.ResultJSON;

public interface RoleService {

    // 通过用户 id 查询角色
    ResultJSON findByUserId(int userId);

    // 通过角色 id 查询角色
    ResultJSON findByRoleId(int rid);

    // 查询所有角色
    ResultJSON findAll();

    // 根据用户 id 修改用户角色
    ResultJSON updateByUserId(int uid, int rid);

    // 根据角色 id 删除角色
    ResultJSON deleteRoleByRoleId(int rid);

    // 根据角色 id 修改信息
    ResultJSON updateRoleByRoleId(int rid,String name,String nameZh,String desc);

    // 添加角色
    ResultJSON insertRole(String name,String nameZh, String desc);

    // 获取最大 id
    ResultJSON selectMaxRoleId();
}
