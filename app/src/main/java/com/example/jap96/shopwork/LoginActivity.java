package com.example.jap96.shopwork;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.system.ErrnoException;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mysql.jdbc.Driver;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static android.support.constraint.Constraints.TAG;


public class LoginActivity extends Activity {
    private String dbName = "members";
    private String url = "jdbc:mysql://" + "10.23.12.110"
            + "/" + dbName; // 構建連接mysql的字符串
    private String user = "jap123";
    private String password = "12345";

    private Button mBtnLogIn, mBtnRegist, mBtnForPassword;

    private EditText mEdtAccount,
            mEdtPassword;


    Inet4Address ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEdtAccount = (EditText) this.findViewById(R.id.edtLoginAccount);
        mEdtPassword = (EditText) this.findViewById(R.id.edtLoginPassword);

        mBtnRegist = (Button) this.findViewById(R.id.btnRegist);
        mBtnLogIn = (Button) this.findViewById(R.id.btnLogIn);
        mBtnForPassword = (Button) this.findViewById(R.id.btnForPassword);

        mBtnLogIn.setOnClickListener(btnLogInOnClick);
        mBtnRegist.setOnClickListener(btnRegistOnClick);
        mBtnForPassword.setOnClickListener(btnForPasswordOnClick);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .penaltyLog()
                .build());


        /*
        Cursor cursor2 = MemberDb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                DB_TABLE2 + "'", null);


   /*   mysql  CREATE TABLE members(
                member_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                mb_account TEXT NOT NULL ,
                mb_password TEXT NOT NULL ,
                mb_Identity VARCHAR( 10 ) NOT NULL ,
                mb_email TEXT NOT NULL ,
                mb_point NUMERIC NOT NULL ,
                mb_registdate DATETIME
        );
        CREATE TABLE history (
                        his_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        his_memberid TEXT NOT NULL,
                        his_date datetime NOT NULL,
                        his_thecost NUMERIC NOT NULL,
                        his_usepoint NUMERIC NOT NULL,
                        his_finalcost NUMERIC NOT NULL,
                        his_pointchange NUMERIC NOT NULL,
                        his_nowpoint NUMERIC NOT NULL )*/
    }


    private View.OnClickListener btnLogInOnClick = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            String Account = mEdtAccount.getText().toString();
            String Password = mEdtPassword.getText().toString();

            //Create and/or open a database that will be used for reading and writing.
            Connection conn = null;
            try {
                ip = (Inet4Address) Inet4Address.getLocalHost();
                Log.e("connection","ip = " + ip.toString());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            try {
                Driver drv = new com.mysql.jdbc.Driver();
                DriverManager.registerDriver(drv);
                Log.e("connection","url = " + url);
                Log.e("connection","user = " + user);
                Log.e("connection","password = " + password);
                Log.e("connection","getConnection");

                conn = DriverManager.getConnection(url, user, password);
                Log.e("connection",conn.toString());
                    java.sql.Statement statement = conn.createStatement();
                    java.sql.Statement statement2 = conn.createStatement();
                    ResultSet account = statement.executeQuery(
                            "select * from members" +
                                    " where mb_account ='" +
                                    Account + "'");
                    ResultSet passw = statement2.executeQuery(
                            "select * from members" +
                                    " where mb_account ='" +
                                    Account + "'" + "and mb_password='" +
                                    Password + "'");
                    account.last();     // by the https://stackoverflow.com/questions/7545820/total-number-of-row-resultset-getrow-method
                    passw.last();
                    Log.d("123", String.valueOf(passw.getRow()));

                    if (account.getRow() != 0) {          //have accout
                        if (passw.getRow() != 0) {         //password true
                            conn.close();
                            Intent it = new Intent();
                            it.setClass(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();      //bundle the user's information
                            bundle.putString("main_account", Account);
                            bundle.putString("main_password", Password);
                            it.putExtras(bundle);
                            startActivity(it);
                        } else {    //password err
                            conn.close();
                            Toast toast = Toast.makeText(LoginActivity.this, R.string.Passworderr, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            mEdtPassword.setText("");
                        }

                    } else if (account.getRow() == 0) {//無帳號
                        conn.close();
                        Toast toast = Toast.makeText(LoginActivity.this, R.string.Accounterr, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        mEdtAccount.setText("");
                        mEdtPassword.setText("");
                    }

                } catch(SQLException e){
                    e.printStackTrace();
                }


        }
    };

    private View.OnClickListener btnRegistOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent();
            it.setClass(LoginActivity.this, RegistActivity.class);
            startActivity(it);

        }
    };
    private View.OnClickListener btnForPasswordOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent();
            it.setClass(LoginActivity.this, ForPassword.class);
            startActivity(it);
        }
    };


    @Override
    protected void onResume() {
        SharedPreferences SP = getSharedPreferences("playE",MODE_PRIVATE);
        SP.edit().putInt("playE", 1).commit();  //set the game's pic for new one
        super.onResume();
    }


}

