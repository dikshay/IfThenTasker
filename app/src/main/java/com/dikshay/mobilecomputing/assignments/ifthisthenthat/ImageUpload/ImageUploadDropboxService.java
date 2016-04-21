package com.dikshay.mobilecomputing.assignments.ifthisthenthat.ImageUpload;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer.MediaObserver;

/**
 * Created by Dikshay on 4/19/2016.
 */
public class ImageUploadDropboxService extends Service {
    String TAG = "ImageUploadService";
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        Log.d(TAG,"oncreate");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG,"request received");
        MediaObserver mediaObserver = new MediaObserver(new Handler(),getApplicationContext());
        mediaObserver.observe();
        return START_REDELIVER_INTENT;
    }
    @Override
    public void onDestroy()
    {
        Log.d(TAG,"service execution stopped successfully");
    }
}
