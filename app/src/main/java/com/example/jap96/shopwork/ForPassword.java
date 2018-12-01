package com.example.jap96.shopwork;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ForPassword extends AppCompatActivity {
    private String dbName = "members";
    private String url = "jdbc:mysql://" + "172.20.10.2"
            + "/" + dbName; // 構建連接mysql的字符串
    private String user = "jap123";
    private String password = "12345";
    private EditText mEdtForPassAccount,
            mEdtForPassIdentity;
    private Button mBtnForPassSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_password);

        mEdtForPassAccount = (EditText) findViewById(R.id.edtForPassAccount);
        mEdtForPassIdentity = (EditText) findViewById(R.id.edtForPassIdentity);
        mBtnForPassSure = (Button) findViewById(R.id.btnForPassSure);
        mBtnForPassSure.setOnClickListener(btnForPasswordOnClick);
    }

    private View.OnClickListener btnForPasswordOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String Account = mEdtForPassAccount.getText().toString();
            String Identity = mEdtForPassIdentity.getText().toString();
            Connection conn = null;


            try {
                conn = DriverManager.getConnection(url, user, password);

            java.sql.Statement statement = conn.createStatement();
                ResultSet passw = statement.executeQuery(
                        "select * from members" +
                                " where mb_account ='" +
                                Account + "'" + "and mb_Identity='" +
                                Identity + "'");
                passw.last();
            if (passw.getRow() != 0) {//有帳號

                RegistDialog mtAltDlg = new RegistDialog(ForPassword.this);
                mtAltDlg.setTitle("您的密碼是");
                mtAltDlg.setMessage(passw.getObject(3).toString());
                conn.close();
                mtAltDlg.setButton(DialogInterface.BUTTON_POSITIVE, "確認", aa);
                mtAltDlg.show();

            } else {
                Toast toast = Toast.makeText(ForPassword.this, R.string.Informationerr, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                conn.close();
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        private DialogInterface.OnClickListener aa = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };


    };
}

