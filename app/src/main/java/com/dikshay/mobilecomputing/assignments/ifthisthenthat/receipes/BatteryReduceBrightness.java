package com.dikshay.mobilecomputing.assignments.ifthisthenthat.receipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.Utils.Constants;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.Utils.Utilities;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.battery.BatteryService;

public class BatteryReduceBrightness extends AppCompatActivity {
    String TAG = "Battery Reduce Brightness";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_reduce_brightness);
        Log.d(TAG,"Request Received");
        Button submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                EditText batteryLevel =  (EditText)findViewById(R.id.batterylevel);
                String batteryLevelText = batteryLevel.getText().toString();


                if(Utilities.isNumeric(batteryLevel.getText().toString()))
                {
                    Intent intent = new Intent(BatteryReduceBrightness.this, BatteryService.class);
                    intent.putExtra(Constants.BATTERY_LEVEL,Integer.parseInt(batteryLevelText.toString()));

                    startService(intent);
                }
                else {
                    Toast.makeText(BatteryReduceBrightness.this.getApplicationContext(),"Battery level should be a number",Toast.LENGTH_LONG).show();
                     }
            }
        });
        Button endButton = (Button)findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                    Intent intent = new Intent(BatteryReduceBrightness.this, BatteryService.class);
                    stopService(intent);


            }
        });


    }
    public  void setScreenBrightness(float brightness)
    {
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;
        getWindow().setAttributes(layout);
    }
}
