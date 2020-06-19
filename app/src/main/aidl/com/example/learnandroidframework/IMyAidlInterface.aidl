// IMyAidlInterface.aidl
package com.example.learnandroidframework;

// Declare any non-default types here with import statements
import android.os.IBinder;

interface IMyAidlInterface {
    void publishBinder(IBinder binder);

    void ring();
}
