package ro.pub.cs.systems.eim.practicaltest01.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PracticalTest01Service extends Service {

    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("tag", "onStartCommand() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());

        String no1 = intent.getStringExtra("no1");
        String no2 = intent.getStringExtra("no2");

        processingThread = new ProcessingThread(this, no1, no2);
        processingThread.start();

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stop();
        super.onDestroy();
    }

}