package com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dikshay on 4/19/2016.
 */
    public class MediaObserver extends ContentObserver
    {
        String TAG = "MEDIAOBSERVER";
        Context mContext = null;
        ContentResolver resolver;
        String[] projection = { MediaStore.Images.Media._ID };
        String[] projection2 = {MediaStore.Images.Media._ID , MediaStore.Images.Media.DATA};
        Set<String> image_id = new HashSet<String>();
        public MediaObserver(Handler handler,Context context)
        {
            super(handler);
            mContext = context;
        }
        public void observe()
        {
            Log.d(TAG,"media observer is observing");
            resolver = mContext.getContentResolver();
            resolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,true,this);
            resolver.registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI,true,this);
            Cursor cursor1 = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,projection,null,null,null);
            try {
                if (cursor1.moveToFirst()) {
                    do {
                        image_id.add("e_" + cursor1.getLong(0));
                    } while (cursor1.moveToNext());
                }
            }
            finally
            {
                cursor1.close();
            }

            Cursor cursor2 = resolver.query(MediaStore.Images.Media.INTERNAL_CONTENT_URI,projection,null,null,null);
            try {
                if (cursor2.moveToFirst()) {
                    do {
                        image_id.add("i_" + cursor2.getLong(0));
                    } while (cursor2.moveToNext());
                }
            }
            finally
            {
                cursor2.close();
            }
        }
        @Override
        public void onChange(boolean selfChange)
        {
            //if changes occurred in either of the watched Uris updateSettings()
            Log.d(TAG,"Image change detected");
            Cursor cursor1 = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,projection2,null,null,null);
            try
                {
                if (cursor1.moveToFirst()) {
                    do {
                        long id = cursor1.getLong(0);

                        if (!image_id.contains("e_" + id)) {
                            String filePath = cursor1.getString(cursor1.getColumnIndex(MediaStore.Images.Media.DATA));
                            Log.d(TAG, "External: New Image is :" + filePath);
                            image_id.add("e_" + id);
                        }

                        // do something meaningful
                    } while (cursor1.moveToNext());
                }

                }
            finally
            {
                cursor1.close();
            }
            Cursor cursor2 = resolver.query(MediaStore.Images.Media.INTERNAL_CONTENT_URI,projection2,null,null,null);
            try {
                if (cursor2.moveToFirst()) {
                    do {
                        long id = cursor2.getLong(0);
                        if (!image_id.contains("i_" + id)) {
                            String filePath = cursor2.getString(cursor2.getColumnIndex(MediaStore.Images.Media.DATA));
                            Log.d(TAG, "Internal: New Image is :" + filePath);
                            image_id.add("i_" + id);
                        }

                        // do something meaningful
                    } while (cursor2.moveToNext());
                }
            }
            finally {
                cursor2.close();
            }
        }
    }

