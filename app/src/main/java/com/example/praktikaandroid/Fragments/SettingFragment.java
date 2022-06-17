package com.example.praktikaandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.praktikaandroid.R;
import com.example.praktikaandroid.authorization.SignInActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.transform.Result;

public class SettingFragment extends Fragment implements View.OnClickListener{

    static final String APP_PREFERENCES = "mysettings";

    SharedPreferences mSettings;
    SharedPreferences.Editor editor;
    TextView textViewSignOut,textViewSave;
    EditText editTextUserName,editTextEmail,editTextPhone,editTextGender, editTextDateOfBirth;
    ImageButton imageButtonAddPhoto;
    ImageView imageViewProfilePhoto;
    int Pick_image=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();
        View view = inflater.inflate(R.layout.fragment_setting,container,false);

        textViewSignOut = view.findViewById(R.id.textViewSignOut);
        textViewSave = view.findViewById(R.id.textViewSave);
        imageButtonAddPhoto =view.findViewById(R.id.imageButtonAddPhoto);
        imageViewProfilePhoto = view.findViewById(R.id.imageViewProfilePhoto);

        textViewSignOut.setOnClickListener(this);
        textViewSave.setOnClickListener(this);
        imageButtonAddPhoto.setOnClickListener(this);

        editTextUserName = view.findViewById(R.id.editTextUserName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextGender = view.findViewById(R.id.editTextGender);
        editTextDateOfBirth = view.findViewById(R.id.editTextDateOfBirth);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textViewSave:
                saveInfo();
                break;
            case R.id.textViewSignOut:
                Intent intentSignOut = new Intent(getActivity(), SignInActivity.class);
                getActivity().finish();
                startActivity(intentSignOut);
                break;
            case R.id.imageButtonAddPhoto:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,Pick_image);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==getActivity().RESULT_OK){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageViewProfilePhoto.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveInfo(){
        editor.putString("UserName",editTextUserName.getText().toString());
        editor.putString("Email",editTextEmail.getText().toString());
        editor.putString("Phone",editTextPhone.getText().toString());
        editor.putString("Gender",editTextGender.getText().toString());
        editor.putString("DateOfBirth",editTextDateOfBirth.getText().toString());
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        editTextUserName.setText(mSettings.getString("UserName",""));
        editTextEmail.setText(mSettings.getString("Email",""));
        editTextPhone.setText(mSettings.getString("Phone",""));
        editTextGender.setText(mSettings.getString("Gender",""));
        editTextDateOfBirth.setText(mSettings.getString("DateOfBirth",""));
    }
}