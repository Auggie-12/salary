package cn.jt.mapper;

import cn.jt.service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecordMapperTest {

    @Resource
    RecordMapper recordMapper;
    @Test
    void selectPersonBookChangeReason() {

        System.out.println(recordMapper.selectPersonBookChangeReason(1,1,"2020","12"));
    }

    @Test
    void selectPersonBookChangeReasonPlus() {
        System.out.println(recordMapper.selectPersonBookChangeReasonPlus(1,2,"2020","12"));
    }

    @Test
    void testSelectPersonBookChangeReason() {
    }
}