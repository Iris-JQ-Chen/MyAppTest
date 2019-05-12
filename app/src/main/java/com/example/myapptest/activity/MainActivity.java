package com.example.myapptest.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapptest.R;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final int SHOW_RESPONSE=1;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOW_RESPONSE:
                    Log.d("MyAppTest","test7");
                    String response=(String)msg.obj;
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.Login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText uid=(EditText)findViewById(R.id.username);
                EditText pwd=(EditText)findViewById(R.id.password);
                String id=uid.getText().toString().trim();
                String pw=pwd.getText().toString().trim();
                MainActivity.MyTestAsyncTask task =new MainActivity.MyTestAsyncTask();
                task.execute(id,pw);
//                SendByHttpClient(id,pw);
            }
        });
    }

    public void SendByHttpClient(final String id, final String pw){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    HttpClient httpclient=new DefaultHttpClient();
//                    HttpPost httpPost=new HttpPost("http://192.168.13.1:8888/untitled3_war_exploded/Login");
//                    List<NameValuePair> params=new ArrayList<NameValuePair>();
//                    params.add(new BasicNameValuePair("ID",id));
//                    params.add(new BasicNameValuePair("PW",pw));
//                    final UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"utf-8");
//                    httpPost.setEntity(entity);
                    Toast.makeText(MainActivity.this,"已发送",Toast.LENGTH_SHORT).show();
                    Log.d("MyAppTest","已发送");
//                    HttpResponse httpResponse= httpclient.execute(httpPost);
                    Toast.makeText(MainActivity.this,"已接收",Toast.LENGTH_SHORT).show();
                    Log.d("MyAppTest","未发送");
//                    if(httpResponse.getStatusLine().getStatusCode()==200)
//                    {
//                        HttpEntity entity1=httpResponse.getEntity();
//                        String response=EntityUtils.toString(entity1, "utf-8");
//                        Message message=new Message();
//                        message.what=SHOW_RESPONSE;
//                        message.obj=response;
//                        handler.sendMessage(message);
//                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    class MyTestAsyncTask extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... param) {
            String id = param[0];
            String pw = param[1];

//          使用okhttp
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("ID",id)
                    .add("PW",pw)
                    .build();
            Request request = new Request.Builder()
                    .url("http://10.6.194.113:8080/Login")
                    .post(requestBody)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                String s = response.body().string();
                Log.d("MyAppTest",s);
                Message message = new Message();
                message.what = SHOW_RESPONSE;
                message.obj = s;
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
