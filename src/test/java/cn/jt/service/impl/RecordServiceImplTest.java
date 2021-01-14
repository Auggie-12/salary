package cn.jt.service.impl;

import cn.jt.mapper.RecordMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecordServiceImplTest {
    @Resource
    RecordMapper recordMapper;
    @Test
    void insertRecord() {
        System.out.println(recordMapper.insertRecord(1,1,"A",1,11,20,"小系","2020-11-12 12:00:00","11","2020"));
    }
}