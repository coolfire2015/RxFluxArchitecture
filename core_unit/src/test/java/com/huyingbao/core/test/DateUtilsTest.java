package com.huyingbao.core.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by liujunfeng on 2019/4/1.
 */
public class DateUtilsTest {
    private String mTime = "2019-01-01 16:00:02";
    private long mTimeStamp = 1546329602000L;
    private Date mDate;

    @Before
    public void setUp() {
        System.out.println("测试开始！");
        mDate = new Date();
        mDate.setTime(mTimeStamp);
    }

    @After
    public void tearDown() {
        System.out.println("测试结束");
    }

    /**
     * 预期相等
     */
    @Test
    public void dateToStamp() {
        assertEquals(mTime, DateUtils.stampToDate(mTimeStamp));
    }

    /**
     * 预期不相等
     *
     * @throws ParseException
     */
    @Test
    public void stampToDate() throws ParseException {
        assertNotEquals(4, DateUtils.dateToStamp(mTime));
    }

    /**
     * 预期抛出异常
     *
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void stampToDateThrowException() throws ParseException {
        assertNotEquals(4, DateUtils.dateToStamp("234324"));
    }

    @Test
    @Ignore("test 方法不执行\n")
    public void test() {
        System.out.println("-----");
    }
}