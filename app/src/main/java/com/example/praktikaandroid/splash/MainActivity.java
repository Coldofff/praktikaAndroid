package com.example.praktikaandroid.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.praktikaandroid.R;
import com.example.praktikaandroid.api.ApiFetcher;
import com.example.praktikaandroid.authorization.SignInActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    int splash_length= 2000;
    ImageView imageViewSplash;

    static final String APP_PREFERENCES = "settings";
    static final String APP_PREFERENCES_TOKEN = "Token";
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;
    String appId = "com.example.praktikaandroid";
    String device = "";
    String UUId = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUId = UUID.randomUUID().toString().toUpperCase();

        mSettings = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        editor = mSettings.edit();

        if(mSettings.getString(APP_PREFERENCES_TOKEN,"").equals("")) {
            editor.putString(APP_PREFERENCES_TOKEN, UUId);
            editor.apply();
        }

        Toast.makeText(this, mSettings.getString(APP_PREFERENCES_TOKEN,""),Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_main);
        imageViewSplash=findViewById(R.id.imageViewSplash);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        imageViewSplash.setAnimation(animation);

        device=Build.BRAND.toUpperCase() + " " + Build.MODEL.toUpperCase();

        new appRegister().execute();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, splash_length);

    }

    public class appRegister extends AsyncTask<String, Void, String> {
        String response = "";
        @Override
        protected String doInBackground(String... strings) {
            try {
                response = new ApiFetcher().sendPostMobileRegister("https://smarthome.madskill.ru/mobile",UUId,appId,device);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            editor.putString("key",s);
            editor.apply();
        }
    }
}