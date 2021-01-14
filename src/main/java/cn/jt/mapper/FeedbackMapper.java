package cn.jt.mapper;

import cn.jt.pojo.Depart;
import cn.jt.pojo.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FeedbackMapper {

    // 获取反馈列表，分页
    List<Feedback> selectPagingFeedbacks();

    // 获取反馈列表，检索（关键字，起止日期）
    List<Feedback> selectFeedbackByInfo(@Param("input")String input, @Param("beginTime")String beginTime,@Param("endTime")String endTime);






    // 获取反馈列表，整合分页与检索
    List<Feedback> selectPagingFeedbacksByInfo(
            @Param("input")String input,
            @Param("beginTime")String beginTime,
            @Param("endTime")String endTime,
            @Param("userId")int userId);


    // 根据反馈编号删除反馈
    int deleteFeedbackById(@Param("id") int id);

    // 根据反馈 id 修改信息
    int updateFeedbackById(@Param("id")int id,@Param("changeId")int changeId,@Param("description")
            String description,@Param("summarize") String summarize,@Param("submitTime") String submitTime);

    // 根据部门 id 和反馈状态查询指定部门员工反馈数量及员工信息
    List<Map> selectFeedbacksByDepartIdAndStatus(@Param("feedbackStatus")String feedbackStatus,@Param("departId")String departId);

    // 根据用户 id 和反馈状态查询反馈列表
    List<Feedback> selectFeedbacksByUserIdAndStatus(@Param("userId")int userId,@Param("feedbackStatus")String feedbackStatus);

    // 根据反馈 id 更新反馈处理说明，处理时间，反馈状态
    int updateFeedbackStatusById(@Param("id")int id,@Param("resultDesc")String resultDesc,@Param("feedbackStatus")String feedbackStatus,@Param("dealTime")String dealTime);

    // 增加反馈
    int insertFeedback(
            int userId,String departId,
            int changeId,int recordId,
            String summarize,String description,
            String submitTime);

//    int insertFeedback(
//            @Param("userId")int userId,@Param("departId") String departId,
//            @Param("changeId") int changeId,@Param("recordId")int recordId,
//            @Param("summarize")String summarize,@Param("description")String description,
//            @Param("submit_time")String submitTime);

    // 根据反馈状态查询
//    int selectFeedbacksByDepartIdAndStatus(@Param("feedbackStatus")int feedbackStatus,@Param("departId")String departId);



}
