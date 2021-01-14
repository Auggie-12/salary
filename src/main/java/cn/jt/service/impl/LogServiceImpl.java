package cn.jt.service.impl;

import cn.jt.mapper.LogMapper;
import cn.jt.pojo.Log;
import cn.jt.pojo.Role;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    LogMapper logMapper;

    // 查询登录日志
    @Override
    public ResultJSON selectLoginLog() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Log> logs = logMapper.selectLoginLog();
            result.put("logs",logs);
            return new ResultJSON(11,"查询登录日志成功",result);
        }catch (Exception e) {
            return new ResultJSON(00, "系统错误，查询失败", null);
        }
    }

    // 查询系统日志
    public ResultJSON selectSystemLog() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Log> logs = logMapper.selectSystemLog();
            result.put("logs",logs);
            return new ResultJSON(11,"查询系统日志成功",result);
        }catch (Exception e) {
            return new ResultJSON(00, "系统错误，查询失败", null);
        }
    }

    // 查询操作日志
    public ResultJSON selectOperateLog() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Log> logs = logMapper.selectOperateLog();
            result.put("logs",logs);
            return new ResultJSON(11,"查询操作日志成功",result);
        }catch (Exception e) {
            return new ResultJSON(00, "系统错误，查询失败", null);
        }
    }

    // 删除日志根据 id
    public ResultJSON deleteByLogId(int logId) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            int isDeleted = logMapper.deleteByLogId(logId);
            result.put("isDeleted",isDeleted);
            return new ResultJSON(11,"删除日志成功",result);
        }catch (Exception e) {
            return new ResultJSON(00, "系统错误，删除失败", null);
        }
    }
}
