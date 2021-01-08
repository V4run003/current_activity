package com.sandscape.getcurrentactivity;

import android.app.Service;
import android.app.UiModeManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.rvalerio.fgchecker.AppChecker;

import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class Isevice extends Service {
    AudioManager audioManager;
    int maxVol;
    public Isevice() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        maxVol = (audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));


        onTaskRemoved(intent);
        String currentApp = "NULL";
        UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        assert usm != null;
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 1000*1000, time);
        if (appList != null && appList.size() > 1) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : appList) {
               mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
            if (!mySortedMap.isEmpty()) {
                currentApp = Objects.requireNonNull(mySortedMap.get(mySortedMap.lastKey())).getPackageName();
            }
        }
        Log.e("current activity",currentApp);
        if (currentApp.compareTo("com.instagram.android")==0) {
            setCommand();
        }
        else
        {
            defaultCommand();
        }

        return START_STICKY;
    }

    private void defaultCommand() {
        android.provider.Settings.System.putInt(getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                10);
        int refVol = (int) (maxVol * 0.6f);
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE,AudioManager.FLAG_PLAY_SOUND);
        android.provider.Settings.System.putInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION,
                0);




    }

    private void setCommand() {
        android.provider.Settings.System.putInt(getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                100);
        audioManager.adjustVolume(AudioManager.ADJUST_MUTE,AudioManager.FLAG_PLAY_SOUND);
        android.provider.Settings.System.putInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION,
                1);

    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }
}
