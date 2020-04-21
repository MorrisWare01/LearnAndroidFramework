package com.example.learnandroidframework.process.main.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

/**
 * Created by mmw on 2019/10/28.
 **/
public class MainService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IMainService();
    }

    public static class IMainService extends android.os.Binder {
        public String test() {
            return "test";
        }
    }
}
