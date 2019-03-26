package com.huyingbao.module.wan.ui.login.action;

/**
 * @author liujunfeng
 * @date 2019/1/1
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
}
