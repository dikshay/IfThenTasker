package com.dikshay.mobilecomputing.assignments.ifthisthenthat.receipes;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.ImageUpload.ImageUploadDropboxService;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.observer.MediaObserver;

public class NewImageUploadToDropbox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image_upload_to_dropbox);
        Button submitButton = (Button)findViewById(R.id.activate);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent(NewImageUploadToDropbox.this, ImageUploadDropboxService.class);
                startService(intent);

                                            }
                                        });
        MediaObserver mediaObserver = new MediaObserver(new Handler());
        mediaObserver.observe();
    }
}
