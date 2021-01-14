package cn.jt.service;

import cn.jt.security.pub.ResultJSON;
import org.apache.ibatis.annotations.Param;

public interface ReasonService {

    /* 获取具体变动类型列表（ 返回 map 类型，id 为键 ） */
    ResultJSON selectAllReasons();

    // 根据变动原因编号查询变动原因
    ResultJSON selectReasonById(@Param("id") int id);

    // 根据一级变动类型编号编号查询具体变动类型
    ResultJSON  selectAllReasonsByChangeId(int changeId);
}
