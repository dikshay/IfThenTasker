package com.dikshay.mobilecomputing.assignments.ifthisthenthat.battery;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class BatteryService extends Service {
    private static final String TAG = "BatteryService";
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg){
            Log.d(TAG,msg.toString());
        }

    }
    public BatteryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        HandlerThread thread = new HandlerThread("Service Thread Arguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.BATTERY_CHANGED");
        registerReceiver(ConnectionReceiver,filter);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "Service Starting");
        Message message = mServiceHandler.obtainMessage();
        message.arg1 = startId;
        mServiceHandler.sendMessage(message);

        return START_STICKY;
    }
    @Override
    public void onDestroy()
    {

        Log.d("service done", "Battery Service execution done");
        unregisterReceiver(ConnectionReceiver);
    }

    private void broadcastMessage(Context context)
    {
        Intent intent = new Intent("UI update Broadcast");
        intent.putExtra("Message","MyMessage");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private BroadcastReceiver ConnectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent batteryStatus) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level / (float)scale;
            Toast.makeText(context,"batteryLevel:" + batteryPct,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,Notification)
        }
    };

}
