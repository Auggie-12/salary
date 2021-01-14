package cn.jt.controller;

import cn.jt.security.pub.ResultJSON;
import cn.jt.service.LogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/log")
public class LogController {

    @Resource
    LogService logService;

    // 查询登录日志
    @RequestMapping("selectLoginLog")
    public ResultJSON selectLoginLog() {
        return logService.selectLoginLog();
    }

    // 查询系统日志
    @RequestMapping("selectSystemLog")
    public ResultJSON selectSystemLog() {
        return logService.selectSystemLog();
    }

    // 查询操作日志
    @RequestMapping("selectOperateLog")
    public ResultJSON selectOperateLog() {
        return logService.selectOperateLog();
    }

    // 删除日志根据 id
    @RequestMapping("deleteByLogId")
    public ResultJSON deleteByLogId(@RequestParam("logId") int logId) {
        return logService.deleteByLogId(logId);
    }
}
