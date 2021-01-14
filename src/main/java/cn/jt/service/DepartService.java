package cn.jt.service;

import cn.jt.security.pub.ResultJSON;

public interface DepartService {

    // 获取部门列表，未分页
    ResultJSON selectAllDeparts();

    //获取部门列表，分页
    ResultJSON selectPagingDeparts(Integer page,Integer size);











    //添加部门信息
    ResultJSON add(String id,String name,int number,String address,String departDesc) throws Exception;

    //通过id和名字查询部门信息
    ResultJSON getByIdName(String id, String name,Integer page,Integer size);

    // 通过 id 查询部门信息
    ResultJSON getById(String id);

    //通过id删除部门信息
    ResultJSON delete(String id);

    //更新部门信息
    ResultJSON update(String id, String name, int number, String address, String departDesc);
}
