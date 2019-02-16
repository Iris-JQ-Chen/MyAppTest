package com.example.myapptest;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 蒲公英之流 on 2019-02-16.
 */

public class NoticeDialogFragment extends DialogFragment {

    public String userName;
    public String userPassword;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(NoticeDialogFragment dialog);
        public void onDialogNegativeClick(NoticeDialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
    //...

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = getArguments();
        String message = "Message";
        if(bundle != null){
            message = bundle.getString("string")+bundle.getInt("int");
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_signin,null);
        builder.setMessage(message)
                .setView(view)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        View view1 = view.findViewById(R.id.username);
                        TextView textView = (TextView)view1;
                        userName = textView.getText().toString();
                        // Toast.makeText(getActivity(),textView.getText().toString(),Toast.LENGTH_SHORT).show();
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(NoticeDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        View view1 = view.findViewById(R.id.password);
                        TextView textView = (TextView)view1;
                        userPassword = textView.getText().toString();
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(NoticeDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
