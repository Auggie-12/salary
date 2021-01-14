package cn.jt.security.pub;

import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/* 向前端返回 json 数据的通用方法 */
public class SecurityHandlerUtil {

    /* 将封装好的 json 数据传送给前端 */
    public static void responseHandler(HttpServletResponse response, ResultJSON result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(result));
        writer.flush();
        writer.close();
    }

}
