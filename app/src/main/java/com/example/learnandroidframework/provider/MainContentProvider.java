package com.example.learnandroidframework.provider;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileNotFoundException;

/**
 * Created by mmw on 2019/10/28.
 **/
public class MainContentProvider extends ContentProvider {

    public static String queryAuthority(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ProviderInfo providerInfo = packageManager.getProviderInfo(new ComponentName(context, MainContentProvider.class), 0);
            return providerInfo.authority;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(new String[]{"text"}, 1);
        cursor.addRow(new String[]{"test"});
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        return super.call(method, arg, extras);
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String authority, @NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        return super.call(authority, method, arg, extras);
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        return super.openFile(uri, mode);
    }
}
