package com.example.praktikaandroid.authorization;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.praktikaandroid.R;
import com.example.praktikaandroid.api.ApiFetcher;

import org.json.JSONException;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    String result = "";
    EditText editTextSignUpEmail, editTextSignUpName, editTextSignUpPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextSignUpEmail = findViewById(R.id.editTextSignUpEmail);
        editTextSignUpName = findViewById(R.id.editTextSignUpName);
        editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonNewResident:
                if(checkEmptyFields() && correctEmail()){
                    new registerAPI().execute();
                   /* Intent intentAuthorization = new Intent(SignUpActivity.this, SignInActivity.class);
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

    public boolean correctEmail(){
        if(editTextSignUpEmail.getText().toString().matches(".*[A-Z].*") || !editTextSignUpEmail.getText().toString().contains("@")){
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
    public class registerAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                result = new ApiFetcher().sendPostRegister("https://smarthome.madskill.ru/user","vasya@mail.com","Vasya","qwerty","5FA1B987-3890-4A87-9712-ACDEAD0173AE");
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
}