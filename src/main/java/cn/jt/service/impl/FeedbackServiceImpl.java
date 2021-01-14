package cn.jt.service.impl;

import cn.jt.mapper.DepartMapper;
import cn.jt.mapper.FeedbackMapper;
import cn.jt.pojo.Depart;
import cn.jt.pojo.Feedback;
import cn.jt.pojo.User;
import cn.jt.pojo.result.FeedbackNumber;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.FeedbackService;
import cn.jt.utils.DateUtil;
import cn.jt.utils.Log4jUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javafx.collections.transformation.FilteredList;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    FeedbackMapper feedbackMapper;

    //获取反馈列表，分页
    public ResultJSON selectPagingFeedbacks(Integer page, Integer size) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            // 查询第几页，几条记录
            PageHelper.startPage(page,size);
            // 获取反馈列表
            List<Feedback> feedbacks = feedbackMapper.selectPagingFeedbacks();
            // 将获取的列表存入 result
            PageInfo<Feedback> pageInfo = new PageInfo<>(feedbacks);
            result.put("feedbacks",pageInfo);
            return new ResultJSON(11,"获取反馈列表（分页）成功",result);
        }
        catch (Exception e) {
            return new ResultJSON(00,"获取反馈列表（分页）失败",null);
        }
    }

    // 获取反馈列表，检索（关键字，起止日期）
    public ResultJSON selectFeedbackByInfo(String input, String beginTime, String endTime) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            if (beginTime != null && beginTime != "") {
                beginTime = new DateUtil().convertChineseStandardTimeToJavaDate(beginTime);
            }
            if (endTime != null && endTime != "") {
                endTime = new DateUtil().convertChineseStandardTimeToJavaDate(endTime);
            }

            System.out.println(input);
            System.out.println(beginTime);
            System.out.println(endTime);

            List<Feedback> feedbacks = feedbackMapper.selectFeedbackByInfo(input, beginTime, endTime);
            result.put("feedbacks",feedbacks);
            return new ResultJSON(11,"查询反馈列表（检索）成功",result);
        }
        catch (Exception e) {
            return new ResultJSON(00,"系统错误，查询反馈列表（检索）失败",null);
        }
    }

    // 获取反馈列表，整合分页与检索
    public ResultJSON selectPagingFeedbacksByInfo(Integer page, Integer size, String input, String beginTime, String endTime, int userId) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{

            if (beginTime != null && !beginTime.equals("")) {
                beginTime = new DateUtil().convertChineseStandardTimeToJavaDate(beginTime);
            }
            if (endTime != null && !endTime.equals("")) {
                endTime = new DateUtil().convertChineseStandardTimeToJavaDate(endTime);
            }

            // 查询第几页，几条记录
            PageHelper.startPage(page,size);
            List<Feedback> feedbacks = feedbackMapper.selectPagingFeedbacksByInfo(input, beginTime, endTime, userId);
            // 将获取的列表存入 result
            PageInfo<Feedback> pageInfo = new PageInfo<>(feedbacks);
            result.put("feedbacks",pageInfo);
            return new ResultJSON(11,"查询反馈列表（检索）成功",result);
        }
        catch (Exception e) {
            return new ResultJSON(00,"系统错误，查询反馈列表（检索）失败",null);
        }
    }

    // 根据反馈编号删除反馈
    public ResultJSON deleteFeedbackById(int id) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            int isDelete = feedbackMapper.deleteFeedbackById(id);
            result.put("isDelete",isDelete);

            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId",user.getId());
            MDC.put("userName",user.getRealname());
            Log4jUtils.getLogger().info("删除反馈，反馈号："+ id);

            return new ResultJSON(11,"删除反馈成功",result);
        }
        catch (Exception e) {
            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId",user.getId());
            MDC.put("userName",user.getRealname());
            Log4jUtils.getLogger().error("删除反馈失败，反馈号："+ id);

            return new ResultJSON(00,"删除反馈失败",null);
        }
    }

    // 根据反馈 id 修改信息
    public ResultJSON updateFeedbackById(int id,int changeId,String description,String summarize){
        HashMap<String,Object> result = new HashMap<>();
        try{
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String submitTime = df.format(new Date());
            int isUpdate = feedbackMapper.updateFeedbackById(id, changeId, description, summarize, submitTime);
            result.put("isUpdate",isUpdate);

            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId",user.getId());
            MDC.put("userName",user.getRealname());
            Log4jUtils.getLogger().info("修改反馈（反馈号 "+ id +"）为\n变动类型："+changeId+"\n概述："+summarize
                    +"\n详述："+description);

            return new ResultJSON(11,"更新反馈信息成功",result);
        }catch (Exception e){
            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId",user.getId());
            MDC.put("userName",user.getRealname());
            Log4jUtils.getLogger().error("修改反馈失败（反馈号 "+ id +"）");

            return new ResultJSON(00,"系统错误，更新反馈信息失败",null);
        }
    }

    // 根据部门 id 和反馈状态查询指定部门员工反馈数量及员工信息
    public ResultJSON selectFeedbacksByDepartIdAndStatus(String feedbackStatus, String departId) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            // 指定部门职工反馈情况
            List<Map> departUserFeedbackMap = feedbackMapper.selectFeedbacksByDepartIdAndStatus(feedbackStatus, departId);
            result.put("departUserFeedbackMap",departUserFeedbackMap);
            return new ResultJSON(11,"查询指定部门职工反馈情况成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询指定部门职工反馈情况失败",null);
        }
    }

    // 根据用户 id 和反馈状态查询反馈列表
    public ResultJSON selectFeedbacksByUserIdAndStatus(@Param("userId")int userId,@Param("feedbackStatus")String feedbackStatus) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            // 指定部门职工，指定反馈状态
            List<Feedback> feedbacks = feedbackMapper.selectFeedbacksByUserIdAndStatus(userId, feedbackStatus);

            result.put("feedbacks",feedbacks);
            return new ResultJSON(11,"查询指定职工指定状态反馈情况成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询指定职工指定状态反馈情况失败",null);
        }
    }

    // 根据反馈 id 更新反馈处理说明，处理时间，反馈状态
    public ResultJSON updateFeedbackStatusById(int id,String resultDesc, String feedbackStatus) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dealTime = df.format(new Date());
            int isUpdate = feedbackMapper.updateFeedbackStatusById(id, resultDesc, feedbackStatus,dealTime);
            result.put("isUpdate",isUpdate);
            return new ResultJSON(11,"根据反馈 id 更新反馈处理说明，处理时间，反馈状态成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，根据反馈 id 更新反馈处理说明，处理时间，反馈状态失败",null);
        }
    }

    // 增加反馈
    public ResultJSON insertFeedback(int userId,String departId,int changeId,int recordId,String summarize,String description) {
        // 需要生成 submitTime，并插入
        HashMap<String,Object> result = new HashMap<>();
        try{
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String submitTime = df.format(new Date());
            int isInsert = feedbackMapper.insertFeedback(userId,departId,changeId,recordId,summarize,description,submitTime);
            result.put("isInsert",isInsert);

            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId",user.getId());
            MDC.put("userName",user.getRealname());
            Log4jUtils.getLogger().info("新增反馈。\n变动类型："+changeId+"\n录入号："+recordId+"\n概述："+summarize
            +"\n详述："+description);

            return new ResultJSON(11,"增加反馈成功",result);
        }catch (Exception e){
            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId",user.getId());
            MDC.put("userName",user.getRealname());
            Log4jUtils.getLogger().error("新增反馈失败");

            return new ResultJSON(00,"系统错误，增加反馈失败",null);
        }
    }


}
