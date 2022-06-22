package com.example.praktikaandroid.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praktikaandroid.Adapter.AddRoom;
import com.example.praktikaandroid.Adapter.AddRoomAdapter;
import com.example.praktikaandroid.R;
import com.example.praktikaandroid.api.ApiFetcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddRoomActivity extends AppCompatActivity {
    RecyclerView recyclerViewRooms;
    List<AddRoom> addRooms = new ArrayList<>();
    AddRoomAdapter addRoomAdapter;
    EditText editTextTextRoomName;
    Intent intentClick;
    static final String APP_PREFERENCES = "settings";
    static final String APP_PREFERENCES_TOKEN = "Token";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        editTextTextRoomName = findViewById(R.id.editTextTextRoomName);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        intentClick = new Intent(AddRoomActivity.this, HomePageActivity.class);


        setRooms();

        AddRoomAdapter.OnItemClickListener onItemClickListener = new AddRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AddRoom addRoom) {
                editTextTextRoomName.setText(addRoom.getTitle());
                intentClick.putExtra(AddRoom.class.getSimpleName(), addRoom);
                intentClick.putExtra("Title",editTextTextRoomName.getText().toString());
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
                new postRoom().execute();
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

    public class postRoom extends AsyncTask<Void, Void, String> {

        String result = "";
        String link = "https://smarthome.madskill.ru/rooms";

        @Override
        protected String doInBackground(Void... voids) {
            try {
                result = new ApiFetcher().postRoom(link,editTextTextRoomName.getText().toString(),"Kitchen",sharedPreferences.getString("authToken",""),sharedPreferences.getString(APP_PREFERENCES_TOKEN,""));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }
}