package com.example.jap96.shopwork;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.jap96.shopwork.util.myapplication;
import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private String dbName = "members";

    private TextView history;
    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_history, container, false);

        history = (TextView) view.findViewById(R.id.abc);

        Bundle bundle = getActivity().getIntent().getExtras();
        String main_account = bundle.getString("main_account");
        String main_password = bundle.getString("main_password");

        Connection conn = null;
        try {
            Driver drv = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(drv);
            conn = DriverManager.getConnection(myapplication.get_url(), myapplication.get_user(), myapplication.get_password());
            java.sql.Statement statement = conn.createStatement();
            java.sql.Statement statement2 = conn.createStatement();
            ResultSet password = statement.executeQuery(
                    "select * from members" +
                            " where mb_account ='" +
                            main_account + "'" + "and mb_password='" +
                            main_password + "'");
            password.last();
            String id = password.getObject(1).toString();
            ResultSet account = statement2.executeQuery("select * from history " +
                    " where his_memberid ='" +
                    id + "'");
            ResultSetMetaData rm = account.getMetaData();


            history.setText("         "+"購買日期"+"           "+
                    "金額"+"   "+"用點"+" "+
                    "扣點"+" "+"增點"+" "+
                    "目前點數" +"\n");

                while(account.next())
                {
                    history.append(account.getObject(3).toString()+" "+
                            " $ "+account.getObject(4).toString()+"    "+
                            account.getObject(5).toString()+"   "+
                            account.getObject(6).toString()+"   "+
                            account.getObject(7).toString()+"          "+
                            account.getObject(8).toString()+"\n");
                }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }







        return view;
    }

}
