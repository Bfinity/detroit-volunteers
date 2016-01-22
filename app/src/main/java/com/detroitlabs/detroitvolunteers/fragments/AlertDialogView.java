package com.detroitlabs.detroitvolunteers.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogView {

    private Context context;

    public AlertDialogView(Context context){
        this.context = context;
    }

    public void showErrorAlertDialog(String errorMessage){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Oops")
                .setMessage(errorMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }
}
