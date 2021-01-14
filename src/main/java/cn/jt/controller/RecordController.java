package cn.jt.controller;

import cn.jt.security.pub.ResultJSON;
import cn.jt.service.RecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("api/record/")
public class RecordController {

    @Resource
    RecordService recordService;

    // 根据录入编号查询录入记录
    @RequestMapping("selectRecordById")
    public ResultJSON selectRecordById(@RequestParam("id") int id) {
        return recordService.selectRecordById(id);
    }

    // 根据职工号和具体变动类型（reason）号 ... 查询
    @RequestMapping("selectReasonsByUserIdAndReasonId")
    ResultJSON  selectReasonsByUserIdAndReasonId(
            @RequestParam("userId")int userId,
            @RequestParam("reasonId") int reasonId,
            @RequestParam(value = "rmonth",required = false)String rmonth,
            @RequestParam(value = "ryear",required = false)String ryear) {
        return recordService.selectReasonsByUserIdAndReasonId(userId, reasonId,rmonth,ryear);
    }

    // 根据职工号和具体变动类型（reason）号查询最小年份和最大年份
    @RequestMapping("selectMaxAndMinYearByUserIdAndReasonId")
    ResultJSON selectMaxAndMinYearByUserIdAndReasonId(
            @RequestParam("userId")int userId,
            @RequestParam("reasonId") int reasonId) {
        return recordService.selectMaxAndMinYearByUserIdAndReasonId(userId, reasonId);
    }

    // 新增一条录入记录
    @RequestMapping("insertRecord")
    ResultJSON insertRecord(
            @RequestParam("userId")int userId,
            @RequestParam("checkerId")int checkerId,
            @RequestParam("changeId")int changeId,
            @RequestParam("reasonId")int reasonId,
            @RequestParam("money")double money,
            @RequestParam("checkerName")String checkerName,
            @RequestParam("rmonth")String rmonth,
            @RequestParam("ryear")String ryear) {
        return recordService.insertRecord(userId, checkerId, changeId, reasonId, money, checkerName, rmonth, ryear);
    }

    // 为部门所有成员新增录入信息
    @RequestMapping("insertDepartRecord")
    public ResultJSON insertDepartRecord(
            @RequestParam("departId")String departId,
            @RequestParam("checkerId")int checkerId,
            @RequestParam("changeId")int changeId,
            @RequestParam("reasonId")int reasonId,
            @RequestParam("money")double money,
            @RequestParam("checkerName")String checkerName,
            @RequestParam("rmonth")String rmonth,
            @RequestParam("ryear")String ryear) {
        return recordService.insertDepartRecord(departId, checkerId, changeId, reasonId, money, checkerName, rmonth, ryear);
    }


    // 获取录入列表，整合分页与检索
//    @RequestMapping("selectPagingRecordsByInfo")
//    public ResultJSON selectPagingRecordsByInfo(
//            @RequestParam(value = "page",defaultValue = "1")Integer page,
//            @RequestParam(value = "size",defaultValue = "5")Integer size,
//            @RequestParam(value = "input", defaultValue = "")String input,
//            @RequestParam(value = "userId")Integer userId,
//            @RequestParam(value = "checkerId")Integer checkerId,
//            @RequestParam(value = "departId")String departId,
//            @RequestParam(value = "changeId")Integer changeId,
//            @RequestParam(value = "reasonId")Integer reasonId,
//            @RequestParam(value = "beginRyear")String beginRyear,
//            @RequestParam(value = "endRyear")String endRyear,
//            @RequestParam(value = "beginRmonth")String beginRmonth,
//            @RequestParam(value = "endRmonth")String endRmonth) {
//        System.out.println(page +" "+size);
//        System.out.println("---------------$$$$$$$$$$$$$$$----------------");
//        return recordService.selectPagingRecordsByInfo(page, size, input, userId, checkerId, departId, changeId, reasonId, beginRyear, endRyear, beginRmonth, endRmonth);
//    }

    // 获取录入列表，整合分页与检索
    @RequestMapping("selectPagingRecordsByInfo")
    @ResponseBody
    public ResultJSON selectPagingRecordsByInfo(
            @RequestParam Map<String,String> map) {
        System.out.println(map);
        Integer page = Integer.parseInt(map.get("page"));
        Integer size = Integer.parseInt( map.get("size"));
        String input = map.get("input");
        String departId = map.get("departId");

        System.out.println("$$$$ userId :" + map.get("userId"));
        Integer userId,checkerId,changeId,reasonId;

        try {
             userId = Integer.parseInt(map.get("userId"));
        }
        catch (Exception e) {
             userId = -1;
        }

        try {
            checkerId = Integer.parseInt(map.get("checkerId"));
        }
        catch (Exception e) {
            checkerId = -1;
        }

        try {
            changeId = Integer.parseInt(map.get("changeId"));
        }
        catch (Exception e) {
            changeId = -1;
        }

        try {
            reasonId = Integer.parseInt(map.get("changeId"));
        }
        catch (Exception e) {
            reasonId = -1;
        }

        String beginRyear =map.get("beginRyear");
        String endRyear =  map.get("endRyear");
        String beginRmonth = map.get("beginRmonth");
        String endRmonth =  map.get("endRmonth");
        return recordService.selectPagingRecordsByInfo(page, size, input, userId, checkerId, departId, changeId, reasonId, beginRyear, endRyear, beginRmonth, endRmonth);
    }

    // 更新
    @RequestMapping("updateRecordById")
    public ResultJSON updateRecordById(
            @RequestParam("id")int id,
            @RequestParam("changeId")int changeId,
            @RequestParam("reasonId")int reasonId,
            @RequestParam("money")double money) {
        return recordService.updateRecordById(id, changeId, reasonId, money);
    }

}
