package com.example.logictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.logictest.Common.ToolbarSetup;
import com.example.logictest.FabMenu.FABsMenu;
import com.example.logictest.FabMenu.FABsMenuListener;
import com.example.logictest.FabMenu.TitleFAB;
import com.example.logictest.Interface.FragmentBackPressed;
import com.example.logictest.Model.Choices;
import com.example.logictest.UI.DatePickerFragment;
import com.example.logictest.UI.FormulaFragment;
import com.example.logictest.UI.TextBoxFragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ToolbarSetup toolbarSetup;
    public FragmentBackPressed backPressedInterface;

    private FABsMenu fabsMenu;
    private TitleFAB textBox,datePicker,result;

    private  ArrayList<Choices> choicesData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }



    private void initViews() {

        toolbar = findViewById(R.id.toolbar);

        toolbarSetup =new ToolbarSetup(this,toolbar,"Test ", R.drawable.ic_arrow_back);

        fabsMenu = (FABsMenu)findViewById(R.id.fabs_menu);

        textBox = (TitleFAB)findViewById(R.id.textBox);
        datePicker = (TitleFAB)findViewById(R.id.datePicker);
        result =(TitleFAB)findViewById(R.id.result);


        textBox.setOnClickListener(this);
        datePicker.setOnClickListener(this);
        result.setOnClickListener(this);



        fabsMenu.setMenuListener(new FABsMenuListener() {
            @Override
            public void onMenuClicked(FABsMenu fabMenu) {
                super.onMenuClicked(fabMenu);

            }

            @Override
            public void onMenuExpanded(FABsMenu fabsMenu) {
                super.onMenuExpanded(fabsMenu);
            }

            @Override
            public void onMenuCollapsed(FABsMenu fabsMenu) {
                super.onMenuCollapsed(fabsMenu);
            }

        });



    }

    @Override
    protected void onResume() {
        super.onResume();

      choicesData = loadJsonData();

        Log.e("ArrayList","-="+new Gson().toJson(choicesData));

    }

    private ArrayList<Choices> loadJsonData() {

        JSONArray jsonArray = readJsonFromAsset("choices.json");

        Log.e("read JSON DATA","data:-"+jsonArray);

        ArrayList<Choices> listData = new ArrayList<>();

        for(int i=0; i<jsonArray.length();i++){
            try {

                Choices choicePojo = new Choices();

                JSONObject jsonObject =  jsonArray.getJSONObject(i);
                String type = jsonObject.getString("type");
                String identifier = jsonObject.getString("identifier");
                String value= jsonObject.getString("value");

                if(jsonObject.has("prompt") && !jsonObject.isNull("prompt")){
                    String prompt = jsonObject.getString("prompt");
                    choicePojo.setPrompt(prompt);
                }

                choicePojo.setType(type);
                choicePojo.setIdentifier(identifier);
                choicePojo.setValue(value);

                if(jsonObject.has("jslogic") && !jsonObject.isNull("jslogic")){
                    String jslogic = jsonObject.getString("jslogic");
                    choicePojo.setJslogic(jslogic);
                }

                if(jsonObject.has("choices") && !jsonObject.isNull("choices")){

                    ArrayList<String> choiceList = new ArrayList<>();
                    JSONArray jsonArray1 = jsonObject.getJSONArray("choices");
                    if(jsonArray1.length()!=0){

                        for(int j= 0 ; j< jsonArray1.length();j++){
                            String choice = (String) jsonArray1.get(j);
                            choiceList.add(choice);
                        }
                    }
                    choicePojo.setChoices(choiceList);
                }

                listData.add(choicePojo);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



       return listData;
    }


    private JSONArray readJsonFromAsset(String filename) {
        JSONArray jsonArray = null;
        try {
            InputStream is = getResources().getAssets().open(filename);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String (data,"UTF-8");
            jsonArray = new JSONArray(json);

        }catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
        return  jsonArray;
    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;
        Fragment tempFragment = null;
        boolean flagToAddFragment = true;

        switch (v.getId()){

            case R.id.datePicker:
                if(fabsMenu.isExpanded()){
                    fragment = new DatePickerFragment();
                    tempFragment = getCurrentFragment();
                    Bundle lifestyleBundle=new Bundle();

                    Choices choice = getChoiceData("date_picker");

                    lifestyleBundle.putSerializable("choiceData",choice);

                    fragment.setArguments(lifestyleBundle);
                    fabsMenu.collapse();
                }

                break;

            case R.id.textBox:
                if(fabsMenu.isExpanded()){
                    fragment = new TextBoxFragment();
                    tempFragment = getCurrentFragment();
                    Bundle lifestyleBundle=new Bundle();

                    Choices choice = getChoiceData("textBox");

                    lifestyleBundle.putSerializable("choiceData",choice);

                    fragment.setArguments(lifestyleBundle);
                    fabsMenu.collapse();
                }

                break;

            case R.id.result:

                if(fabsMenu.isExpanded()){
                    fragment = new FormulaFragment();
                    tempFragment = getCurrentFragment();
                    Bundle lifestyleBundle=new Bundle();

                    Choices choice = getChoiceData("formula");

                    lifestyleBundle.putSerializable("choiceData",choice);

                    fragment.setArguments(lifestyleBundle);
                    fabsMenu.collapse();
                }

                break;


        }

        if (fragment != null)
        {
            if (flagToAddFragment)
            {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.flContentRoot, fragment)
                        .addToBackStack(null)
                        .commit();
            }

        }
        else
        {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }



    }

    private Choices getChoiceData(String seachText) {

        Choices choices = null;

        for(int i=0;i<choicesData.size();i++){

             choices = choicesData.get(i);
            if(choices.getType().equalsIgnoreCase(seachText)){
                return choices;
            }

        }
        return  choices;
    }

    Fragment getCurrentFragment()
    {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.flContentRoot);
        return currentFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments())
        {
            handleResult(fragment, requestCode, resultCode, data);
        }

    }


    private void handleResult(Fragment frag, int requestCode, int resultCode, Intent data)
    {

        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null)
        {
            for (Fragment f : frags)
            {
                if (f != null)
                {
                    handleResult(f, requestCode, resultCode, data);
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
        {
            for (Fragment fragment : getSupportFragmentManager().getFragments())
            {
                handleBackPressed(fragment);
            }

        }

    }


    private void handleBackPressed(Fragment fragment)
    {
        if (fragment instanceof FragmentBackPressed)
        {
            backPressedInterface = (FragmentBackPressed) fragment;
            backPressedInterface.onBackKeyPressed();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}