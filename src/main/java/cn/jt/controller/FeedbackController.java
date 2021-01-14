package cn.jt.controller;

import cn.jt.security.pub.ResultJSON;
import cn.jt.service.FeedbackService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/feedback/")
public class FeedbackController {

    @Resource
    FeedbackService feedbackService;

    //获取反馈列表，分页（默认 5 条）
    @RequestMapping("selectPagingFeedbacks")
    public ResultJSON selectPagingDeparts(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "size",defaultValue = "5")Integer size) {
        return feedbackService.selectPagingFeedbacks(page,size);
    }

    // 获取反馈列表，检索（关键字，起止日期）
    @RequestMapping("selectFeedbackByInfo")
    ResultJSON selectFeedbackByInfo(
            @RequestParam(value = "input", defaultValue = "")String input,
            @RequestParam(value = "beginTime", defaultValue = "")String beginTime,
            @RequestParam(value = "endTime", defaultValue = "")String endTime) {
        return feedbackService.selectFeedbackByInfo(input, beginTime, endTime);
    }

    // 获取反馈列表，整合分页与检索
    @RequestMapping("selectPagingFeedbacksByInfo")
    public ResultJSON selectPagingFeedbacksByInfo(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "size",defaultValue = "5")Integer size,
            @RequestParam(value = "input", defaultValue = "")String input,
            @RequestParam(value = "beginTime")String beginTime,
            @RequestParam(value = "endTime")String endTime,
            @RequestParam(value = "userId")int userId) {
        return feedbackService.selectPagingFeedbacksByInfo(page, size, input, beginTime, endTime, userId);
    }

    // 根据反馈编号删除反馈
    @RequestMapping("deleteFeedbackById")
    public ResultJSON deleteFeedbackById(@RequestParam("id") int id) {
        return feedbackService.deleteFeedbackById(id);
    }

    // 根据反馈 id 修改信息
    @PostMapping("updateFeedbackById")
    @ResponseBody
    public ResultJSON updateFeedbackById(int id,int changeId,String description,String summarize) {
        return feedbackService.updateFeedbackById(id, changeId, description, summarize);
    }

    // 根据部门 id 和反馈状态查询指定部门员工反馈数量及员工信息
    @RequestMapping("selectFeedbacksByDepartIdAndStatus")
    public ResultJSON selectFeedbacksByDepartIdAndStatus(@RequestParam("feedbackStatus") String feedbackStatus, @RequestParam("departId") String departId) {
        return feedbackService.selectFeedbacksByDepartIdAndStatus(feedbackStatus, departId);
    }

    // 根据用户 id 和反馈状态查询反馈列表
    @RequestMapping("selectFeedbacksByUserIdAndStatus")
    ResultJSON selectFeedbacksByUserIdAndStatus(@RequestParam("userId") int userId,@RequestParam("feedbackStatus") String feedbackStatus) {
        return feedbackService.selectFeedbacksByUserIdAndStatus(userId, feedbackStatus);
    }

    // 根据反馈 id 更新反馈处理说明，处理时间，反馈状态
    @PostMapping("updateFeedbackStatusById")
    @ResponseBody
    ResultJSON updateFeedbackStatusById(int id,String resultDesc,String feedbackStatus) {
        return feedbackService.updateFeedbackStatusById(id, resultDesc, feedbackStatus);
    }

    // 增加反馈
    @PostMapping("insertFeedback")
    @ResponseBody
    ResultJSON insertFeedback(Integer userId,String departId,Integer changeId,Integer recordId,String summarize,String description){
        System.out.println("u:"+userId);
        return feedbackService.insertFeedback(userId, departId, changeId, recordId, summarize, description);
    }
}
