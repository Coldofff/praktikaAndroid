package com.example.praktikaandroid.api;

import android.util.Log;
import android.util.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiFetcher {

    public String sendPostMobileRegister(String link,String uuid,String appId, String device) throws IOException, JSONException {
        String content;

        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
        connection.setRequestProperty("Accept","application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid",uuid);
        jsonObject.put("appId",appId);
        jsonObject.put("device",device);

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(jsonObject.toString());

        outputStream.flush();
        outputStream.close();

        StringBuilder buf = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((content = reader.readLine())!=null){
            buf.append(content).append("\n");
        }
        content = buf.toString();
        connection.disconnect();

        return new JSONObject(content).getString("keyDevice");
    }

    public String sendPostRegister(String link,String email,String name, String password, String uuid) throws IOException, JSONException {

        String content;

        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
        connection.setRequestProperty("Accept","application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());

        outputStream.flush();
        outputStream.close();

        StringBuilder buf = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((content = reader.readLine())!=null){
            buf.append(content).append("\n");
        }
        content = buf.toString();
        connection.disconnect();

        return content;
    }

}
