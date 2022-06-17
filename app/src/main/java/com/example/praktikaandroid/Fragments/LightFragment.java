package com.example.praktikaandroid.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.praktikaandroid.R;

public class LightFragment extends Fragment implements View.OnClickListener{

    TextView textViewNumber, textViewUnderNumberTitle,textViewNearNumberTitle;;
    TextView textViewUnderLineTitle;
    SeekBar seekBarRoomLight;
    ImageButton imageButtonOnLight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle argument = getActivity().getIntent().getExtras();

        View view = inflater.inflate(R.layout.fragment_light,container,false);
        textViewUnderLineTitle = view.findViewById(R.id.textViewUnderLineTitle);
        seekBarRoomLight = view.findViewById(R.id.seekBarRoomLight);
        imageButtonOnLight = view.findViewById(R.id.imageButtonOnLight);
        textViewNumber = view.findViewById(R.id.textViewNumber);
        textViewUnderNumberTitle = view.findViewById(R.id.textViewUnderNumberTitle);
        textViewNearNumberTitle = view.findViewById(R.id.textViewNearNumberTitle);

        imageButtonOnLight.setOnClickListener(this);

        seekBarRoomLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewNumber.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(argument!=null){
            textViewUnderLineTitle.setText(getActivity().getIntent().getStringExtra("title"));
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButtonOnLight:
                imageButtonOnLight.setSelected(!imageButtonOnLight.isSelected());
                if(imageButtonOnLight.isSelected()){
                    textViewNumber.setVisibility(View.VISIBLE);
                    textViewUnderNumberTitle.setVisibility(View.VISIBLE);
                    textViewNearNumberTitle.setVisibility(View.VISIBLE);
                    seekBarRoomLight.setVisibility(View.VISIBLE);
                }
                else{
                    textViewNumber.setVisibility(View.GONE);
                    textViewUnderNumberTitle.setVisibility(View.GONE);
                    textViewNearNumberTitle.setVisibility(View.GONE);
                    seekBarRoomLight.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}