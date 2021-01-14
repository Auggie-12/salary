package cn.jt.controller;

import cn.jt.security.pub.ResultJSON;
import cn.jt.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/depart/")
public class DepartController {

    @Resource
    DepartService departService;

    // 获取部门列表，未分页
    @RequestMapping("selectAllDeparts")
    public ResultJSON selectAllDeparts() {
        return departService.selectAllDeparts();
    }


    // 通过 id 查询部门信息
    @RequestMapping("getById")
    public ResultJSON getById(@RequestParam("id") String id) {
        return departService.getById(id);
    }

    //获取部门列表，分页
    @RequestMapping("selectPagingDeparts")
    public ResultJSON selectPagingDeparts(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "size",defaultValue = "3")Integer size) {
        return departService.selectPagingDeparts(page,size);
    }


















    //根据部门id和名字查询信息
    @PostMapping("query")
    public ResultJSON getByIdName(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "size",defaultValue = "3")Integer size) throws Exception{
        return departService.getByIdName(id,name,page,size);
    }



    //新增部门信息
    @PostMapping("add")
    public ResultJSON add(@RequestParam("id") String id,@RequestParam("name") String name,
                          @RequestParam(value = "number",defaultValue = "0") int number,
                          @RequestParam("address") String address,
                          @RequestParam("departDesc") String departDesc) throws Exception {
        return departService.add(id, name, number, address, departDesc);
    }

    @RequestMapping("delete")
    public ResultJSON delete(@RequestParam("id")String id)throws Exception{
        return departService.delete(id);
    }

    @PostMapping("update")
    public ResultJSON update(@RequestParam("id") String id,@RequestParam("name") String name,
                             @RequestParam(value = "number",defaultValue = "0") int number,
                             @RequestParam("address") String address,
                             @RequestParam("departDesc") String departDesc)throws Exception{
        return departService.update(id,name,number,address,departDesc);
    }
}
