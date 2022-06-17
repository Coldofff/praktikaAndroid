package com.example.praktikaandroid.Fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.praktikaandroid.R;

public class ThermostatFragment extends Fragment implements View.OnClickListener{

    ImageButton imageButton, imageButtonCool;
    TextView textViewUnderLineTitle;
    ImageView imageViewTerm;
    ConstraintLayout layout_turn;
    Switch switchThermostat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thermostat, container,false);

        Bundle argument = getActivity().getIntent().getExtras();

        textViewUnderLineTitle = view.findViewById(R.id.textViewUnderLineTitle);
        imageButton = view.findViewById(R.id.imageButtonHeating);
        imageButton.setOnClickListener(this);

        imageButtonCool = view.findViewById(R.id.imageButtonCool);
        imageButtonCool.setOnClickListener(this);

        layout_turn=view.findViewById(R.id.layout_turn);
        switchThermostat=view.findViewById(R.id.switchThermostat);
        imageViewTerm = view.findViewById(R.id.imageViewTerm);

        switchThermostat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchThermostat.isChecked()){
                    layout_turn.setVisibility(View.VISIBLE);
                    imageViewTerm.setVisibility(View.VISIBLE);

                }
                else {
                    layout_turn.setVisibility(View.INVISIBLE);
                    imageViewTerm.setVisibility(View.INVISIBLE);
                }
            }
        });

        if(argument!=null){
            textViewUnderLineTitle.setText(getActivity().getIntent().getStringExtra("title"));
        }
        return view;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageButtonHeating:
                imageButton.setSelected(!imageButton.isSelected());
                if(imageButton.isSelected()){
                    imageButton.setImageResource(R.drawable.heating_on);
                    imageButtonCool.setImageResource(R.drawable.cool_button_off);
                    imageButtonCool.setSelected(false);
                }
                else{
                    imageButton.setImageResource(R.drawable.heating_button);
                }
                break;
            case R.id.imageButtonCool:
                imageButtonCool.setSelected(!imageButtonCool.isSelected());
                if(imageButtonCool.isSelected()){
                    imageButtonCool.setImageResource(R.drawable.cool_button);
                    imageButton.setImageResource(R.drawable.heating_button);
                    imageButton.setSelected(false);
                }
                else{
                    imageButtonCool.setImageResource(R.drawable.cool_button_off);
                }
                break;
        }
    }
}