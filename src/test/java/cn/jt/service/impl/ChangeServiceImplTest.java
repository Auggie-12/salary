package cn.jt.service.impl;

import cn.jt.mapper.ChangeMapper;
import cn.jt.pojo.Change;
import cn.jt.service.ChangeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChangeServiceImplTest {

    @Resource
    ChangeMapper changeMapper;

    @Resource
    ChangeService changeService;

    @Test
    void list() {
        List<Change> changes = changeMapper.list();
        Map map = new HashMap();
        for (int i = 0; i < changes.size() ; i++) {
            map.put(changes.get(i).getId(),changes.get(i));
        }
        System.out.println(map);
        System.out.println(map.get(1));
    }

    @Test
    void selectChangeCascader() {
        changeService.selectChangeCascader();
    }
}