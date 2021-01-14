package cn.jt.service.impl;

import cn.jt.mapper.DepartMapper;
import cn.jt.pojo.Depart;
import cn.jt.pojo.User;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.DepartService;
import cn.jt.utils.Log4jUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DepartServiceImpl implements DepartService {

    @Resource
    DepartMapper departMapper;

    // 获取部门列表，未分页
    public ResultJSON selectAllDeparts() {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Depart> departs = departMapper.selectAllDeparts();

            Map departsMap = new HashMap();
            for (int i = 0; i < departs.size() ; i++) {
                departsMap.put(""+departs.get(i).getId(),departs.get(i));
            }

            result.put("departs",departs);
            result.put("departsMap",departsMap);

            // 写入日志
            /*
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            MDC.put("userId",user.getId());
            MDC.put("userName",user.getRealname());
            Log4jUtils.getLogger().info("查看部门列表");
            */
            return new ResultJSON(11,"查询部门列表成功",result);
        }catch (Exception e){
            Log4jUtils.getLogger().error("查看部门列表失败，系统异常");
            return new ResultJSON(00,"系统错误，查询失败失败",null);
        }
    }

    // 通过 id 查询部门信息
    public ResultJSON getById(String id) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            Depart depart = departMapper.getById(id);
            result.put("depart",depart);
            return new ResultJSON(11,"查询部门信息成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    //获取部门列表，分页
    public ResultJSON selectPagingDeparts(Integer page,Integer size) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            //获取页数和信息条数
            PageHelper.startPage(page,size);
            //获取部门列表
            List<Depart> departList = departMapper.selectPagingDeparts();
            //将获取的列表存入result
            PageInfo<Depart> pageInfo = new PageInfo<>(departList);
            result.put("departlist",pageInfo);
            return new ResultJSON(11,"获取列表成功",result);
        }
        catch (Exception e) {
            return new ResultJSON(00,"获取列表失败",null);
        }
    }









    //通过id和名字查询部门信息
    public ResultJSON getByIdName(String id, String name,Integer page,Integer size) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            //获取页数和信息条数
            PageHelper.startPage(page,size);
            //根据id或者名字查询部门信息
            List<Depart> departList = departMapper.getByIdName(id,name);
            //将获取的列表存入result
             PageInfo<Depart> pageInfo = new PageInfo<>(departList);
            //无该部门信息
            if(departList.isEmpty()){
                return new ResultJSON(00,"满足条件的部门信息不存在",null);
            }
            //将获取的部门信息存入result
            result.put("depart",pageInfo);
            return new ResultJSON(11,"查询部门信息成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }



    //通过id删除部门信息
    public ResultJSON delete(String id) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            Depart depart = departMapper.getById(id);
            if(depart==null){
                return  new ResultJSON(00,"删除失败，部门信息不存在",null);
            }
            result.put("depart",depart);
            departMapper.delete(id);

            return new ResultJSON(11,"删除部门信息成功,删除的部门信息为：",result);
        }catch (Exception e){

            return new ResultJSON(00,"系统错误，删除失败",null);
        }
    }

    public ResultJSON update(String id, String name, int number, String address, String departDesc) {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            //查询部门id是否存在
            Depart depart1 = departMapper.getById(id);
            if (depart1 == null){
                return new ResultJSON(00,"部门信息不存在，无法更改",null);
            }
            //查询部门名字是否重复
            Depart depart2 = departMapper.getByName(name);
            String id2 = departMapper.getId(name);
            //id不等于本身并且通过名字查询要改的部门名字信息不存在，则可以更改，否则提示部门名字重复
            // 要用equals匹配两个字符串是否相等，而不是！=
            if (depart2 != null&& !(id2.equals(id))){
                return new ResultJSON(00,"部门名字重复!",null);
            }
            //更新部门信息
            departMapper.update(id, name, number, address, departDesc);
            Depart depart3 = departMapper.getById(id);
            //将更新后的部门信息存入result
            result.put("depart",depart3);

            return new ResultJSON(11,"更新成功，更新后的部门信息为：",depart3);
        }catch (Exception e){

            return new ResultJSON(00,"系统错误，更新信息失败",null);
        }
    }

    //新增部门信息
    public ResultJSON add(String id, String name, int number, String address, String departDesc) throws Exception {
        //返回数据
        HashMap<String,Object> result = new HashMap<>();
        try{
            //查询部门id是否重复
            Depart depart1 = departMapper.getById(id);
            if (depart1!= null){
                return new ResultJSON(00,"部门id重复!",null);
            }
            //查询部门名字是否重复
            Depart depart2 = departMapper.getByName(name);
            if (depart2 != null){
                return new ResultJSON(00,"部门名字重复!",null);
            }
            departMapper.add(id, name, number, address, departDesc);
            Depart depart3 = departMapper.getById(id);
            result.put("depart:",depart3);

            return new ResultJSON(11,"部门添加成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，添加部门失败",null);
        }
    }



}
