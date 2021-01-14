package cn.jt.service;

import cn.jt.pojo.Log;
import cn.jt.security.pub.ResultJSON;

import java.util.List;

public interface LogService {

    // 查询登录日志
    public ResultJSON selectLoginLog();

    // 查询系统日志
    public ResultJSON selectSystemLog();

    // 查询操作日志
    public ResultJSON selectOperateLog();

    // 删除日志根据 id
    public ResultJSON deleteByLogId(int logId);

}
