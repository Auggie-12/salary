package cn.jt.service.impl;

import cn.jt.mapper.FeedbackMapper;
import cn.jt.pojo.Feedback;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedbackServiceImplTest {

    @Resource
    FeedbackMapper feedbackMapper;

    @Test
    void selectPagingFeedbacks() {

        // 查询第几页，几条记录
        PageHelper.startPage(1,5);
        // 获取反馈列表
        List<Feedback> feedbacks = feedbackMapper.selectPagingFeedbacks();
        // 将获取的列表存入 result
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbacks);
        System.out.println(pageInfo.getList());

    }

    @Test
    void updateFeedbackById() {
        feedbackMapper.updateFeedbackById(1, 1, "12", "23","2020.12.11 12:34:23");
    }

    @Test
    void test1() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String submitTime = df.format(new Date());
        System.out.println(submitTime);
    }

    @Test
    void selectFeedbacksByDepartIdAndStatus() {

        // 指定部门职工反馈情况
        List<Map> departUserFeedbackMap = feedbackMapper.selectFeedbacksByDepartIdAndStatus("0", "C");
        System.out.println(departUserFeedbackMap);

    }

    @Test
    void selectFeedbacksByUserIdAndStatus() {
        // 指定部门职工，指定反馈状态
        List<Feedback> feedbacks = feedbackMapper.selectFeedbacksByUserIdAndStatus(1, "0");
        System.out.println(feedbacks.size());
    }
}