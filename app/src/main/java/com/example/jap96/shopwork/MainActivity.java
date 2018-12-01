package com.example.jap96.shopwork;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String dbName = "members";
    private String url = "jdbc:mysql://" + "172.20.10.2"
            + "/" + dbName; // 構建連接mysql的字符串
    private String user = "jap123";
    private String password = "12345";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView MailOnBar = (TextView) navigationView.getHeaderView(0).findViewById(R.id.Bar_Mail);
        TextView NameOnBar = (TextView) navigationView.getHeaderView(0).findViewById(R.id.Bar_name);

        try {
            ChangeTheMailOnBar(NameOnBar,MailOnBar);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //定義首頁
        getSupportActionBar().setTitle("集點首頁");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMain,new HomeFragment());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle("集點首頁");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new HomeFragment());
            ft.commit();
        } else if (id == R.id.nav_lottery) {
            getSupportActionBar().setTitle("抽抽樂");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new LotteryFragment());
            ft.commit();
        } else if (id == R.id.nav_passwordchange) {
            getSupportActionBar().setTitle("更改密碼");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new PassWordChangeFragment());
            ft.commit();
        }else if (id == R.id.nav_shop) {
            getSupportActionBar().setTitle("商家模擬");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new ShopFragment());
            ft.commit();
        }else if (id == R.id.nav_history) {
            getSupportActionBar().setTitle("交易紀錄");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain,new HistoryFragment());
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ChangeTheMailOnBar(TextView Name,TextView Mail) throws SQLException {
        Bundle bundle = getIntent().getExtras();
        String main_account = bundle.getString("main_account");
        String main_password = bundle.getString("main_password");


        Connection conn = DriverManager.getConnection(url, user, password);
        java.sql.Statement statement = conn.createStatement();
        ResultSet passw = statement.executeQuery(
                "select * from members" +
                        " where mb_account ='" +
                        main_account + "'" + "and mb_password='" +
                        main_password + "'");
        passw.last();

            if (passw.getRow() != 0) {
                Name.setText("會員帳號:" + passw.getObject(2).toString());
                Mail.setText(passw.getObject(5).toString());
            }



    }


}
