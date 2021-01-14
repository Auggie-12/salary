package cn.jt.security.pub;

import lombok.Data;

/* 封装 json 数据，后端向前端返回统一格式 */
@Data
public class ResultJSON {

    /* 返回的状态码 */
    private Integer code;

    /* 返回的信息 */
    private String msg;

    /* 返回的数据 */
    private Object result;

    /* 构造方法 - 空 */
    public ResultJSON() {

    }

    /* 构造方法 - 只返回状态码 */
    public ResultJSON(Integer code) {

        this.code = code;

    }

    /* 构造方法 - 返回状态码和信息 */
    public ResultJSON(Integer code, String msg) {

        this.code = code;
        this.msg = msg;

    }

    /* 构造方法 - 返回状态吗和数据 */
    public ResultJSON(Integer code, Object result) {
        this.code = code;
        this.result = result;
    }

    /* 构造方法 - 返回状态吗，信息和数据 */
    public ResultJSON(Integer code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

}
