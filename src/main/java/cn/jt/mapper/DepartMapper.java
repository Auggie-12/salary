package cn.jt.mapper;
import cn.jt.pojo.Depart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartMapper {

    // 获取列表，未分页
    List<Depart> selectAllDeparts();

    // 根据 id 查询部门信息
    Depart getById(@Param("id")String id);

    // 获取列表，分页
    List<Depart> selectPagingDeparts();









    //新增部门
    void add(@Param("id") String id,@Param("name") String name,
                    @Param("number") int number,@Param("address") String address,
                    @Param("departDesc") String departDesc);

    //根据名字查询部门信息
    Depart getByName(@Param("name")String name);

    //根据id和名字查询部门信息
    List<Depart> getByIdName(@Param("id") String id,@Param("name") String name);

    //根据id删除部门信息
    void delete(@Param("id")String id);

    //更新部门信息
    void update(@Param("id") String id,@Param("name") String name,
                       @Param("number") int number,@Param("address") String address,
                       @Param("departDesc") String departDesc);

    //查询部门的id信息
    String getId(@Param("name") String name);
}
