package cn.jt.controller;

import cn.jt.mapper.LogMapper;
import cn.jt.pojo.Log;
import cn.jt.service.LogService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class LogControllerTest {

    @Resource
    LogMapper logMapper;

    @Test
    void selectLoginLog() {
        List<Log> logs = logMapper.selectLoginLog();
        System.out.println(logs.get(0));
    }
    @Test
    void test() {
        System.out.println("hello\n2");
    }
}