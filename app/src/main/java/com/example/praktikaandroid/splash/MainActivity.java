package com.example.praktikaandroid.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.praktikaandroid.R;
import com.example.praktikaandroid.authorization.SignInActivity;

public class MainActivity extends AppCompatActivity {

    int splash_length= 2000;
    ImageView imageViewSplash;

    static final String APP_PREFERENCES = "settings";
    static final String APP_PREFERENCES_TOKEN = "Token";
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String androidId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        mSettings = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_TOKEN,androidId);
        editor.apply();

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