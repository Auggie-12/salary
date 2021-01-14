package cn.jt.service.impl;

import cn.jt.mapper.ChangeMapper;
import cn.jt.mapper.ReasonMapper;
import cn.jt.pojo.Change;
import cn.jt.pojo.Reason;
import cn.jt.pojo.result.ChangeCascader;
import cn.jt.pojo.result.ReasonCascader;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.ChangeService;
import cn.jt.utils.Log4jUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChangeServiceImpl implements ChangeService {

    @Resource
    ChangeMapper changeMapper;

    @Resource
    ReasonMapper reasonMapper;

    /* 获取变动类型列表（ 返回 map 类型，id 为键 ） */
    public ResultJSON list() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Change> changes = changeMapper.list();
            Map changeMap = new HashMap();
            Map changesMap = new HashMap();
            for (int i = 0; i < changes.size() ; i++) {
                changeMap.put(""+changes.get(i).getId(),changes.get(i));
                changesMap.put(""+changes.get(i).getId(),changes.get(i));
            }
            result.put("changes", changes);
            result.put("changeMap", changeMap);
            result.put("changesMap", changesMap);
            return new ResultJSON(11,"获取变动类型列表成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，获取变动类型列表失败",null);
        }
    }

    // 获取变动类型级联菜单
    public ResultJSON selectChangeCascader() {
        // 存储返回结果
        HashMap<String, Object> result = new HashMap<>();
        // 待返回数据
        List<ChangeCascader> changeCascaderList = new ArrayList();
        // 获取变动类型
        List<Change> changeList = changeMapper.list();
        // 遍历变动类型
        for (int i = 0; i < changeList.size(); i++) {
            // 当前变动类型
            Change change = changeList.get(i);
            // 新加一组
            ChangeCascader changeCascader = new ChangeCascader();
            // 设置绑定值为类型编号
            changeCascader.setValue(change.getId());
            // 设置显示文本
            changeCascader.setLabel(change.getName());
            // 添加子选项
            List<ReasonCascader> children = new ArrayList();
            // 获取当前变动类型的所有具体类型
            List<Reason> reasons = reasonMapper.selectAllReasonsByChangeId(change.getId());
            // 遍历具体类型，添加到子选项中
            System.out.println(reasons);
            for (int j = 0; j < reasons.size(); j++) {
                // 当前具体变动类型
                Reason reason = reasons.get(j);
                // 新加一个子选项
                ReasonCascader reasonCascader = new ReasonCascader();
                // 设置绑定值为具体类型编号
                reasonCascader.setValue(reason.getId());
                // 设置显示文本
                reasonCascader.setLabel(reason.getDescription());
                // 添加到父选项中
                children.add(reasonCascader);
            }
            // 设置子选项
            changeCascader.setChildren(children);
            // 添加到根
            changeCascaderList.add(changeCascader);
        }
        System.out.println(changeCascaderList);
        // 加入结果
        result.put("changeCascaderList",changeCascaderList);
        // 成功返回
        return new ResultJSON(11, "获取变动类型级联菜单成功", result);

    }


    // 获取变动原因与具体原因 map
    public ResultJSON selectChangeReasons() {
        // 存储返回结果
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> changeReasons = new HashMap<>();
        // 获取所有变动类型
        List<Change> changes = changeMapper.list();
        // 遍历
        for (int i = 0; i < changes.size() ; i++) {
            List<Reason> reasons = reasonMapper.selectAllReasonsByChangeId(changes.get(i).getId());
            changeReasons.put(changes.get(i).getId()+"",reasons);
        }
        result.put("changeReasons",changeReasons);
        return new ResultJSON(11,"获取selectChangeReasons列表成功",result);

    }














    public ResultJSON add(String name) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            if(name.isEmpty()){
                return new ResultJSON(0,"请输入类型名！",null);
            }
            //自动获取变动id,为最后一条数据id+1
            int count = changeMapper.getId()+1;
            //查找是否有相同工资变动类型
            Change changes1 = changeMapper.getByName(name);
            if(changes1 != null){
                //将存在的变动类型信息存入result
                return new ResultJSON(00,"工资类型已经存在，无法重复添加",null);
            }
            //添加变动类型
            changeMapper.add(count,name);
            //返回刚刚添加的变动类型
            Change changes = changeMapper.getById(count);
            //获取添加的变动类型放入result中
            result.put("changelist",changes);
            //日志信息
            Log4jUtils.getLogger().info("新增工资变动类型,类型名：'"+name+"'");
            return new ResultJSON(11,"新增工资变动类型成功",result);
        }catch (Exception e){
            //日志信息
            Log4jUtils.getLogger().error("新增工资类型时发生系统错误");
            return new ResultJSON(00,"系统错误，新增工资类型失败",null);
        }
    }

    public ResultJSON edit(int id, String name) {
        //返回对象
        ResultJSON ResultJSON = new ResultJSON();
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            //根据id查找要更改的id是否存在
            Change changes1 = changeMapper.getById(id);
            if(changes1==null){
                return new ResultJSON(00,"更改失败，id不存在",null);
            }
            String oldName = changes1.getName();
            //根据变动名查找要变动的名字是否已经存在
            Change changes2 = changeMapper.getByName(name);
            if(changes2 != null && changes2.equals(changes1)){
                //将存在的变动类型信息存入result
                result.put("changelist",changes2);
                return new ResultJSON(01,"更改失败,工资类型已存在",result);
            }
            //更改id对应的变动类型
            changeMapper.update(id,name);
            //获取刚刚更改的变动类型信息
            Change changes3 = changeMapper.getById(id);
            //将更改的变动类型信息存入result
            result.put("changelist",changes3);
            //日志信息
            Log4jUtils.getLogger().warn("更改了工资变动类型,将类型名从'"+oldName+"',改为'"+name+"'");
            return new ResultJSON(11,"更改工资变动类型成功",result);
        }catch (Exception e){
            //日志信息
            Log4jUtils.getLogger().error("更改工资变动类型时发生系统错误");
            return new ResultJSON(00,"系统错误，更改失败",null);
        }
    }

    public ResultJSON delete(int id) {
        //返回对象
        ResultJSON ResultJSON = new ResultJSON();
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            //查询要删除的id是否存在
            Change change1 = changeMapper.getById(id);
            if(change1==null){
                //对应id变动类型信息不存在，无法进行删除操作
                return new ResultJSON(00,"删除失败，要删除的变动类型不存在",null);
            }
            String name = change1.getName();
            //删除对应id变动类型信息
            changeMapper.delete(id);
            //将删除的变动类型信息存入result
            result.put("changelist",change1);
            //日志信息
            Log4jUtils.getLogger().warn("删除了工资变动类型,类型名:'"+name+"'");
            return new ResultJSON(11,"删除工资变动类型成功",result);
        }catch (Exception e){
            //日志信息
            Log4jUtils.getLogger().error("删除工资变动类型时发生系统错误");
            return new ResultJSON(00,"系统错误，删除失败",null);
        }

    }
}
