package cn.jt.service.impl;

import cn.jt.mapper.ReasonMapper;
import cn.jt.mapper.RecordMapper;
import cn.jt.pojo.Change;
import cn.jt.pojo.Reason;
import cn.jt.pojo.Record;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.ReasonService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReasonServiceImpl implements ReasonService {

    @Resource
    ReasonMapper reasonMapper;

    /* 获取具体变动类型列表（ 返回 map 类型，id 为键 ） */
    public ResultJSON selectAllReasons() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Reason> reasons = reasonMapper.selectAllReasons();
            Map reasonMap = new HashMap();
            Map reasonsMap = new HashMap();
            for (int i = 0; i < reasons.size() ; i++) {
                reasonMap.put(""+reasons.get(i).getId(),reasons.get(i));
                reasonsMap.put(""+reasons.get(i).getId(),reasons.get(i));
            }
            result.put("reasons", reasons);
            result.put("reasonMap", reasonMap);
            result.put("reasonsMap", reasonsMap);
            return new ResultJSON(11,"获取具体变动类型列表成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，获取具体变动类型列表失败",null);
        }
    }

    // 根据变动原因编号查询变动原因
    public ResultJSON selectReasonById(@Param("id")int id) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            Reason reason = reasonMapper.selectReasonById(id);
            result.put("reason", reason);
            return new ResultJSON(11,"根据变动原因编号查询变动原因成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，根据变动原因编号查询变动原因失败",null);
        }
    }

    // 根据一级变动类型编号编号查询具体变动类型
    public ResultJSON  selectAllReasonsByChangeId(int changeId) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Reason> reasons = reasonMapper.selectAllReasonsByChangeId(changeId);
            result.put("reasons", reasons);
            return new ResultJSON(11,"根据一级变动类型编号编号查询具体变动类型成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，根据一级变动类型编号编号查询具体变动类型失败",null);
        }
    }

}
