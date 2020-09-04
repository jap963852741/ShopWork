package com.example.jap96.shopwork;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class ShopFragment extends Fragment {


    public ShopFragment() {
        // Required empty public constructor
    }
    private EditText MemberNum,Thecost,UsePoint;
    private TextView Nowpoint,FinalCost;
    private Button Nowpointsure,Checkcost,Changetopoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        MemberNum = (EditText) view.findViewById(R.id.edtMemberNum);
        Nowpoint = (TextView) view.findViewById(R.id.txtNowpoint);
        Nowpointsure = (Button) view.findViewById(R.id.pointsure);

        Thecost = (EditText) view.findViewById(R.id.thecost);
        UsePoint = (EditText) view.findViewById(R.id.theuseofpoint);
        Checkcost = (Button) view.findViewById(R.id.checkcost);
        FinalCost = (TextView) view.findViewById(R.id.finalcost);

        Changetopoint = (Button) view.findViewById(R.id.changetopoint);

        Nowpointsure.setOnClickListener(btnpointsureOnClick);
        Checkcost.setOnClickListener(btncheckcostOnClick);
        Changetopoint.setOnClickListener(btnchangetopointOnClick);

        return view;
    }

    private void pointsure() throws SQLException {
        String id = MemberNum.getText().toString();
        //Create and/or open a database that will be used for reading and writing.
        Driver drv = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(drv);
        Connection conn = DriverManager.getConnection(myapplication.get_url(), myapplication.get_user(), myapplication.get_password());
        java.sql.Statement statement = conn.createStatement();
        ResultSet account = statement.executeQuery(
                "select * from members" +
                        " where member_id ='" +
                        id + "'" );
        account.last();
        if (account.getRow() != 0) {  //有此編號
            Nowpoint.setText(account.getObject(6).toString());
            conn.close();
        }else {
            Nowpoint.setText("資料錯誤");
            conn.close();
        }

    }

    private void checkcost(){
        try{
            int thecost = Integer.parseInt(Thecost.getText().toString());
            int thepoint = Integer.parseInt(UsePoint.getText().toString());
            int total = thecost - thepoint ;
            FinalCost.setText(String.valueOf(total));
        } catch (NumberFormatException e){
            Toast toast = Toast.makeText(getActivity(), R.string.Informationerr, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Thecost.setText("");
            UsePoint.setText("");
        }
    }

    private void changetopoint()throws SQLException{
        String id = MemberNum.getText().toString();
        int a = Integer.parseInt(FinalCost.getText().toString());
        int subpoint = Integer.parseInt(UsePoint.getText().toString());
        int changepoint = (int) (a * 0.1 +0.5) - subpoint;  // +point

        Driver drv = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(drv);
        Connection conn = DriverManager.getConnection(myapplication.get_url(), myapplication.get_user(), myapplication.get_password());
        java.sql.Statement statement = conn.createStatement();
        ResultSet account = statement.executeQuery("select * from members" +
                 " where member_id ='" +
                id + "'");
        account.last();
        int nowpoint = Integer.parseInt(account.getObject(6).toString());
        int finalpoint = nowpoint + changepoint;

        if (account.getRow() != 0) {  //有此編號
            java.sql.Statement statement1 = conn.createStatement();
            java.sql.Statement statement2 = conn.createStatement();
            statement1.executeUpdate("update members set mb_point = "
                    +"'"+finalpoint+"'"+ " where member_id ='" +
                    id + "'");
            statement2.executeUpdate("INSERT INTO history(his_memberid, his_date, his_thecost,his_usepoint,his_finalcost,his_pointchange,his_nowpoint) " +
                    "VALUES ("+"'"+account.getObject(1).toString()+"'"+", "+"'"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date())+"'"+","+
                    "'"+Thecost.getText().toString()+"'"+","+"'"+UsePoint.getText().toString()+"'"+","+"'"+FinalCost.getText().toString()+"'"
                    + ","+"'"+changepoint+"'"+",'"+finalpoint+"'"+")"+";");


        }
        conn.close();
        pointsure();
        Thecost.setText("");
        UsePoint.setText("");
        FinalCost.setText("");
    }


    private View.OnClickListener btnpointsureOnClick = new View.OnClickListener() {//button1
        @Override
        public void onClick(View view) {
            try {
                pointsure();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    private View.OnClickListener btncheckcostOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkcost();
        }
    };
    private View.OnClickListener btnchangetopointOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                changetopoint();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

}
