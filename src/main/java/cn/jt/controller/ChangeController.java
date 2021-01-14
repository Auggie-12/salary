package cn.jt.controller;

import cn.jt.mapper.ChangeMapper;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.ChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/change/")
public class ChangeController {

    @Autowired
    ChangeService changeService;

    //获取列表页面
    @RequestMapping("list")
    public ResultJSON list() {
        return changeService.list();
    }

    // 获取变动类型级联菜单
    @RequestMapping("selectChangeCascader")
    public ResultJSON selectChangeCascader() {
        return changeService.selectChangeCascader();
    }

    // 获取变动原因与具体原因 map
    @RequestMapping("selectChangeReasons")
    public ResultJSON selectChangeReasons() {
        return changeService.selectChangeReasons();
    }















    //添加变动类型页面
    @PostMapping("add")
    public ResultJSON add( @RequestParam("name")String name) throws  Exception {
       return changeService.add(name);
    }
    //修改变动类型页面
    @PostMapping("edit")
    public ResultJSON edit(@RequestParam("id") int id,@RequestParam("name")String name) throws  Exception {
        return changeService.edit(id,name);
    }
    //删除变动类型页面
    @RequestMapping("delete")
    public ResultJSON delete(@RequestParam("id")int id) throws  Exception {
        return changeService.delete(id);
    }
}
