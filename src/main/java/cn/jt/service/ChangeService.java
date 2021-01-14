package cn.jt.service;


import cn.jt.security.pub.ResultJSON;
import org.springframework.stereotype.Service;

@Service
public interface ChangeService {

    // 获取变动类型列表（ map 形式，以 id 键）
    ResultJSON list();

    //添加变动类型
    ResultJSON add(String name);

    //修改变动类型
    ResultJSON edit(int id,String name);

    //删除变动类型
    ResultJSON delete(int id);

    // 获取级联菜单
    ResultJSON selectChangeCascader();

    // 获取变动原因与具体原因 map
    ResultJSON selectChangeReasons();
}
