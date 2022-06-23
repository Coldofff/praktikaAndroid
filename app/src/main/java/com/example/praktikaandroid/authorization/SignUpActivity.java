package com.example.praktikaandroid.authorization;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.praktikaandroid.R;
import com.example.praktikaandroid.api.ApiFetcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    String result = "";
    EditText editTextSignUpEmail, editTextSignUpName, editTextSignUpPassword;

    static final String APP_PREFERENCES = "settings";
    static final String APP_PREFERENCES_UUID = "UUID";
    static final String APP_PREFERENCES_KEY_DEVICE = "KEY_DEVICE";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RequestQueue requestQueue;
    String appId = "com.example.praktikaandroid";
    String uuid;
    String hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextSignUpEmail = findViewById(R.id.editTextSignUpEmail);
        editTextSignUpName = findViewById(R.id.editTextSignUpName);
        editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);

        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonNewResident:
                if(checkEmptyFields() && correctEmail(editTextSignUpEmail.getText().toString())){
                    postRegister();
                    /*Intent intentAuthorization = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intentAuthorization);
                    finish();*/
                }
                break;
            case R.id.buttonEnterYourHouse:
                Intent intentEnter = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intentEnter);
                finish();
                break;
        }
    }

    public boolean checkEmptyFields(){
        if(editTextSignUpEmail.getText().toString().isEmpty()){
            alertDialogCreate("Email is empty");
            return false;
        }
        else
            if(editTextSignUpName.getText().toString().isEmpty()){
                alertDialogCreate("Name is empty");
                return false;
            }
            else
                if(editTextSignUpPassword.getText().toString().isEmpty()) {
                    alertDialogCreate("Password is empty");
                    return false;
                }
                return true;
    }

    public boolean correctEmail(CharSequence target){
        if(!Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            alertDialogCreate("Not valid email");
            return false;
        }
        return true;
    }

    public void alertDialogCreate(String textError){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registration error")
                .setMessage(textError)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.create();
        builder.show();
    }

    public void postRegister(){
        String link = "https://smarthome.madskill.ru/user";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("app",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("app",error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> params = new HashMap<String,String>();
                params.put("email",editTextSignUpEmail.getText().toString());
                params.put("name",editTextSignUpName.getText().toString());
                params.put("password",editTextSignUpPassword.getText().toString());
                params.put("uuid",sharedPreferences.getString(APP_PREFERENCES_UUID,""));
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                Log.d("app", String.valueOf(statusCode));
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}