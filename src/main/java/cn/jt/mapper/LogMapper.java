package cn.jt.mapper;

import cn.jt.pojo.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogMapper {

    // 查询登录日志
    List<Log> selectLoginLog();

    // 查询系统日志
    List<Log> selectSystemLog();

    // 查询操作日志
    List<Log> selectOperateLog();

    // 删除日志根据 id
    int deleteByLogId(@Param("logId") int logId);

}
