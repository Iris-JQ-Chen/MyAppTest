package com.example.myapptest;

//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.provider.MediaStore;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import java.io.File;
//
//public class MainActivity extends AppCompatActivity {
//    public static final int RECORD_AUDIO = 1;
//
//    private Uri audioUri;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        findViewById(R.id.record_audio).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File file = new File(getExternalCacheDir(),"output_audio.amr");
//                try{
//                    if (file.exists()){
//                        file.delete();
//                    }
//                    file.createNewFile();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                if (Build.VERSION.SDK_INT >= 24){
//                    audioUri = FileProvider.getUriForFile(MainActivity.this,"com.example.myapptest.fileprovider",file);
//                }else {
//                    audioUri = Uri.fromFile(file);
//                }
//                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,audioUri);
//                startActivityForResult(intent,RECORD_AUDIO);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case RECORD_AUDIO:
//                if (resultCode == RESULT_OK){
//                    Log.d("MyAppTest",audioUri.toString());
//                    Log.d("MyAppTest",data.getData().toString());
//                    if (audioUri.equals(data.getData())){
//                        Log.d("MyAppTest","SAME");
//                    }else {
//                        Log.d("MyAppTest","DIFFERENCE");
//                    }
//                    Log.d("MyAppTest",audioUri.getPath());
//                    Log.d("MyAppTest",data.getData().getPath());
//                }
//                return;
//            default:
//                return;
//        }
//    }
//}

        import android.Manifest;
        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import java.io.BufferedOutputStream;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyAppTest";

    public static final String SD_APP_DIR_NAME = "TestDir"; //存储程序在外部SD卡上的根目录的名字
    public static final String VOICE_DIR_NAME = "voice";    //存储音频在根目录下的文件夹名字

    public static final int VOICE_RESULT_CODE = 101;        //标志符，音频的结果码，判断是哪一个Intent

    private String mVoicePath;             //用于存储录音的最终目录，即根目录 / 录音的文件夹 / 录音
    private String mVoiceName;             //保存的录音的名字
    private File mVoiceFile;               //录音文件


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "开始...");

        //录音按钮的点击事件
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 201);
            }
        });
    }

    /**
     * 返回用户是否允许权限的结果，并处理
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        if (requestCode == 201) {
            //用户允许权限
            if (grantResult[0] == PackageManager.PERMISSION_GRANTED && grantResult[1] == PackageManager.PERMISSION_GRANTED) {
                //启动录音机
                startRecord();
            } else {
                Log.d(TAG, "用户已拒绝权限，程序终止。");
                Toast.makeText(this, "程序需要足够权限才能运行", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 启动录音机，创建文件
     */
    private void startRecord() {

        Intent intent = new Intent();
        intent.setAction(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        createVoiceFile();
        Log.d(TAG, "创建录音文件");
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Log.d(TAG, "启动系统录音机，开始录音...");
        startActivityForResult(intent, VOICE_RESULT_CODE);
    }

    /**
     * 创建音频目录
     */
    private void createVoiceFile() {
        mVoiceName = getMyTime() + ".amr";
        Log.d(TAG, "录音文件名称：" + mVoiceName);
        mVoiceFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + SD_APP_DIR_NAME + "/" + VOICE_DIR_NAME + "/", mVoiceName);
        mVoicePath = mVoiceFile.getAbsolutePath();
        mVoiceFile.getParentFile().mkdirs();
        Log.d(TAG, "按设置的目录层级创建音频文件，路径：" + mVoicePath);
        mVoiceFile.setWritable(true);
    }

    /**
     * 处理返回结果。
     * 1、图片
     * 2、音频
     * 3、视频
     *
     * @param requestCode 请求码
     * @param resultCode  结果码 成功 -1 失败 0
     * @param data        返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

//        Log.d(TAG, "拍摄结束。");
        Log.d(TAG, "录音结束。");
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "返回成功。");
            Log.d(TAG, "请求码：" + requestCode + "  结果码：" + resultCode + "  data：" + data);
            switch (requestCode) {
                case VOICE_RESULT_CODE: {
                    try {
                        Uri uri = data.getData();
                        String filePath = getAudioFilePathFromUri(uri);
                        Log.d(TAG, "根据uri获取文件路径：" + filePath);
                        Log.d(TAG, "开始保存录音文件");
                        saveVoiceToSD(filePath);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
        }
    }

    /**
     * 获取日期并格式化
     * 如：2017_10_20 周三 上午 11：20：35
     *
     * @return 格式化好的日期字符串
     */
    private String getMyTime() {
        //存储格式化后的时间
        String time;
        //存储上午下午
        String ampTime = "";
        //判断上午下午，am上午，值为 0 ； pm下午，值为 1
        int apm = Calendar.getInstance().get(Calendar.AM_PM);
        if (apm == 0) {
            ampTime = "上午";
        } else {
            ampTime = "下午";
        }
        //设置格式化格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd E " + ampTime + " kk:mm:ss");
        time = format.format(new Date());

        return time;
    }

    /**
     * 保存音频到SD卡的指定位置
     *
     * @param path 录音文件的路径
     */
    private void saveVoiceToSD(String path) {
        //创建输入输出
        InputStream isFrom = null;
        OutputStream osTo = null;
        try {
            //设置输入输出流
            isFrom = new FileInputStream(path);
            osTo = new FileOutputStream(mVoicePath);
            byte bt[] = new byte[1024];
            int len;
            while ((len = isFrom.read(bt)) != -1) {
                Log.d(TAG, "len = " + len);
                osTo.write(bt, 0, len);
            }
            Log.d(TAG, "保存录音完成。");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osTo != null) {
                try {
                    //不管是否出现异常，都要关闭流
                    osTo.close();
                    Log.d(TAG, "关闭输出流");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isFrom != null) {
                try {
                    isFrom.close();
                    Log.d(TAG, "关闭输入流");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过Uri，获取录音文件的路径（绝对路径）
     *
     * @param uri 录音文件的uri
     * @return 录音文件的路径（String）
     */
    private String getAudioFilePathFromUri(Uri uri) {
        Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        String temp = cursor.getString(index);
        cursor.close();
        return temp;
    }
}