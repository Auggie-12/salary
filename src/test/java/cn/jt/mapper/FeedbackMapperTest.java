package cn.jt.mapper;

import cn.hutool.core.date.DateTime;
import cn.jt.pojo.Feedback;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
class FeedbackMapperTest {

    @Resource
    FeedbackMapper feedbackMapper;
    private Date d;

    @Test
    // 查询反馈列表，检索
    void selectFeedbackByInfo() {

        String input = "错误";
        String beginTime = "2020.09.12 16:00:00";
        String endTime = "2020.12.24 12:00:00";




        List<Feedback> feedbacks = feedbackMapper.selectFeedbackByInfo(input, beginTime, endTime);

        System.out.println(feedbacks);

    }
}