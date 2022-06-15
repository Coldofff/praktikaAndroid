package com.example.praktikaandroid.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.praktikaandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThermostatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThermostatFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThermostatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThermostatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThermostatFragment newInstance(String param1, String param2) {
        ThermostatFragment fragment = new ThermostatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ImageButton imageButton, imageButtonCool;
    TextView textViewUnderLineTitle;
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