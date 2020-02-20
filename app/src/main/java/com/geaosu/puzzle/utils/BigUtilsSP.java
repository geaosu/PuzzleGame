package com.geaosu.puzzle.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Des: sp tools
 * Author: geaosu
 * Date: 2019-09-19 周四
 * Email：geaosu@163.com
 * WeChat: geaosu
 * QQ: 819813992
 */
public class BigUtilsSP {
    public static final String NAME = "SpConfig";

    // 上下文
    private static Context mContext;

    /**
     * 初始化
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 存储String类型的值
     *
     * @param key   key值
     * @param value 要存储的String值
     */
    public static void putString(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 获取String类型的值
     *
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static String getString(String key, String defValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }


    /**
     * 存储Int类型的值
     *
     * @param key   key
     * @param value 要存储的Int值
     */
    public static void putInt(String key, int value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).commit();
    }


    /**
     * 获取Int类型的值
     *
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }


    /**
     * 存储Boolean类型的值
     *
     * @param key   key
     * @param value 要存储Boolean值
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取Boolean类型的值
     *
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(String key, Boolean defValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 删除 单个 key
     *
     * @param key
     */
    public static void delect(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).commit();
    }

    /**
     * 删除全部 key
     */
    public static void delectAll() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
