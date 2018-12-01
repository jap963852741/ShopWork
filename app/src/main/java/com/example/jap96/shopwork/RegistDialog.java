package com.example.jap96.shopwork;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class RegistDialog extends AlertDialog {
    public RegistDialog(Context context){
        super(context);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
    }
}
