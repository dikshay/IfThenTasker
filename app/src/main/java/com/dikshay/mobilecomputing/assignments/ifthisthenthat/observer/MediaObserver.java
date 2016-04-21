package com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by Dikshay on 4/19/2016.
 */
    public class MediaObserver extends ContentObserver
    {
        String TAG = "MEDIAOBSERVER";
        Context mContext = null;
        public MediaObserver(Handler handler)
        {
            super(handler);
        }
        public void observe()
        {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,true,this);
            resolver.registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI,true,this);

        }
        @Override
        public void onChange(boolean selfChange)
        {
            //if changes occurred in either of the watched Uris updateSettings()
            Log.d(TAG,"Image change detected");
        }
    }

