package com.example.praktikaandroid.authorization;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praktikaandroid.R;
import com.example.praktikaandroid.api.ApiFetcher;

import org.json.JSONException;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    String result = "";
    String UUID;
    static final String APP_PREFERENCES = "settings";
    static final String APP_PREFERENCES_TOKEN = "Token";
    SharedPreferences sharedPreferences;
    EditText editTextSignUpEmail, editTextSignUpName, editTextSignUpPassword;
    String link = "https://smarthome.madskill.ru/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        UUID = sharedPreferences.getString(APP_PREFERENCES_TOKEN,"");

        editTextSignUpEmail = findViewById(R.id.editTextSignUpEmail);
        editTextSignUpName = findViewById(R.id.editTextSignUpName);
        editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonNewResident:
                if(checkEmptyFields() && correctEmail(editTextSignUpEmail.getText().toString())){
                    new registerAPI().execute();
                    Intent intentAuthorization = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intentAuthorization);
                    finish();
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

    public class registerAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                result = new ApiFetcher().sendPostRegister("https://smarthome.madskill.ru/user",editTextSignUpEmail.getText().toString(),editTextSignUpName.getText().toString(),editTextSignUpPassword.getText().toString(),UUID);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
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
}