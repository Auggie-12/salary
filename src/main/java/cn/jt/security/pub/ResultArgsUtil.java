package cn.jt.security.pub;

/* 登录成功或失败的返回信息 */
public class ResultArgsUtil {

    //登录验证失败
    public static Integer USER_NOT_EXIST_FAILURE_CODE = 1004;
    public static String USER_NOT_EXIST_FAILURE_MSG = "账号或者密码错误";

    //没有登录
    public static Integer USER_NOT_LOGIN_FAILURE_CODE = 1002;
    public static String USER_NOT_LOGIN_FAILURE_MSG = "未登录";

    //登录成功
    public static Integer USER_LOGIN_SUCCESS_CODE = 1000;
    public static String USER_LOGIN_SUCCESS_MSG = "登录成功";

    //无权限
    public static Integer AUTHORIZE_FAILURE_CODE = 1003;
    public static String AUTHORIZE_FAILURE_MSG = "没有权限";

    //注销成功
    public static Integer LOGOUT_SUCCESS_CODE = 1005;
    public static String LOGOUT_SUCCESS_MSG = "注销成功";

}
