package com.example.praktikaandroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praktikaandroid.Adapter.AddRoom;
import com.example.praktikaandroid.Adapter.AddRoomAdapter;
import com.example.praktikaandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddRoomActivity extends AppCompatActivity {
    RecyclerView recyclerViewRooms;
    List<AddRoom> addRooms = new ArrayList<>();
    AddRoomAdapter addRoomAdapter;
    EditText editTextTextRoomName;
    Intent intentClick;
    int test=1;
    String text="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        editTextTextRoomName = findViewById(R.id.editTextTextRoomName);
        intentClick = new Intent(AddRoomActivity.this, HomePageActivity.class);
        intentClick.putExtra(AddRoom.class.getSimpleName(), new AddRoom(R.drawable.room_name_kitchen__selected_off,"Kitchen"));

        setRooms();

        AddRoomAdapter.OnItemClickListener onItemClickListener = new AddRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AddRoom addRoom) {
                editTextTextRoomName.setText(addRoom.getTitle());
                intentClick.putExtra(AddRoom.class.getSimpleName(), addRoom);
            }
        };

        addRoomAdapter = new AddRoomAdapter(this, addRooms,onItemClickListener);
        recyclerViewRooms.setAdapter(addRoomAdapter);
        recyclerViewRooms.setLayoutManager(new GridLayoutManager(this,3));
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageButtonBack:
                finish();
                break;
            case R.id.textViewSave:
                startActivity(intentClick);
                finish();
                break;
        }
    }

    public void setRooms(){
        addRooms.add(new AddRoom(R.drawable.room_name_kitchen__selected_on,"Kitchen"));
        addRooms.add(new AddRoom(R.drawable.room_name_bedroom__selected_on,"Bedroom"));
        addRooms.add(new AddRoom(R.drawable.room_name_bathroom__selected_on,"Bathroom"));
        addRooms.add(new AddRoom(R.drawable.room_name_office__selected_on,"Office"));
        addRooms.add(new AddRoom(R.drawable.room_name_tv_room__selected_on,"TV Room"));
        addRooms.add(new AddRoom(R.drawable.room_name_living_room__selected_on,"Living Room"));
        addRooms.add(new AddRoom(R.drawable.room_name_garage__selected_on,"Garage"));
        addRooms.add(new AddRoom(R.drawable.room_name_toilet__selected_on,"Toilet"));
        addRooms.add(new AddRoom(R.drawable.room_name_kids_room__selected_on,"Kid Room"));
    }
}