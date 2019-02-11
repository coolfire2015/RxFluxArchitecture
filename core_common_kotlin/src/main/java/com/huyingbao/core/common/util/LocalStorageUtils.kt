package com.huyingbao.core.common.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONException

import javax.inject.Inject
import javax.inject.Singleton

/**
 * 本地配出存贮类,保存 int,boolean,String,Object,List
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class LocalStorageUtils @Inject
internal constructor(application: Application) {
    private val sSharedPreferences: SharedPreferences

    init {
        sSharedPreferences = application.getSharedPreferences(SETTING_NAME, Context.MODE_PRIVATE)
    }

    fun setInt(key: String, value: Int) {
        sSharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sSharedPreferences.getInt(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        sSharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sSharedPreferences.getBoolean(key, defaultValue)
    }

    fun setString(key: String, value: String) {
        sSharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        return sSharedPreferences.getString(key, defaultValue)
    }

    fun setLong(key: String, value: Long) {
        sSharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sSharedPreferences.getLong(key, defaultValue)
    }

    /**
     * 保存对象
     *
     * @param key
     * @param t
     */
    fun <T> setObject(key: String, t: T) {
        sSharedPreferences.edit().putString(key, JSON.toJSONString(t)).apply()
    }

    /**
     * 获取对象
     *
     * @param key
     * @param cls
     * @return
     */
    fun <T> getObject(key: String, cls: Class<T>): T? {
        val value = sSharedPreferences.getString(key, null)
        try {
            return JSON.parseObject(value, cls)
        } catch (e: JSONException) {
            return null
        }

    }

    /**
     * 保存list
     *
     * @param key
     * @param list
     */
    fun <T> setList(key: String, list: List<T>) {
        var value: String?
        try {
            value = JSON.toJSONString(list)
        } catch (e: JSONException) {
            value = null
        }

        sSharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * 获取对象
     *
     * @param key
     * @param cls
     * @return
     */
    fun <T> getList(key: String, cls: Class<T>): List<T>? {
        val value = sSharedPreferences.getString(key, null)
        try {
            return JSON.parseArray(value, cls)
        } catch (e: JSONException) {
            return null
        }

    }

    /**
     * 删除key
     *
     * @param key
     */
    fun removeKey(key: String) {
        sSharedPreferences.edit().remove(key).apply()
    }

    /**
     * 清空
     */
    fun clear() {
        sSharedPreferences.edit().clear().apply()
    }

    companion object {
        private val SETTING_NAME = "Setting"
    }
}
