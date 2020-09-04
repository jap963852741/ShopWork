package com.example.jap96.shopwork.util;

import android.app.Application;

public class myapplication extends Application {
    private static final String dbName = "members";
//    private static final String Mysql_ip;
    private static final String user = "jap123";
    private static final String password = "12345";
    private static final String Mysql_ip = "10.23.12.110";// 構建連接mysql的字符串
    private static final String url = "jdbc:mysql://" + Mysql_ip+ "/" + dbName; // 構建連接mysql的字符串

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static String get_user()
    {
        return user;
    }
    public static String get_password()
    {
        return password;
    }
    public static String get_url()
    {
        return url;
    }

}
