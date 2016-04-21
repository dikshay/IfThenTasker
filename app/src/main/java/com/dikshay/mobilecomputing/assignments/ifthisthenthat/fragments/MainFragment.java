package com.dikshay.mobilecomputing.assignments.ifthisthenthat.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.MainActivity;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.adapter.MyArrayAdapter;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.battery.BatteryService;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.receipes.BatteryDisconnectData;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.receipes.BatteryReduceBrightness;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.receipes.BatterySendMessage;
import com.dikshay.mobilecomputing.assignments.ifthisthenthat.receipes.NewImageUploadToDropbox;


/**
 * Created by Dikshay on 4/12/2016.
 */
public class MainFragment extends Fragment{
    private String[] data={"Reduce brightness on battery","Disconnect wifi on battery","Send Message on battery",
    "Upload new photos to Dropbox"};
    private ListView mainFragmentListView;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = layoutInflater.inflate(R.layout.fragment_planet,container,false);

        mainFragmentListView = (ListView)rootView.findViewById(R.id.main_fragment_listView);
        MyArrayAdapter listAdapter = new MyArrayAdapter(getActivity(),data);
        mainFragmentListView.setAdapter(listAdapter);
        mainFragmentListView.setOnItemClickListener(new SlideMenuClickListener());
        return rootView;
    }
    public class SlideMenuClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Log.e("ListView item clicked","clicked");
            Log.d("ListView",String.valueOf(position));
            switch(position)
            {
                case 0:
                    Log.d("ListView","battery screen brightness clicked");
                    Intent intent1 = new Intent(getActivity(), BatteryReduceBrightness.class);
                    startActivity(intent1);
                    break;
                case 1:
                    Log.d("ListView","battery disconnect data clicked");
                    Intent intent2 = new Intent(getActivity(), BatteryDisconnectData.class);
                    startActivity(intent2);
                    break;
                case 2:
                    Log.d("ListView","battery send message clicked");
                    Intent intent3 = new Intent(getActivity(), BatterySendMessage.class);
                    startActivity(intent3);
                    break;
                case 3:
                    Log.d("ListView","upload to dropbox clicked");
                    Intent intent4 = new Intent(getActivity(), NewImageUploadToDropbox.class);
                    startActivity(intent4);
                    break;
                default:
                    Log.d("ListView","Error in input");
                    break;
            }
        }
    }

}
