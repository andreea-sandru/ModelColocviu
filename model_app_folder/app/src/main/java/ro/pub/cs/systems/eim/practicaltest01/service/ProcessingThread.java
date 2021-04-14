package ro.pub.cs.systems.eim.practicaltest01.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ProcessingThread extends Thread {

    private Context context;
    String no1, no2;
    String TAG = "tag";

    String typeString = "MESSAGE_STRING";
    final public static String ACTION_STRING = "ro.pub.cs.systems.eim.practicaltest01.service.startedservice.string";
    final public static String DATA = "ro.pub.cs.systems.eim.practicaltest01.service.startedservice.data";


    public ProcessingThread(Context context, String no1, String no2) {
        this.context = context;
        this.no1 = no1;
        this.no2 = no2;
    }

    @Override
    public void run() {

        Log.d(TAG, "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());

        while(true) {
            sleep();
            sendMessage(0);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void sendMessage(int messageType) {
        Intent intent = new Intent();

        switch(messageType) {
            case 0:
                intent.setAction(ACTION_STRING);

                Integer int1 = Integer.valueOf(no1);
                Integer int2 = Integer.valueOf(no2);

                Integer mean = (int1 + int2)/2;
                float product = 1;
                product = int1 * int2;
                float gm = (float)Math.pow(product, (float)1 / 2);

                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String time = formatter.format(date);

                intent.putExtra(DATA, "mean = " + mean + " gm = " + gm + " time = " + time);
                context.sendBroadcast(intent);
                break;
        }
    }

}