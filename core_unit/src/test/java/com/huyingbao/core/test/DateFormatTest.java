package com.huyingbao.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by liujunfeng on 2019/4/1.
 * Parameterized进行参数化测试
 */
@RunWith(Parameterized.class)
public class DateFormatTest {
    private String mTime;

    public DateFormatTest(String time) {
        mTime = time;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(
                "2019-01-01",
//                "2019-01-01 16:00:02",
                "2019年10月15日 16时00分30秒"
        );
    }

    @Test(expected = ParseException.class)
    public void dateToStamp() throws ParseException {
        DateUtils.dateToStamp(mTime);
    }
}
