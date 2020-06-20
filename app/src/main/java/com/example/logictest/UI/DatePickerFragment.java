package com.example.logictest.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.logictest.Interface.FragmentBackPressed;
import com.example.logictest.Model.Choices;
import com.example.logictest.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DatePickerFragment extends Fragment implements FragmentBackPressed {

    private View rootView;
    private Choices choiceData ;
    private TextView txtType;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_date_picker,container,false);

        if(getArguments()!=null){


            choiceData= (Choices) getArguments().getSerializable("choiceData");

            Log.e("Got","List"+new Gson().toJson(choiceData));

        }

        initView();

        return rootView;

    }

    private void initView() {

        txtType = rootView.findViewById(R.id.txtType);

    }


    @Override
    public void onResume() {
        super.onResume();

        if(choiceData!=null){

            txtType.setText(choiceData.getType().toUpperCase());

        }

    }

    @Override
    public void onBackKeyPressed() {
        if (getActivity() != null)
        {
            Log.e("Fragment Count", "" + getActivity().getSupportFragmentManager()
                    .getBackStackEntryCount());

            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
