package com.dikshay.mobilecomputing.assignments.ifthisthenthat;

<<<<<<< HEAD
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
=======
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
>>>>>>> 958a7be3ede7d7de56f18885916b7efbc38852e6
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
<<<<<<< HEAD
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
=======
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.activity.LocationBasedWifiToggle;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.service.GetLocationService;
>>>>>>> 958a7be3ede7d7de56f18885916b7efbc38852e6

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.adapter.MyArrayAdapter;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.battery.BatteryService;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    //private ArrayAdapter<String> navigationDrawerAdapter;
    private String[] leftSliderData = {"A","B","C"};
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           String message = intent.getStringExtra("Message");
            Toast.makeText(context, "service starting", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

                /*
        * code for actionbar drawer
        * */
        createView();

        if(toolbar !=null)
        {
            toolbar.setTitle("IFFT");
            setSupportActionBar(toolbar);
        }
        initDrawer();
        /*
        *end code for actionbar drawer
         */
        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();


        //Intent intent = new Intent(this, BatteryService.class);
        //startService(intent);
    }
    private void createView()
    {
        leftDrawerList = (ListView)findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        // navigationDrawerAdapter = new ArrayAdapter<String>(Calculator.this,android.R.layout.simple_list_item_1,leftSliderData);
        MyArrayAdapter navigationDrawerAdapter  =  new MyArrayAdapter(MainActivity.this,leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);
        leftDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }
    public class SlideMenuClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Log.e("ListView item clicked","clicked");
            displayView(position);
        }
    }
    public void displayView(int position)
    {
        Fragment fragment = null;
        switch(position)
        {
            case 0:
                fragment = new MainFragment();
                break;
            case 1:
                fragment = new MainFragment();
                break;
            case 2:
                fragment = new MainFragment();
                break;


            default: break;
        }
        if(fragment!=null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.left_drawer_relative_layout);
            // drawerLayout.closeDrawer(leftDrawerList);
            drawerLayout.closeDrawer(relativeLayout);
        }
        else
        {
            Log.e("IFFT","error");
        }


    }

    private void initDrawer()
    {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
=======

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
>>>>>>> 958a7be3ede7d7de56f18885916b7efbc38852e6
    }

    @Override
    public void onResume()
    {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("UI update Broadcast"));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
