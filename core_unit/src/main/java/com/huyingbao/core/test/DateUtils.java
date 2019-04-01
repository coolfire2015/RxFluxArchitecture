package com.huyingbao.core.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liujunfeng on 2019/4/1.
 */
public class DateUtils {
    /**
     * eg：2019-01-01 12:00:00
     */
    public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 输入所要转换的时间eg：2019-01-01 12:00:00
     * 返回时间戳
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static long dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YMDHMS, Locale.CHINA);
        Date date = simpleDateFormat.parse(time);
        return date.getTime();
    }

    /**
     * 输入时间戳
     * 返回格式化的时间
     *
     * @param time
     * @return
     */
    public static String stampToDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YMDHMS, Locale.CHINA);
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }
}
