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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.praktikaandroid.R;
import com.example.praktikaandroid.api.ApiFetcher;
import com.example.praktikaandroid.authorization.SignInActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    int splash_length= 2000;
    ImageView imageViewSplash;

    static final String APP_PREFERENCES = "settings";
    static final String APP_PREFERENCES_UUID = "UUID";
    static final String APP_PREFERENCES_KEY_DEVICE = "KEY_DEVICE";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RequestQueue requestQueue;
    String appId = "com.example.praktikaandroid";
    String uuid;
    String device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewSplash=findViewById(R.id.imageViewSplash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        imageViewSplash.setAnimation(animation);

        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getString(APP_PREFERENCES_UUID,"").equals("")) {
            uuid = UUID.randomUUID().toString();
            editor.putString(APP_PREFERENCES_UUID,uuid);
            editor.apply();
        }

        device = Build.BRAND.toString()+" "+Build.MODEL.toString();

        postMobileRegister();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, splash_length);

    }

    public void postRegisterApp(){
        String link = "https://smarthome.madskill.ru/app";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appId",appId);
            jsonObject.put("competitor","Competitor-1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, link, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("app",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("app",error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void postMobileRegister(){
        String link = "https://smarthome.madskill.ru/mobile";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uuid",sharedPreferences.getString(APP_PREFERENCES_UUID,""));
            jsonObject.put("appId",appId);
            jsonObject.put("device",device);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, link, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("app",response.toString());
                try {
                    editor.putString(APP_PREFERENCES_KEY_DEVICE,response.getString("keyDevice"));
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("app",error.toString());
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}