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

public class FormulaFragment extends Fragment implements FragmentBackPressed  {

    private View rootView;
    private Choices choiceData ;
    private TextView txtType,txtResult;
    private TextInputEditText edtFirstName ,edtLastName;
    private Button btnSave;
    private String errorText ="";
    private CoordinatorLayout coordinator;
    private DataManager dataManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_formula,container,false);

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
        txtResult = rootView.findViewById(R.id.txtResult);

    }


    @Override
    public void onResume() {
        super.onResume();

        if(choiceData!=null){

            txtType.setText(choiceData.getType().toUpperCase());

            String firstname = dataManager.getFirstName();
            String lastName= dataManager.getlastName();

            if(firstname!=null && lastName!=null){
                txtResult.setText(new StringBuilder().append(firstname).append(" ").append(lastName).toString());
            }


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
