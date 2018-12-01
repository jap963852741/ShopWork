package com.example.jap96.shopwork;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A simple {@link Fragment} subclass.
 */
public class PassWordChangeFragment extends Fragment {
    private String dbName = "members";
    private String url = "jdbc:mysql://" + "172.20.10.2"
            + "/" + dbName; // 構建連接mysql的字符串
    private String user = "jap123";
    private String password = "12345";
    private Button mBtnChangePassword;
    private EditText mEdtOldPassword,
            mEdtNewPassword00,mEdtNewPassword01;
    public PassWordChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_pass_word_change, container, false);
        // Inflate the layout for this fragment
        mEdtOldPassword =(EditText) view.findViewById(R.id.edtOldPassWord);
        mEdtNewPassword00 =(EditText) view.findViewById(R.id.edtChangePassWord00);
        mEdtNewPassword01 =(EditText) view.findViewById(R.id.edtChangePassWord01);

        mBtnChangePassword = (Button) view.findViewById(R.id.btnChangePassWord);
        mBtnChangePassword.setOnClickListener(btnChangeOnClick);

        return view;
    }



    private View.OnClickListener btnChangeOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            OldPassWordSure();

        }
    };

    private void OldPassWordSure() {  // 1 --> true 0-->false
        Bundle bundle = getActivity().getIntent().getExtras();
        String main_account = bundle.getString("main_account");
        String main_password = bundle.getString("main_password");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            java.sql.Statement statement = conn.createStatement();
            ResultSet passw = statement.executeQuery(
                    "select * from members" +
                            " where mb_account ='" +
                            main_account + "'" + "and mb_password='" +
                            main_password + "'");
            passw.last();
            if (passw.getRow() != 0) {
                String password = passw.getObject(3).toString();
                String OldPassWord = mEdtOldPassword.getText().toString();
                if(OldPassWord.equals(password) ){
                    NewPassWordCheck();
                }else {
                    Toast toast = Toast.makeText(getActivity(), R.string.Passworderr, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mEdtOldPassword.setText("");

                }


                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void NewPassWordCheck() {
        String NewPassWord00 = mEdtNewPassword00.getText().toString();
        String NewPassWord01 = mEdtNewPassword01.getText().toString();

        if(NewPassWord00.equals(NewPassWord01) ){
            ChangePassWord();
        }else {
            Toast toast = Toast.makeText(getActivity(), R.string.NewPassworderr, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            mEdtNewPassword00.setText("");
            mEdtNewPassword01.setText("");

        }


    }
    private void ChangePassWord() {
        String OldPassWord = mEdtOldPassword.getText().toString();
        String NewPassWord00 = mEdtNewPassword00.getText().toString();

        Bundle bundle = getActivity().getIntent().getExtras();
        String main_account = bundle.getString("main_account");
        String main_password = bundle.getString("main_password");

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            java.sql.Statement statement = conn.createStatement();
            ResultSet passw = statement.executeQuery(
                    "select * from members" +
                            " where mb_account ='" +
                            main_account + "'" + "and mb_password='" +
                            main_password + "'");
            passw.last();
            if (passw.getRow() != 0) {
                java.sql.Statement statement1 = conn.createStatement();
                statement1.executeUpdate("update members set mb_password = "
                        +"'"+NewPassWord00+"'"+ " where mb_account ='" +
                        main_account + "'" + "and mb_password='" +
                        main_password + "'"
                );
                conn.close();
                RegistDialog mtAltDlg = new RegistDialog(getActivity());
                mtAltDlg.setTitle("密碼已更改");
                mtAltDlg.setButton(DialogInterface.BUTTON_POSITIVE,"確認",aa);
                mtAltDlg.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
    private DialogInterface.OnClickListener aa = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mEdtOldPassword.setText("");
            mEdtNewPassword00.setText("");
            mEdtNewPassword01.setText("");
            Intent it = new Intent();
            it.setClass(getContext(), LoginActivity.class);
            startActivity(it);

        }
    };
}
