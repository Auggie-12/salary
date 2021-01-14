package cn.jt.mapper;

import cn.jt.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    // 根据职工号获取用户信息
    public User findById(int id);

    // 获取所有用户信息
    public List<User> findAll();

    // 更新用户信息（通过 id）
    public int updateUserByUserId(User user);

    // 新增用户
    public int insertUser(User user);

    // 获取最大用户 id
    public int selectMaxUserId();

    // 为指定用户分配角色
    public int insertUserRole(@Param("uid") int uid,@Param("rid") int rid);

    // 删除用户
    public int deleteUserByUserId(int uid);

    // 根据部门代号获取职工列表
    public List<User> selectUsersByDepartId(String departId);

}
