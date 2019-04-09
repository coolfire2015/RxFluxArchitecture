package com.huyingbao.module.wan.ui.login.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface LoginAction {

    /**
     * ע��
     *
     * @param username   �û���
     * @param password   ����
     * @param repassword ����
     */
    fun register(username: String, password: String, repassword: String)

    /**
     * ��¼
     *
     * @param username �û���
     * @param password ����
     */
    fun login(username: String, password: String)

    /**
     * ������֤��,60s֮������ط�
     */
    fun getIdentify()

    companion object {
        val REGISTER = "register"

        val LOGIN = "login"

        /**
         * ������֤��,60s֮������ط�
         */
        val GET_IDENTIFY = "getIdentify"
    }
}
