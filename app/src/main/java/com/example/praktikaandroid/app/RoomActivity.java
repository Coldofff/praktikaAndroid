package com.example.praktikaandroid.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.praktikaandroid.Fragments.LightFragment;
import com.example.praktikaandroid.Fragments.ThermostatFragment;
import com.example.praktikaandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class RoomActivity extends AppCompatActivity {
    String title = "";
    TextView textViewTitle;
    FrameLayout frameLayout;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        textViewTitle = findViewById(R.id.textViewRoomName);
        frameLayout = findViewById(R.id.frame_layout_room);
        navigationView = findViewById(R.id.navigation_view_room);


        if((getIntent().getExtras())!=null){
            title = getIntent().getStringExtra("title");
            textViewTitle.setText(title);
        }


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getTitle().equals("Light"))
                {
                    replaceFragment(new LightFragment());
                }
                else{
                    replaceFragment(new ThermostatFragment());
                }
                return true;
            }
        });
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageButtonBack:
                finish();
                break;
            case R.id.imageButtonAdd:
                alertDialogCreate();
                break;

        }
    }

    public void replaceFragment (Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_room,fragment);
        fragmentTransaction.commit();
    }

    ImageButton imageButtonLight, imageButtonThermostat;
    TextView textViewLight, textViewThermostat;
    int flag = 0; //1 - term
    // 2 - lamp
    public void alertDialogCreate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog,null);
        imageButtonLight = view.findViewById(R.id.imageButtonLight);
        imageButtonThermostat = view.findViewById(R.id.imageButtonTerm);
        textViewLight = view.findViewById(R.id.textViewLight);
        textViewThermostat = view.findViewById(R.id.textViewThermostat);

        imageButtonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButtonLight.setSelected(!imageButtonLight.isSelected());
                if(imageButtonLight.isSelected()) {
                    imageButtonLight.setImageResource(R.drawable.alamp);
                    textViewLight.setTextColor(getResources().getColor(R.color.app_red));
                    imageButtonThermostat.setImageResource(R.drawable.term);
                    textViewThermostat.setTextColor(getResources().getColor(R.color.profile_gray));
                    imageButtonThermostat.setSelected(false);
                    flag=1;
                }
                else {
                    imageButtonLight.setImageResource(R.drawable.lamp);
                    textViewLight.setTextColor(getResources().getColor(R.color.profile_gray));
                    flag=0;
                }
            }
        });

        imageButtonThermostat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButtonThermostat.setSelected(!imageButtonThermostat.isSelected());
                if(imageButtonThermostat.isSelected()) {
                    imageButtonThermostat.setImageResource(R.drawable.aterm);
                    textViewThermostat.setTextColor(getResources().getColor(R.color.app_red));
                    imageButtonLight.setImageResource(R.drawable.lamp);
                    textViewLight.setTextColor(getResources().getColor(R.color.profile_gray));
                    imageButtonLight.setSelected(false);
                    flag=2;
                }
                else {
                    imageButtonThermostat.setImageResource(R.drawable.term);
                    textViewThermostat.setTextColor(getResources().getColor(R.color.profile_gray));
                    flag=0;
                }
            }
        });


        builder.setTitle("Choose Device")
                .setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Menu menu = navigationView.getMenu();
                        if(flag==1){
                            menu.add(Menu.NONE,101, Menu.NONE,"Light").setIcon(R.drawable.alamp);
                            flag=0;
                        }
                        else if(flag==2){
                            menu.add(Menu.NONE,102, Menu.NONE,"Thermostat").setIcon(R.drawable.aterm);
                            flag=0;
                        }
                    }
                });
        builder.create();
        builder.show();
    }
}