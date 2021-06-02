package com.example.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

/**
 * Created by mmw on 2019/10/28.
 **/
public class AnrBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SystemClock.sleep(15000);
        Toast.makeText(context, "anr broadcast receiver", Toast.LENGTH_SHORT).show();
    }
}
