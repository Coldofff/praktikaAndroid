package com.example.praktikaandroid.api;

import android.util.Log;
import android.util.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
        connection.setRequestProperty("Accept","application/json");
        connection.setRequestProperty("email",email);
        connection.setRequestProperty("name",name);
        connection.setRequestProperty("password",password);
        connection.setRequestProperty("uuid",uuid);


        connection.disconnect();

        return connection.getResponseMessage();
    }

    public String sendPostAuth(String link, String email, String password, String uuid) throws IOException, JSONException {

        String content;

        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        connection.setRequestProperty("X-HTTP-Method-Override","OPTIONS");
        connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
        connection.setRequestProperty("Accept","application/json");

        connection.setRequestProperty("email",email);
        connection.setRequestProperty("password",password);
        connection.setRequestProperty("uuid",uuid);
        connection.connect();

        if(connection.getResponseCode()==201) {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((content = bufferedReader.readLine()) != null) {
                stringBuilder.append(content).append("\n");
            }

            content = stringBuilder.toString();
            connection.disconnect();

            return new JSONObject(content).getString("token");
        }

        else{
            return "error";
        }
    }

    public String getRooms(String link, String token, String UUID) throws IOException {

        String content;

        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("token",token);
        connection.setRequestProperty("uuid",UUID);

        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        if((content=bufferedReader.readLine())!=null){
            stringBuilder.append(content).append("\n");
        }

        content = stringBuilder.toString();
        connection.disconnect();

        return content;
    }

    public String postRoom(String link,String name, String type, String token, String uuid) throws IOException {

        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("name",name);
        connection.setRequestProperty("type",type);
        connection.setRequestProperty("token",token);
        connection.setRequestProperty("uuid",uuid);

        return connection.getResponseMessage();
    }
}
