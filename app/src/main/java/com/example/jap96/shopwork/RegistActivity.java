package com.example.jap96.shopwork;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class RegistActivity extends AppCompatActivity {
    private String dbName = "members";
    private String url = "jdbc:mysql://" + "172.20.10.2"
            + "/" + dbName; // 構建連接mysql的字符串
    private String user = "jap123";
    private  String password = "12345";
    private EditText mEdtAccount,
                     mEdtPassword,
                     mEdtIdentity,
                     mEdtEmail;
    private Button btnRegistSure;
    Number shoppoint = 0;
    private  String TAG = "regis";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        //Create and/or open a database that will be used for reading and writing.


        mEdtAccount =(EditText) findViewById(R.id.edtAccount);
        mEdtPassword =(EditText) findViewById(R.id.edtPassword00);
        mEdtIdentity =(EditText) findViewById(R.id.edtIdentity);
        mEdtEmail =(EditText) findViewById(R.id.edtEmail);

        btnRegistSure = (Button) findViewById(R.id.btnRegistSure);
        btnRegistSure.setOnClickListener(btnRegistSureOnClick);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .penaltyLog()
                .build());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private View.OnClickListener btnRegistSureOnClick = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override

        public void onClick(View v) {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                conn = DriverManager.getConnection(url, user, password);
                Log.e(TAG, "遠程連成功!");
                //use ipconfig not 127.0.0.1  detail in https://blog.csdn.net/cflys/article/details/73469178
            } catch (SQLException e) {
                Log.e(TAG, e.toString());
            }
            try {
                java.sql.Statement statement = conn.createStatement();
                String regist = "INSERT INTO members (mb_account, mb_password, mb_Identity,mb_email,mb_point,mb_registdate) " +
                        "VALUES ("+"'"+mEdtAccount.getText().toString()+"'"+", "+"'"+mEdtPassword.getText().toString()+"'"+","+
                        "'"+mEdtIdentity.getText().toString()+"'"+","+"'"+mEdtEmail.getText().toString()+"'"+","+"'"+String.valueOf(shoppoint)+"'"
                        + ","+"'"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date())+"'"+")"+";";
                statement.executeUpdate(regist);
                conn.close();
                RegistDialog mtAltDlg = new RegistDialog(RegistActivity.this);
                mtAltDlg.setTitle("註冊成功");
                mtAltDlg.setButton(DialogInterface.BUTTON_POSITIVE,"是",finish);
                mtAltDlg.show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    private DialogInterface.OnClickListener finish = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    };


}
