package cn.jt.service;

import cn.jt.security.pub.ResultJSON;

public interface RecordService {

    // 根据录入编号查询录入记录
    ResultJSON selectRecordById(int id);

    // 根据职工号和具体变动类型（reason）号 ... 查询
    ResultJSON  selectReasonsByUserIdAndReasonId(int userId,int reasonId,String rmonth,String ryear);

    // 根据职工号和具体变动类型（reason）号查询最小年份和最大年份
    ResultJSON selectMaxAndMinYearByUserIdAndReasonId(int userId,int reasonId);

    // 新增一条录入记录
    ResultJSON insertRecord(int userId,int checkerId,int changeId,int reasonId,
         double money,String checkerName,String rmonth,String ryear);

    // 为部门所有成员新增录入信息
    ResultJSON insertDepartRecord(String departId,int checkerId,int changeId,int reasonId,
                                         double money,String checkerName,String rmonth,String ryear);



    // 获取录入列表，整合分页与检索
    ResultJSON selectPagingRecordsByInfo(
            Integer page, Integer size,
            String input,
            Integer  userId, Integer checkerId, String departId,
            Integer changeId, Integer reasonId,
            String beginRyear, String endRyear,
            String beginRmonth, String endRmonth );

    // 更新
    ResultJSON updateRecordById( int id, int changeId, int reasonId, double money);
}
