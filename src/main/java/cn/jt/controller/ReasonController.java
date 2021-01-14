package cn.jt.controller;

import cn.jt.security.pub.ResultJSON;
import cn.jt.service.ReasonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/reason/")
public class ReasonController {

    @Resource
    ReasonService reasonService;

    /* 获取具体变动类型列表（ 返回 map 类型，id 为键 ） */
    @RequestMapping("selectAllReasons")
    public ResultJSON selectAllReasons() {
        return reasonService.selectAllReasons();
    }

    // 根据变动原因编号查询变动原因
    @RequestMapping("selectReasonById")
    public ResultJSON selectReasonById(@RequestParam("id") int id) {
        return reasonService.selectReasonById(id);
    }

    // 根据一级变动类型编号编号查询具体变动类型
    @RequestMapping("selectAllReasonsByChangeId")
    public ResultJSON  selectAllReasonsByChangeId(@RequestParam("changeId")int changeId){
        return reasonService.selectAllReasonsByChangeId(changeId);
    }
}
