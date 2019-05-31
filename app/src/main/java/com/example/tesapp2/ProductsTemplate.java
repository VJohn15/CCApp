package com.example.tesapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;



public class ProductsTemplate extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);

        SharedPreferences preferences = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        Boolean savesLoaded = false;

        if(preferences.contains("Saves")){
            savesLoaded = true;
        }



        final String fieldName = getIntent().getExtras().getString("fieldName");
        String fieldData = getIntent().getExtras().getString("Saves");
        if (!savesLoaded){
            fieldData = getIntent().getExtras().getString("fieldData");
        }
        TextView fieldNameTW = findViewById(R.id.fieldName);
        GridLayout gridLayout= findViewById(R.id.grid);


        String data = preferences.getString("Saves", "No Data!");
        try{

            JSONObject obj = new JSONObject(fieldData);

            final JSONArray arrJson = obj.getJSONArray(fieldName);
            fieldNameTW.setText(fieldName);
            for(int i = 0 ; i < arrJson.length(); i++){
                TextView tw = new TextView(this);
                final String fieldNameText = arrJson.get(i).toString();
                final CheckBox rb = new CheckBox(this);

                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v ) {
                        editor.putBoolean(fieldNameText, rb.isChecked());
                        editor.commit();
                        for(String s : CheckedItem.myList ){
                            Log.d("Food to eat : ",s);

                        }
                         if(rb.isChecked()){
                            Main2Activity.listOfFood.add(fieldNameText);
                             CheckedItem.myList.add(fieldNameText);

                        } else {
                            Main2Activity.listOfFood.remove(fieldNameText);
                             CheckedItem.myList.remove(fieldNameText);
                        }
                    }


                });


                rb.setChecked(preferences.getBoolean(fieldNameText,false));


                float margin = 20;
                tw.setX(30);
                tw.setY(margin * i);

                rb.setX(0);
                rb.setY(margin * i);

                tw.setText(arrJson.get(i).toString());

                gridLayout.addView(tw);
                gridLayout.addView(rb);

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }





    }




    }
