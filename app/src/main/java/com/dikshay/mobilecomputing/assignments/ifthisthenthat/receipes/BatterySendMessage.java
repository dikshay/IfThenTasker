package com.dikshay.mobilecomputing.assignments.ifthisthenthat.receipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.Utils.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.Utils.Utilities;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.battery.BatterySendMessageService;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.battery.BatteryService;

public class BatterySendMessage extends AppCompatActivity {
    String TAG = "Battery Send Message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_send_message);
        Log.d(TAG,"Request Received");
        Button submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                EditText batteryLevel =  (EditText)findViewById(R.id.batterylevel);
                String batteryLevelText = batteryLevel.getText().toString();
                EditText mobileNumberView = (EditText)findViewById(R.id.mobileNumber);
                String mobileNumber = mobileNumberView.getText().toString();
                EditText messageView = (EditText)findViewById(R.id.message);
                String message = messageView.getText().toString();


                if(Utilities.isNumeric(batteryLevel.getText().toString()))
                {
                    Intent intent = new Intent(BatterySendMessage.this, BatterySendMessageService.class);
                    intent.putExtra(Constants.BATTERY_LEVEL,Integer.parseInt(batteryLevelText.toString()));
                    intent.putExtra(Constants.PHONE_NUMBER,mobileNumber);
                    intent.putExtra(Constants.MESSAGE,message);
                    startService(intent);
                }
                else {
                    Toast.makeText(BatterySendMessage.this.getApplicationContext(),"Battery level should be a number",Toast.LENGTH_LONG).show();
                }
            }
        });

        Button endButton = (Button)findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(BatterySendMessage.this, BatterySendMessageService.class);
                stopService(intent);


            }
        });

    }
}
