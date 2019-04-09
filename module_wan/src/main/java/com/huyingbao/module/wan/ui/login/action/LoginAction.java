package com.huyingbao.module.wan.ui.login.action;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public interface LoginAction {
    String REGISTER = "register";

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 密码
     */
    void register(String username, String password, String repassword);

    String LOGIN = "login";

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    void login(String username, String password);

    /**
     * 发送验证码,60s之后可以重发
     */
    String GET_IDENTIFY = "getIdentify";

    /**
     * 发送验证码,60s之后可以重发
     */
    void getIdentify();
}
