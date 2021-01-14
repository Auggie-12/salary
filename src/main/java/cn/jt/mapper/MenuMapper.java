package cn.jt.mapper;

import cn.jt.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.*;

public interface MenuMapper {

    // 查询所有权限
    List<Menu> findAllMenus();

    // 根据权限 id 查询指定权限
    Menu findMenuByMenuId(@Param("mid") int mid);

    // 查询所有一级权限
    List<Menu> findAllParentMenus();

    // 查询指定角色的权限
    List<Menu> findMenusByRoleId(@Param("rid") int rid);

    // 查询指定用户的权限
    List<Menu> findMenusByUserId(@Param("uid") int uid);

    // 为指定角色分配一个权限
    int saveMenu(@Param("rid") int rid , @Param("mid") int mid);

    // 删除指定角色的所有权限
    int deleteAllMenusByRoleId(@Param("rid") int rid);

    // 查询指定角色权限数量
    int countMenusByRoleId(@Param("rid") int rid);

    // 查询所有角色权限数量
    List<Map> countMenus();
}
