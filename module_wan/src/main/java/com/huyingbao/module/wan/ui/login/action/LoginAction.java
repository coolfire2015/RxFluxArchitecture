package com.huyingbao.module.wan.ui.login.action;

/**
 * Created by liujunfeng on 2018/12/26.
 */
public interface LoginAction {
    String REGISTER = "register";

    void register(String username, String password, String repassword);

    String LOGIN = "login";

    void login(String username, String password);
}
