package com.example.jap96.shopwork;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sqltest extends AppCompatActivity {
    private Button testb;
    private TextView text;
    private  String TAG = "MAIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqltest);

        text = (TextView) this.findViewById(R.id.textView);

        testb = (Button) this.findViewById(R.id.button);


        testb.setOnClickListener(b1);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .penaltyLog()
                        .build());


    }

    private View.OnClickListener b1 = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            // String r = DBstring.DB1("select * from members");
            //text.setText(r);

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
                String sql = "SELECT * FROM members";

                try {
                    // 創建用來執行sql語句的對象
                    java.sql.Statement statement = conn.createStatement();
                    // 執行sql查詢語句並獲取查詢信息
                    ResultSet rSet = statement.executeQuery(sql);
                    // 叠代打印出查詢信息
                    Log.i(TAG, "寶可夢列表");
                    Log.i(TAG, "ID\tName\tAttr1\tAttr2");
                    while (rSet.next()) {
                        Log.i(TAG, rSet.getString("mb_account") + "\t");
                    }
                } catch (SQLException e) {
                    Log.e(TAG, "createStatement error");
                }

                try {
                    conn.close();
                } catch (SQLException e) {
                    Log.e(TAG, "關閉連接失敗");
                }
            }

        }
    };
}
