package cn.jt.controller;

import cn.jt.service.ReasonService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReasonControllerTest {

    @Resource
    ReasonService reasonService;

    @Test
    void selectReasonById() {
        System.out.println(reasonService.selectReasonById(1));
    }

    @Test
    void selectAllReasonsByChangeId() {
        System.out.println(reasonService.selectAllReasonsByChangeId(1));
    }
}