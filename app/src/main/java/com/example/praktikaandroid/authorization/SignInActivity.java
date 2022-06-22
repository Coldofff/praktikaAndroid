package com.example.praktikaandroid.authorization;

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

import com.example.praktikaandroid.R;
import com.example.praktikaandroid.api.ApiFetcher;
import com.example.praktikaandroid.app.HomePageActivity;

import org.json.JSONException;

import java.io.IOException;

public class SignInActivity extends AppCompatActivity{

    EditText editTextSignInEmail, editTextSignInPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    static final String APP_PREFERENCES = "settings";
    static final String APP_PREFERENCES_TOKEN = "Token";
    String UUID;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextSignInEmail = findViewById(R.id.editTextSignInEmail);
        editTextSignInPassword = findViewById(R.id.editTextSignInPassword);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        UUID = sharedPreferences.getString(APP_PREFERENCES_TOKEN,"");
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignIn:
                if(checkEmptyFields() && correctEmail(editTextSignInEmail.getText().toString())){
                    new authAPI().execute();
                }
                break;
            case R.id.buttonSignUp:
                Intent intentSignUp = new Intent (SignInActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
                break;
        }
    }

    public boolean checkEmptyFields(){
        if(editTextSignInEmail.getText().toString().isEmpty()){
            alertDialogCreate("Email is empty");
            return false;
        }
        else
        if(editTextSignInPassword.getText().toString().isEmpty()) {
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
        builder.setTitle("Authorization error")
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

    public class authAPI extends AsyncTask<String, String, String> {

        String link = "https://smarthome.madskill.ru/user";

        @Override
        protected String doInBackground(String... strings) {
            try {
                result = new ApiFetcher().sendPostAuth(link,editTextSignInEmail.getText().toString(),editTextSignInPassword.getText().toString(),UUID);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            if(!s.equals("error")) {
                Intent intentSignIn = new Intent(SignInActivity.this, HomePageActivity.class);
                startActivity(intentSignIn);
                editor.putString("authToken", s);
                editor.apply();
            }
            else
                alertDialogCreate("Unsuccessful");
            super.onPostExecute(s);
        }
    }
}