package cn.jt.mapper;

import cn.jt.pojo.Role;
import org.apache.ibatis.annotations.Param;
import java.util.*;

public interface RoleMapper {

    // 通过用户 id 查询角色
    Role findByUserId(@Param("userId") int userId);

    // 通过角色 id 查询角色
    Role findByRoleId(@Param("rid")int rid);

    // 查询所有角色
    List<Role> findAll();

    // 根据用户 id 修改用户角色
    int updateByUserId(@Param("uid")int uid, @Param("rid")int rid);

    // 根据角色 id 删除角色权限
    int deleteRoleMenuByRoleId(@Param("rid")int rid);

    // 根据角色 id 删除角色对应用户记录
    int deleteRoleUserByRoleId(@Param("rid")int rid);

    // 根据角色 id 删除角色
    int deleteRoleByRoleId(@Param("rid")int rid);

    // 根据角色 id 修改信息
    int updateRoleByRoleId(@Param("rid")int rid,@Param("name")String name,@Param("nameZh")
                            String nameZh,@Param("desc") String desc);

    // 插入角色
    int insertRole(Role role);

    // 获取最大 id
    int selectMaxRoleId();

}
