package cn.jt.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

// 调用：Log4jUtils.getLogger().warn("xxxx");

public class Log4jUtils {
    private static Logger logger = null;

    public static Logger getLogger(){
        if (null == logger){

            //获取调用的类名
            String classname = new Exception().getStackTrace()[1].getClassName();
            //Java8 废弃了Reflection.getCallerClass()
            logger = Logger.getLogger(classname);
            //logger.debug("调用者类名"+classname);
        }
        return logger;
    }

    public static void main(String[] args) {
        Log4jUtils.getLogger().debug("debug");
        Log4jUtils.getLogger().info("info");
        Log4jUtils.getLogger().warn("warn");
        Log4jUtils.getLogger().error("error");
        Log4jUtils.getLogger().fatal("fatal");
    }
}
