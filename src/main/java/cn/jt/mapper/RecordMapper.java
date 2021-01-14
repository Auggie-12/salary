package cn.jt.mapper;

import cn.jt.pojo.Feedback;
import cn.jt.pojo.Record;
import cn.jt.pojo.result.ChangeReason;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.*;

@Mapper
public interface RecordMapper {

    // 根据录入编号查询录入记录
    Record selectRecordById(@Param("id")int id);

    // 根据职工号和具体变动类型（reason）号 ... 查询（加年份，加月份）
    List<Record> selectReasonsByUserIdAndReasonId(
            @Param("userId")int userId,
            @Param("reasonId")int reasonId,
            @Param("rmonth")String rmonth,
            @Param("ryear")String ryear);

    // 根据职工号和具体变动类型（reason）号查询最小年份和最大年份
    Map selectMaxAndMinYearByUserIdAndReasonId(
            @Param("userId")int userId,
            @Param("reasonId")int reasonId);

    // 获取某名职工某年某月某一级变动类型（如津贴）下各二级变动类型总额
    List<ChangeReason> selectPersonBookChangeReason(
            @Param("userId")int userId,
            @Param("changeId")int changeId,
            @Param("ryear")String ryear,
            @Param("rmonth")String rmonth );
    // 获取没有录入金额的变动类型 ，和 selectPersonBookChangeReason 结合使用
    List<ChangeReason> selectPersonBookChangeReasonPlus(
            @Param("userId")int userId,
            @Param("changeId")int changeId,
            @Param("ryear")String ryear,
            @Param("rmonth")String rmonth );

    // 新增一条录入记录
    int insertRecord(
            @Param("userId")int userId,
            @Param("checkerId")int checkerId,
            @Param("departId")String departId,
            @Param("changeId")int changeId,
            @Param("reasonId")int reasonId,
            @Param("money")double money,
            @Param("checkerName")String checkerName,
            @Param("recordTime")String recordTime,
            @Param("rmonth")String rmonth,
            @Param("ryear")String ryear);


    // 查询录入列表，整合分页与检索
    List<Record> selectPagingRecordsByInfo(
            @Param("input")String input,
            @Param("userId")int userId,
            @Param("checkerId")int checkerId,
            @Param("departId")String departId,
            @Param("changeId")int changeId,
            @Param("reasonId")int reasonId,
            @Param("beginRyear")String beginRyear,
            @Param("endRyear")String endRyear,
            @Param("beginRmonth")String beginRmonth,
            @Param("endRmonth")String endRmonth
            );

    // 更新
    int updateRecordById(
            @Param("id")int id,
            @Param("changeId")int changeId,
            @Param("reasonId")int reasonId,
            @Param("money")double money);
}
