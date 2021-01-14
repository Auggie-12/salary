package cn.jt.mapper;

import cn.jt.pojo.Change;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChangeMapper {

    // 获取变动类型列表
    List<Change> list();



















    //根据id返回单个列表
    Change getById(@Param("id")int id);
    //根据变动名返回单个列表
     Change getByName(@Param("name")String name);
    //插入新变动类型
    void add(@Param("id")int id, @Param("name")String name);
    //删除变动类型
    void delete(@Param("id")int id);
    //更改变动类型名
    void update(@Param("id")int id,@Param("name")String name);
    //获取最后一条数据id
    int getId();
}
