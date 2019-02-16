package com.example.myapptest;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends FragmentActivity implements NoticeDialogFragment.NoticeDialogListener{
    //...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoticeDialog();
            }
        });
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new NoticeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("int",1);
        bundle.putString("string","string");
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(NoticeDialogFragment dialog) {
        // User touched the dialog's positive button
        //...
        Toast.makeText(Main2Activity.this,dialog.userName,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(NoticeDialogFragment dialog) {
        // User touched the dialog's negative button
        //...
        Toast.makeText(Main2Activity.this,dialog.userPassword,Toast.LENGTH_SHORT).show();
    }
}
