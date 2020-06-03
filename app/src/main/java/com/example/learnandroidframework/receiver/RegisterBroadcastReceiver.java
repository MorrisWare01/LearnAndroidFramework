package com.example.learnandroidframework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author mmw
 * @date 2020/6/1
 **/
public class RegisterBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "register broadcast receiver", Toast.LENGTH_SHORT).show();
    }
}
