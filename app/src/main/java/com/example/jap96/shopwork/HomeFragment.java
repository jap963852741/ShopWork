package com.example.jap96.shopwork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jap96.shopwork.util.myapplication;
import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    private TextView mEdtForPassAccount;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        mEdtForPassAccount = (TextView) view.findViewById(R.id.PassAccount);

        // Inflate the layout for this fragment
        showResult();

        return  view;

    }

    private void showResult() {
        Bundle bundle = getActivity().getIntent().getExtras();
        String main_account = bundle.getString("main_account");
        String main_password = bundle.getString("main_password");

        Connection conn = null;

        try {
            Driver drv = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(drv);
            Log.e("connection","getConnection");
            conn = DriverManager.getConnection(myapplication.get_url(), myapplication.get_user(), myapplication.get_password());            java.sql.Statement statement = conn.createStatement();
            ResultSet passw = statement.executeQuery(
                "select * from members" +
                        " where mb_account ='" +
                        main_account + "'" + "and mb_password='" +
                        main_password + "'");
        passw.last();
        if (passw.getRow() != 0) {
            Log.d("1111111111111111111111","dsfasdafsdfasd");
                mEdtForPassAccount.setText( "\n"+"會員編號:" + passw.getObject(1) + "\n"
                        + "申請日期:" + passw.getObject(7).toString().subSequence(0,10) + "\n"
                        +"\n"+ "目前點數:" + passw.getObject(6) + "\n");
                //getObject start from 1
            conn.close();
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
