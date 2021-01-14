package cn.jt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    // 将中国标准时间转化为 yyyy-MM-dd HH:mm:ss 格式
    public String convertChineseStandardTimeToJavaDate(String strDate) {
        strDate = strDate.replace("GMT", "").replaceAll("\\(.*\\)", "");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return result;
    }


}
