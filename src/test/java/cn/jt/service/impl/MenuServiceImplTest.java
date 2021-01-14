package cn.jt.service.impl;

import cn.jt.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuServiceImplTest {

    @Resource
    MenuMapper menuMapper;

    @Test
    void countMenu() {
        List<Map> countMap = menuMapper.countMenus();
        Map map = new HashMap();
        System.out.println(countMap);
        for (int i = 0; i < countMap.size(); i++) {
            System.out.println(countMap.get(i));
            System.out.println(countMap.get(i).values());
            System.out.println(countMap.get(i).values().toArray()[0]);
            Number cnt = (Number) countMap.get(i).values().toArray()[0];
            Number rid = (Number) countMap.get(i).values().toArray()[1];
            map.put(rid, cnt);
        }
        System.out.println(map);
    }
}