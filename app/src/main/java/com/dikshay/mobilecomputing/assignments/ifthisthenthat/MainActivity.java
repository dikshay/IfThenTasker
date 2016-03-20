package com.dikshay.mobilecomputing.assignments.ifthisthenthat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.LocationBasedWifiToggle;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.service.GetLocationService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String tasks[] = {"Location Wifi Toggle", "Battery Wifi Toggle"};
        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_view_row, R.id.list_item, tasks);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new ListClickHandler());
    }

    private class ListClickHandler implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            TextView textView = (TextView)findViewById(R.id.list_item);
            String text = textView.getText().toString();
            Intent intent = null;
            switch (text){
                case "Location Wifi Toggle":
                    intent = new Intent(MainActivity.this, LocationBasedWifiToggle.class);
                    break;
                case "Battery Wifi Toggle":
                    intent = new Intent(MainActivity.this, LocationBasedWifiToggle.class);
                    break;
            }

            startActivity(intent);
        }
    }
}
