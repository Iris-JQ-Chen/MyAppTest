package com.example.myapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MyAppTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        Integer integer = null;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        StringBuilder builder = new StringBuilder(year);
        builder.append(month);
        builder.append(day);
        builder.append(hour);
        builder.append(minute);
        builder.append(second);
        integer = new Integer(builder.toString());

        Log.d(TAG,year+"");
        Log.d(TAG,month+"");
        Log.d(TAG,day+"");
        Log.d(TAG,hour+"");
        Log.d(TAG,minute+"");
        Log.d(TAG,second+"");
        Log.d(TAG,integer.toString());

        Date date = new Date();
        String dateStr = date.toLocaleString();
        Log.d(TAG,dateStr);

//        Toast.makeText(this,integer+"",Toast.LENGTH_SHORT).show();
    }
}
