package com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentSender;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.googledrive.ApplicationClass;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dikshay on 4/19/2016.
 */
    public class MediaObserver extends ContentObserver
    {
        String gpath;
        String gName="";
        ArrayList<String> newMedia = new ArrayList<String>();
        String TAG = "MEDIAOBSERVER";
        Context mContext = null;
        ContentResolver resolver;
        String[] projection = { MediaStore.Images.Media._ID };
        String[] projection2 = {MediaStore.Images.Media._ID , MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
        Set<String> image_id = new HashSet<String>();
        GoogleApiClient mGoogleApiClient;
        public static String drive_id;
        public static DriveId driveID;
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
                            String fileName = cursor1.getString(cursor1.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                            Log.d(TAG, "External: New Image is :" + filePath);
                            //newMedia.add(filePath);
                            image_id.add("e_" + id);
                            Drive.DriveApi.newDriveContents(mGoogleApiClient)
                                    .setResultCallback(driveContentsCallback);

                            //uploadToDrive(filePath);
                            gpath = filePath;
                            gName = fileName;
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
                            String fileName = cursor2.getString(cursor2.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                            Log.d(TAG, "Internal: New Image is :" + filePath);
                            //newMedia.add(filePath);
                            image_id.add("i_" + id);
                            Drive.DriveApi.newDriveContents(mGoogleApiClient)
                                    .setResultCallback(driveContentsCallback);
                            //uploadToDrive(filePath);
                            gpath = filePath;
                            gName = fileName;
                        }

                        // do something meaningful
                    } while (cursor2.moveToNext());
                }
            }
            finally {
                cursor2.close();
            }
        }
        public void addFileToOutputStream(OutputStream outputStream)
        {
           File textFile = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "Download" + File.separator + "test.txt");
            try {
                BufferedReader br = new BufferedReader(new FileReader(textFile));
                String line = null;
                while ((line = br.readLine()) != null) {
                    Log.d(TAG,line);
                }
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Log.i(TAG, "adding text file to outputstream...");
            byte[] buffer = new byte[1024];
            int bytesRead;
            try {
                BufferedInputStream inputStream = new BufferedInputStream(
                        new FileInputStream(textFile));
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                Log.i(TAG, "problem converting input stream to output stream: " + e);
                e.printStackTrace();
            }
        }
        public void addImageToOutputStream(OutputStream outputStream)
        {
            Log.d(TAG,"inside addImageToOutputStream");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            final Bitmap image = BitmapFactory.decodeFile(gpath, options);
            ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bitmapStream);
            try {
                outputStream.write(bitmapStream.toByteArray());
            } catch (IOException e1) {
                Log.i(TAG, "Unable to write file contents.");
            }

        }
        final private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback = new
                ResultCallback<DriveApi.DriveContentsResult>() {
                    @Override
                    public void onResult(DriveApi.DriveContentsResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Log.i(TAG, "Error creating new file contents");
                            return;
                        }

                        final DriveContents driveContents = result.getDriveContents();
                        new Thread() {
                            @Override
                            public void run() {
                                OutputStream outputStream = driveContents.getOutputStream();
                                addImageToOutputStream(outputStream);
                                //addFileToOutputStream(outputStream);
                                MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                        .setTitle(gName)
                                        .setMimeType("image/jpeg")
                                        .setDescription("This is a text image uploaded from device")
                                        .setStarred(true).build();
                                Drive.DriveApi.getRootFolder(mGoogleApiClient)
                                        .createFile(mGoogleApiClient, changeSet, driveContents)
                                        .setResultCallback(fileCallback);
                            }
                        }.start();
                    }
                };
        final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
                ResultCallback<DriveFolder.DriveFileResult>() {
                    @Override
                    public void onResult(DriveFolder.DriveFileResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Log.i(TAG, "Error creating the file");
                            Log.d(TAG,"Error adding file");
                            return;
                        }
                        Log.i(TAG, "File added to Drive");
                        Log.i(TAG, "Created a file with content: "
                                + result.getDriveFile().getDriveId());
                        Log.d(TAG,"File successfully added");
                        final PendingResult<DriveResource.MetadataResult> metadata
                                = result.getDriveFile().getMetadata(mGoogleApiClient);
                        metadata.setResultCallback(new
                                                           ResultCallback<DriveResource.MetadataResult>() {
                                                               @Override
                                                               public void onResult(DriveResource.MetadataResult metadataResult) {

                                                                   Metadata data = metadataResult.getMetadata();
                                                                   Log.i(TAG, "Title: " + data.getTitle());
                                                                   drive_id = data.getDriveId().encodeToString();
                                                                   Log.i(TAG, "DrivId: " + drive_id);
                                                                   driveID = data.getDriveId();
                                                                   Log.i(TAG, "Description: " + data.getDescription().toString());
                                                                   Log.i(TAG, "MimeType: " + data.getMimeType());
                                                                   Log.i(TAG, "File size: " + String.valueOf(data.getFileSize()));
                                                               }
                                                           });
                    }
                };
        public ArrayList<String> getNewMedia()
        {
            return newMedia;
        }
        public void uploadToDrive(String path)
        {
            Log.d(TAG,"inside uploadToDrive");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            final Bitmap image = BitmapFactory.decodeFile(path, options);
            final int REQUEST_CODE_CREATOR = 2;
            if(mGoogleApiClient==null)
            {
                Log.d(TAG,"found google api client null in media observer");
                Log.d(TAG,"trying to get api client from Application class");
                mGoogleApiClient = ApplicationClass.getGoogleApiClient();
            }
            else
            {
                if(mGoogleApiClient.isConnected())
                {
                    Log.d(TAG,"connected in mediaObserver");

                }
                else
                {
                    Log.d(TAG,"not connected in mediaObserver");
                }
            }

            Drive.DriveApi.newDriveContents(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {

                        @Override
                        public void onResult(DriveApi.DriveContentsResult result) {
                            // If the operation was not successful, we cannot do anything
                            // and must
                            // fail.
                            if (!result.getStatus().isSuccess()) {
                                Log.i(TAG, "Failed to create new contents.");
                                return;
                            }
                            // Otherwise, we can write our data to the new contents.
                            Log.i(TAG, "New contents created.");
                            // Get an output stream for the contents.
                            OutputStream outputStream = result.getDriveContents().getOutputStream();
                            // Write the bitmap data from it.
                            ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
                            image.compress(Bitmap.CompressFormat.PNG, 100, bitmapStream);
                            try {
                                outputStream.write(bitmapStream.toByteArray());
                            } catch (IOException e1) {
                                Log.i(TAG, "Unable to write file contents.");
                            }
                            // Create the initial metadata - MIME type and title.
                            // Note that the user will be able to change the title later.
                            MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                                    .setMimeType("image/jpeg").setTitle("newPhoto.png").build();
                            // Create an intent for the file chooser, and start it.
                            IntentSender intentSender = Drive.DriveApi
                                    .newCreateFileActivityBuilder()
                                    .setInitialMetadata(metadataChangeSet)
                                    .setInitialDriveContents(result.getDriveContents())
                                    .build(mGoogleApiClient);

                        }
                    });
        }
        public void setmGoogleApiClient(GoogleApiClient pGoogleApiClient)
        {
            mGoogleApiClient = pGoogleApiClient;
        }
    }

