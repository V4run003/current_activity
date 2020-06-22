package com.sandscape.getcurrentactivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.rvalerio.fgchecker.AppChecker;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.app.Service.START_STICKY;

public class MainActivity extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
          startForegroundService(new Intent(this, Isevice.class));
      } else {
      startService((new Intent(this,Isevice.class)));
      }

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_SETTINGS}, 1);


      requestUsageStatsPermission();
      reqSettingsperm();
    }

    private void reqSettingsperm() {

    }

    void requestUsageStatsPermission() {
        if(!hasUsageStatsPermission(this)) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    boolean hasUsageStatsPermission(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        assert appOps != null;
        int mode = appOps.checkOpNoThrow("android:get_usage_stats",
                android.os.Process.myUid(), context.getPackageName());
        boolean granted = mode == AppOpsManager.MODE_ALLOWED;
        return granted;
    }

    }
