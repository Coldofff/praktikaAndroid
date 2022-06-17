package com.example.praktikaandroid.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.praktikaandroid.app.AddRoomActivity;
import com.example.praktikaandroid.app.RoomActivity;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_page,container,false);
        //initialize
        recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        imageButtonAdd = v.findViewById(R.id.imageButtonAdd);
        tabHost = v.findViewById(R.id.tabHost);
        imageView = v.findViewById(R.id.imageView);

        Bundle arguments = getActivity().getIntent().getExtras();

        //Tabhost
        setTabHost();

        //filling recyclerview list
        setHomePages();

        if(arguments!=null){
            addRoom = (AddRoom) arguments.getSerializable(AddRoom.class.getSimpleName());
            homePages.add(new HomePage(addRoom.getImage(),addRoom.getTitle(),""));
        }

        imageButtonAdd.setOnClickListener(this);
        imageView.setOnClickListener(this);
        layoutManager = new GridLayoutManager(getActivity(),2);


        HomePageAdapter.onItemClickListener onItemClickListener = new HomePageAdapter.onItemClickListener() {
            @Override
            public void onItemClick(HomePage homepage, String title) {
                Intent intent = new Intent(getActivity(), RoomActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        };
        homePageAdapter = new HomePageAdapter(getActivity(), homePages, onItemClickListener);
        recyclerView.setAdapter(homePageAdapter);
        recyclerView.setLayoutManager(layoutManager);
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
                break;
            case R.id.imageView:
                Intent intentMap = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(intentMap);
                break;
        }
    }

}