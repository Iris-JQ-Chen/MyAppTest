package com.example.myapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static Connection conn;
    static Statement st;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://10.6.194.113:3306/db_0425_01";
//    private static final String url = "jdbc:mysql://10.6.72.1:3306/db_0425_01";
    private static final String usr = "root";
    private static final String psw = "root";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_1:
                jdbcTest();
                break;
            default:
                break;
        }
    }

    public void jdbcTest(){
        conn = getcon();
        try {
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getcon() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e1) {
            Log.d("jdbc","加载驱动失败.");
//            System.out.println("加载驱动失败.");
            e1.printStackTrace();
        }
        Log.d("jdbc","MySQL JDBC Driver Registered!");
//        System.out.println("MySQL JDBC Driver Registered!");

        try {
            conn = DriverManager.getConnection(url, usr, psw);
//            conn = DriverManager.getConnection("jdbc:mysql://192.168.43.253:3306/test_01_xampp?autoReconnect=true&user=root&password=root&useUnicode=true&characterEncoding=UTF-8");
        } catch (SQLException e) {
            Log.d("jdbc","connection failed .");
//            System.out.println("connection failed .");
            e.printStackTrace();
        }
        Log.d("jdbc","connected!");
//        System.out.println("connected!");
        return conn;
    }
}
