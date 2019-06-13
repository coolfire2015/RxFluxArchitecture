package com.huyingbao.core.common.module;

/**
 * 通用常量类
 * <p>
 * Created by liujunfeng on 2019/5/30.
 */
public final class CommonContants {
    public static final class Config {
        public static final int PAGE_SIZE = 20;
        public static final long HTTP_TIME_OUT = 20L;

    }

    public static final class Key {
        public static final String ACCESS_TOKEN = "accessToken";
        public static final String USER_NAME = "userName";
        public static final String PASSWORD = "password";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
    }

    public static final class Header {
        public static final String AUTHORIZATION = "Authorization";
    }
}
