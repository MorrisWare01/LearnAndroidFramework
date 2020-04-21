package com.example.learnandroidframework.process.main.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by mmw on 2019/10/28.
 **/
public class MainBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final String text = intent.getStringExtra("text");
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
