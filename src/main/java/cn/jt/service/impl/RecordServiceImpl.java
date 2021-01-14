package cn.jt.service.impl;

import cn.jt.mapper.RecordMapper;
import cn.jt.mapper.UserMapper;
import cn.jt.pojo.Change;
import cn.jt.pojo.Feedback;
import cn.jt.pojo.Record;
import cn.jt.pojo.User;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.RecordService;
import cn.jt.utils.DateUtil;
import cn.jt.utils.Log4jUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RecordServiceImpl implements RecordService {

    @Resource
    RecordMapper recordMapper;

    @Resource
    UserMapper userMapper;

    // 根据录入编号查询录入记录
    public ResultJSON selectRecordById(@Param("id")int id) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            Record record = recordMapper.selectRecordById(id);
            result.put("record", record);
            return new ResultJSON(11,"根据录入编号查询录入记录成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，根据录入编号查询录入记录失败",null);
        }
    }

    // 根据职工号和具体变动类型（reason）号 ... 查询
    public ResultJSON  selectReasonsByUserIdAndReasonId(
            int userId,int reasonId,String rmonth,String ryear){

        HashMap<String,Object> result = new HashMap<>();
        try{
            if (rmonth == null && ryear == null) {
                Calendar cal = Calendar.getInstance();
                rmonth = (cal.get(Calendar.MONTH) + 1) + "";
                ryear = (cal.get(Calendar.YEAR)) + "";
            }
            List<Record> records = recordMapper.selectReasonsByUserIdAndReasonId(userId, reasonId,rmonth,ryear);
            result.put("records", records);
            return new ResultJSON(11,"根据职工号和具体变动类型（reason）号 ... 查询成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，根据职工号和具体变动类型（reason）号 ... 查询失败",null);
        }
    }

    // 根据职工号和具体变动类型（reason）号查询最小年份和最大年份
    public ResultJSON selectMaxAndMinYearByUserIdAndReasonId(int userId,int reasonId) {
            HashMap<String, Object> result = new HashMap<>();
            try {

                Map year = recordMapper.selectMaxAndMinYearByUserIdAndReasonId(userId, reasonId);
                result.put("year", year);
                return new ResultJSON(11, "根据职工号和具体变动类型（reason）号查询最小年份和最大年份成功", result);
            } catch (Exception e) {
                return new ResultJSON(00, "系统错误，根据职工号和具体变动类型（reason）号查询最小年份和最大年份失败", null);
            }
        }

    // 新增一条录入记录
    public ResultJSON insertRecord(int userId,int checkerId,int changeId,int reasonId,
            double money,String checkerName,String rmonth,String ryear){
        HashMap<String, Object> result = new HashMap<>();
        try {
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String recordTime = df.format(new Date());
            User user = userMapper.findById(checkerId);
            String departId = user.getDepartId();
            int isInsert = recordMapper.insertRecord(userId, checkerId, departId,changeId, reasonId, money, checkerName, recordTime, rmonth, ryear);
            result.put("isInsert", isInsert);

            // 写入日志
            MDC.put("userId",user.getId());
            MDC.put("userName",user.getRealname());
            Log4jUtils.getLogger().info("新增变动。"+ "变动职工号："+userId+" 变动类型号："+changeId+" 具体变动类型号："+reasonId+" 变动金额："+money);

            return new ResultJSON(11, "新增一条录入记录成功", result);
        } catch (Exception e) {
            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId",user.getId());
            MDC.put("userName",user.getRealname());
            Log4jUtils.getLogger().error("新增变动失败。");
            return new ResultJSON(00, "系统错误，新增一条录入记录失败", null);
        }
    }

    // 为部门所有成员新增录入信息
    public ResultJSON insertDepartRecord(String departId,int checkerId,int changeId,int reasonId,
                                   double money,String checkerName,String rmonth,String ryear){
        HashMap<String, Object> result = new HashMap<>();
        try {
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String recordTime = df.format(new Date());
            int isInsert = 0;
            List<User> users = userMapper.selectUsersByDepartId(departId);
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                isInsert = recordMapper.insertRecord(user.getId(), checkerId,departId, changeId, reasonId, money, checkerName, recordTime, rmonth, ryear);
                if (isInsert == 0) break;
            }
            result.put("isInsert", isInsert);
            return new ResultJSON(11, "为部门所有成员新增录入信息成功", result);
        } catch (Exception e) {
            return new ResultJSON(00, "系统错误，为部门所有成员新增录入信息失败", null);
        }
    }


    // 获取录入列表，整合分页与检索
    public ResultJSON selectPagingRecordsByInfo(
            Integer page, Integer size,
            String input,
            Integer  userId, Integer checkerId, String departId,
            Integer changeId, Integer reasonId,
            String beginRyear, String endRyear,
            String beginRmonth, String endRmonth ) {


            if (beginRyear != null && !beginRyear.equals("")) {
                beginRyear = new DateUtil().convertChineseStandardTimeToJavaDate(beginRyear);
                beginRyear = beginRyear.substring(0,4);
            }
            if (endRyear != null && !endRyear.equals("")) {
                endRyear = new DateUtil().convertChineseStandardTimeToJavaDate(endRyear);
                endRyear = endRyear.substring(0,4);

            }
            if (beginRmonth != null && !beginRmonth.equals("")) {
                beginRmonth = new DateUtil().convertChineseStandardTimeToJavaDate(beginRmonth);
                    beginRmonth = beginRmonth.substring(5,7);
            }
            if (endRmonth != null && !endRmonth.equals("")) {
                endRmonth = new DateUtil().convertChineseStandardTimeToJavaDate(endRmonth);
                    endRmonth = endRmonth.substring(5,7);
            }
            System.out.println("开始年份："+beginRyear);
            System.out.println("截止年份："+endRyear);
            System.out.println("开始月份："+beginRmonth);
            System.out.println("截至月份："+endRmonth);
            //返回数据
            HashMap<String,Object> result = new HashMap<>();
            // 查询第几页，几条记录
            PageHelper.startPage(page,size);
            List<Record> records = recordMapper.selectPagingRecordsByInfo(input, userId, checkerId, departId, changeId, reasonId, beginRyear, endRyear, beginRmonth, endRmonth);
            // 将获取的列表存入 result
            PageInfo<Record> pageInfo = new PageInfo<>(records);
            result.put("records",pageInfo);
            return new ResultJSON(11,"查询录入列表（检索）成功",result);
    }

    // 更新
    public ResultJSON updateRecordById( int id, int changeId, int reasonId, double money) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            int isUpdate = recordMapper.updateRecordById(id, changeId, reasonId, money);
            result.put("isUpdate", isUpdate);
            System.out.println(money);

            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId", user.getId());
            MDC.put("userName", user.getRealname());
            Log4jUtils.getLogger().info("新增变动。" + "变动职工号：" + user.getId() + " 变动类型号：" + changeId + " 具体变动类型号：" + reasonId + " 变动金额：" + money);
            return new ResultJSON(11, "更新成功", result);
        }
        catch (Exception e) {
            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId", user.getId());
            MDC.put("userName", user.getRealname());
            Log4jUtils.getLogger().info("更新失败，录入号："+id);
            return new ResultJSON(00, "更新失败", result);
        }

    }



}
