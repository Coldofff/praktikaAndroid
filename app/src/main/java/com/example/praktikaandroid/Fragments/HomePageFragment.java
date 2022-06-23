package com.example.praktikaandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.praktikaandroid.Adapter.AddRoom;
import com.example.praktikaandroid.Adapter.HomePage;
import com.example.praktikaandroid.Adapter.HomePageAdapter;
import com.example.praktikaandroid.R;
import com.example.praktikaandroid.api.ApiFetcher;
import com.example.praktikaandroid.app.AddRoomActivity;
import com.example.praktikaandroid.app.RoomActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    HomePageAdapter homePageAdapter;
    List<HomePage> homePages = new ArrayList<>();
    ImageButton imageButtonAdd;
    AddRoom addRoom;
    TabHost tabHost;
    ImageView imageView;
    static final String APP_PREFERENCES = "settings";
    static final String APP_PREFERENCES_TOKEN = "Token";
    SharedPreferences sharedPreferences;
    HomePageAdapter.onItemClickListener onItemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_page,container,false);
        //initialize
        recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        imageButtonAdd = v.findViewById(R.id.imageButtonAdd);
        tabHost = v.findViewById(R.id.tabHost);
        imageView = v.findViewById(R.id.imageView);
        sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Bundle arguments = getActivity().getIntent().getExtras();

        //Tabhost
        setTabHost();

        //filling recyclerview list
//        setHomePages();

        new getRooms().execute();

        if(arguments!=null){
//            homePages.add(new HomePage(getActivity().getIntent().getIntExtra("Image",0),getActivity().getIntent().getStringExtra("Title"),"x2 devices"));
        }

        imageButtonAdd.setOnClickListener(this);
        imageView.setOnClickListener(this);
        layoutManager = new GridLayoutManager(getActivity(),2);


        onItemClickListener = new HomePageAdapter.onItemClickListener() {
            @Override
            public void onItemClick(HomePage homepage, String title) {
                Intent intent = new Intent(getActivity(), RoomActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        };

        return v;
    }

    public void setHomePages(){
        homePages.add(new HomePage(R.drawable.room_name_living_room__selected_off,"Living Room","x3 Devices"));
        homePages.add(new HomePage(R.drawable.room_name_kitchen__selected_off,"Kitchen","x3 Devices"));
        homePages.add(new HomePage(R.drawable.room_name_bathroom__selected_off,"Bathroom","x3 Devices"));
    }

    public void setTabHost() {
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.Rooms);
        tabSpec.setIndicator("Rooms");
        tabHost.addTab(tabSpec);
        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButtonAdd:
                Intent intent = new Intent(getActivity(), AddRoomActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.imageView:
                Intent intentMap = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(intentMap);
                break;
        }
    }

    public class getRooms extends AsyncTask<Void, Void, List<HomePage>> {

        String link = "https://smarthome.madskill.ru/rooms";
        String response = "";

        @Override
        protected List<HomePage> doInBackground(Void... voids) {
            try {
                response = new ApiFetcher().getRooms(link,sharedPreferences.getString("authToken",""),sharedPreferences.getString(APP_PREFERENCES_TOKEN,""));
                JSONObject jsonObject = new JSONObject(response);
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                Log.d("app",itemsArray.toString());
                for(int i=0;i<itemsArray.length();i++){
                    HomePage homePage = new HomePage();
                    homePage.setTitle(itemsArray.getJSONObject(i).getString("name"));
                    homePage.setImage(R.drawable.room_name_kitchen__selected_off);
                    homePage.setInfo("x2 devices");
                    homePages.add(homePage);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return homePages;
        }

        @Override
        protected void onPostExecute(List<HomePage> s) {
            super.onPostExecute(s);
            homePageAdapter = new HomePageAdapter(getActivity(), s, onItemClickListener);
            recyclerView.setAdapter(homePageAdapter);
            recyclerView.setLayoutManager(layoutManager);
            homePageAdapter.notifyDataSetChanged();
        }
    }
}