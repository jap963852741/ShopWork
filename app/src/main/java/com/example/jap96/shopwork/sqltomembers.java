package com.example.jap96.shopwork;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static android.support.constraint.Constraints.TAG;

public class sqltomembers {

    public Statement sqltomembers(){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .penaltyLog()
                .build());

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.v(TAG, "加載JDBC驅動成功");
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "加載JDBC驅動失敗");
        }
        //use ipconfig not 127.0.0.1  detail in https://blog.csdn.net/cflys/article/details/73469178
        String dbName = "members";
        String url = "jdbc:mysql://" + "172.20.10.2"
                + "/" + dbName; // 構建連接mysql的字符串
        String user = "jap123";
        String password = "12345";

        Log.d("123", "222");
        // 3.連接JDBC
        try {
            Log.d("123", "333");
            conn = DriverManager.getConnection(url, user, password);
            Log.d("123", "44");
            Log.e(TAG, "遠程連成功!");
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }

        if (conn != null) {
            //String sql = "SELECT * FROM members";

            try {
                // 創建用來執行sql語句的對象
                java.sql.Statement statement = conn.createStatement();
                // 執行sql查詢語句並獲取查詢信息
                //ResultSet rSet = statement.executeQuery(sql);
                // 叠代打印出查詢信息

                return statement;
            } catch (SQLException e) {
                Log.e(TAG, "createStatement error");
            }

            try {
                conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "關閉連接失敗");
            }
        }


        return null;
    }
}
