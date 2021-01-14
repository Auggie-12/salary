package cn.jt.mapper;

import cn.jt.pojo.Reason;
import cn.jt.pojo.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.*;

@Mapper
public interface ReasonMapper {

    // 列表
    List<Reason> selectAllReasons();

    // 根据变动原因编号查询变动原因
    Reason selectReasonById(@Param("id")int id);

    // 根据一级变动类型编号编号查询具体变动类型
    List<Reason> selectAllReasonsByChangeId(@Param("changeId")int changeId);
}
