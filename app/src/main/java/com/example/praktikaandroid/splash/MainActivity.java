package com.example.praktikaandroid.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.praktikaandroid.R;
import com.example.praktikaandroid.authorization.SignInActivity;

public class MainActivity extends AppCompatActivity {

    TelephonyManager telephonyManager;
    int splash_length= 2000;
    ImageView imageViewSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String uuid = telephonyManager.getDeviceId();*/


        setContentView(R.layout.activity_main);
        imageViewSplash=findViewById(R.id.imageViewSplash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        imageViewSplash.setAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, splash_length);

    }
}