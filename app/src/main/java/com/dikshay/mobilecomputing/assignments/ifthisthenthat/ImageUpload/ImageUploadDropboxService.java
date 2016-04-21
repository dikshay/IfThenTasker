package com.dikshay.mobilecomputing.assignments.ifthisthenthat.ImageUpload;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

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

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG,"request received");
        return START_REDELIVER_INTENT;
    }
    @Override
    public void onDestroy()
    {

    }
}
