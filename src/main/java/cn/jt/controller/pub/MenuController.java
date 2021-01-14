package cn.jt.controller.pub;

import cn.jt.security.pub.ResultJSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    // 工资信息

    // 我的台账
    @RequestMapping("/nav1/sub1")
    public ResultJSON nav1Sub1(){
        return new ResultJSON(2000, "我的台账");
    }

    // 工资清单
    @RequestMapping("/nav1/sub2")
    public ResultJSON nav1Sub2(){
        return new ResultJSON(2000, "工资清单");
    }


    // 我的反馈

    // 反馈列表
    @RequestMapping("/nav2/sub1")
    public ResultJSON nav2Sub1(){
        return new ResultJSON(2000, "反馈列表");
    }

    // 提交反馈
    @RequestMapping("/nav2/sub2")
    public ResultJSON nav2Sub2(){
        return new ResultJSON(2000, "提交反馈");
    }


    // 反馈审核

    // 等待审核
    @RequestMapping("/nav3/sub1")
    public ResultJSON nav3Sub1(){
        return new ResultJSON(2000, "等待审核");
    }

    // 审核统计
    @RequestMapping("/nav3/sub2")
    public ResultJSON nav3Sub2(){
        return new ResultJSON(2000, "审核统计");

    }


    // 工资管理

    // 工资汇总
    @RequestMapping("/nav4/sub1")
    public ResultJSON nav4Sub1(){
        return new ResultJSON(2000, "工资汇总");

    }

    // 工资发放
    @RequestMapping("/nav4/sub2")
    public ResultJSON nav4Sub2(){
        return new ResultJSON(2000, "工资发放");

    }


    // 系统管理

    // 用户管理
    @RequestMapping("/nav5/sub1")
    public ResultJSON nav5Sub1(){
        return new ResultJSON(2000, "用户管理");

    }

    // 角色管理
    @RequestMapping("/nav5/sub2")
    public ResultJSON nav5Sub2(){
        return new ResultJSON(2000, "角色管理");

    }

    // 权限管理
    @RequestMapping("/nav5/sub3")
    public ResultJSON nav5Sub3(){
        return new ResultJSON(2000, "权限管理");

    }

    // 日志管理
    @RequestMapping("/nav5/sub4")
    public ResultJSON nav5Sub4(){
        return new ResultJSON(2000, "日志管理");

    }


    // 字典管理

    // 部门管理
    @RequestMapping("/nav6/sub1")
    public ResultJSON nav6Sub1(){
        return new ResultJSON(2000, "部门管理");

    }

    // 公告管理
    @RequestMapping("/nav6/sub2")
    public ResultJSON nav6Sub2(){
        return new ResultJSON(2000, "公告管理");

    }

    // 变动类型管理
    @RequestMapping("/nav6/sub3")
    public ResultJSON nav6Sub3(){
        return new ResultJSON(2000, "变动类型管理");

    }

    // 变动原因管理
    @RequestMapping("/nav6/sub4")
    public ResultJSON nav6Sub4(){
        return new ResultJSON(2000, "变动原因管理");

    }


}
