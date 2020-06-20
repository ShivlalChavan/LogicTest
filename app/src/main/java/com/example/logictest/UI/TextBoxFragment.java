package com.example.logictest.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.logictest.Common.DataManager;
import com.example.logictest.Common.Utility;
import com.example.logictest.Interface.FragmentBackPressed;
import com.example.logictest.Model.Choices;
import com.example.logictest.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class TextBoxFragment extends Fragment implements FragmentBackPressed , View.OnClickListener {

    private View rootView;
    private Choices choiceData ;
    private TextView txtType;
    private TextInputEditText edtFirstName ,edtLastName;
    private Button btnSave;
    private String errorText ="";
    private CoordinatorLayout coordinator;
    private DataManager dataManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_text_box,container,false);

        if(getArguments()!=null){


            choiceData= (Choices) getArguments().getSerializable("choiceData");

            Log.e("Got","List"+new Gson().toJson(choiceData));

        }

        initView();

        return rootView;

    }

    private void initView() {

        dataManager = new DataManager(getActivity());
        coordinator = rootView.findViewById(R.id.coordinator);
        txtType = rootView.findViewById(R.id.txtType);
        edtFirstName = (TextInputEditText) rootView.findViewById(R.id.edtFirstName);
        edtLastName = (TextInputEditText) rootView.findViewById(R.id.edtLastName);
        btnSave = (Button)rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSave:
                String firstName = edtFirstName.getText().toString();
                String lastName = edtLastName.getText().toString();

                if(firstName.isEmpty()){
                    if(errorText.equalsIgnoreCase("")){
                        errorText = "Please enter first name.";
                    }
                }

                if(lastName.isEmpty()){
                    if(errorText.equalsIgnoreCase("")){
                        errorText = "Please enter last name.";
                    }
                }

                if(errorText.equalsIgnoreCase("")){

                    dataManager.setFirstName(firstName.trim());
                    dataManager.setLastName(lastName.trim());

                     clearFields();


                }else {
                    Utility.showSnackBar(errorText,coordinator);
                    errorText="";
                }

                break;

        }

    }

    private void clearFields() {

        edtLastName.setText("");
        edtFirstName.setText("");

        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}
