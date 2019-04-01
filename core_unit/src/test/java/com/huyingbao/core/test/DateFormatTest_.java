package com.huyingbao.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class DateFormatTest_ {

    private String mTime;
    private long mTimeStamp;

    public DateFormatTest_(String time, long timeStamp) {
        mTime = time;
        mTimeStamp = timeStamp;
    }


    /**
     * 构造方法中多个参数
     *
     * @return
     */
    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        List<Object[]> objects = Arrays.asList(new Object[][]{{"2017-10-15", 1508054402001L}, {"2017年10月15日 16时00分02秒", 1508054402000L}});
        return objects;
    }

    @Test(expected = ParseException.class)
    public void dateToStampTest1() throws Exception {
        DateUtils.dateToStamp(mTime);
    }


    @Test
    public void stampToDateTest() {
        DateUtils.stampToDate(mTimeStamp);
    }
}
