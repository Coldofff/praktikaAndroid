package com.example.praktikaandroid.authorization;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.praktikaandroid.R;
import com.example.praktikaandroid.app.HomePageActivity;

public class SignInActivity extends AppCompatActivity{

    EditText editTextSignInEmail, editTextSignInPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextSignInEmail = findViewById(R.id.editTextSignInEmail);
        editTextSignInPassword = findViewById(R.id.editTextSignInPassword);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignIn:
                if(checkEmptyFields() && correctEmail()){
                    Intent intentSignIn = new Intent(SignInActivity.this, HomePageActivity.class);
                    startActivity(intentSignIn);
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

    public boolean correctEmail(){
        if(editTextSignInEmail.getText().toString().matches(".*[A-Z].*") || !editTextSignInEmail.getText().toString().contains("@")){
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
}