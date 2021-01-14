package cn.jt.service;

import cn.jt.security.pub.ResultJSON;
import org.apache.ibatis.annotations.Param;

public interface FeedbackService {

    //获取部门列表，分页
    ResultJSON selectPagingFeedbacks(Integer page, Integer size);

    // 获取反馈列表，检索（关键字，起止日期）
    ResultJSON selectFeedbackByInfo(String input, String beginTime, String endTime);

    // 获取反馈列表，整合分页与检索
    ResultJSON selectPagingFeedbacksByInfo(Integer page, Integer size, String input, String beginTime, String endTime, int userId);


    // 根据反馈编号删除反馈
    ResultJSON deleteFeedbackById(int id);

    // 根据反馈 id 修改信息
    ResultJSON updateFeedbackById(int id,int changeId,String description,String summarize);

    // 根据部门 id 和反馈状态查询指定部门员工反馈数量及员工信息
    ResultJSON selectFeedbacksByDepartIdAndStatus(String feedbackStatus, String departId);

    // 根据用户 id 和反馈状态查询反馈列表
    ResultJSON selectFeedbacksByUserIdAndStatus(int userId, String feedbackStatus);

    // 根据反馈 id 更新反馈处理说明，处理时间，反馈状态
    ResultJSON updateFeedbackStatusById(int id,String resultDesc,String feedbackStatus);

    // 增加反馈
    ResultJSON insertFeedback(int userId,String departId,int changeId,int recordId,String summarize,String description);
}
