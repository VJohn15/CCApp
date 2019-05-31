package com.example.tesapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private CardView Fruits, Vegetables, Meat, Cereals, SeeFood, Bakery, Dairy, Sweet, Alcohol, non_Alcohol;
    Context MainActivity = this;
    static ArrayList<String> listOfFood = new ArrayList<>();
    private Button My_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //defining Cards
        Fruits = (CardView) findViewById(R.id.fruits);
        Vegetables = (CardView) findViewById(R.id.vegetables);
        Meat = (CardView) findViewById(R.id.meat);
        Cereals = (CardView) findViewById(R.id.cereals);
        SeeFood = (CardView) findViewById(R.id.seefood);
        Bakery = (CardView) findViewById(R.id.bakery);
        Dairy = (CardView) findViewById(R.id.dairy);
        Sweet = (CardView) findViewById(R.id.sweet);
        Alcohol = (CardView) findViewById(R.id.alcohol);
        non_Alcohol = (CardView) findViewById(R.id.non_alcohol);
        My_List = (Button) findViewById(R.id.my_list);

        //Add OnClick listener to the cards
        Fruits.setOnClickListener(this);
        Vegetables.setOnClickListener(this);
        Meat.setOnClickListener(this);
        Cereals.setOnClickListener(this);
        SeeFood.setOnClickListener(this);
        Bakery.setOnClickListener(this);
        Dairy.setOnClickListener(this);
        Sweet.setOnClickListener(this);
        Alcohol.setOnClickListener(this);
        non_Alcohol.setOnClickListener(this);
        My_List.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.fruits: {
                    changeView("food", "fruits");
                    break;
                }
                case R.id.vegetables: {
                    changeView("food", "vegetables");
                    break;
                }
                case R.id.meat: {
                    changeView("food", "meat");
                    break;
                }
                case R.id.cereals: {
                    changeView("food", "cereals");
                    break;
                }
                case R.id.seefood: {
                    changeView("food", "seafood");
                    break;
                }
                case R.id.bakery: {
                    changeView("food", "bakery");
                    break;
                }
                case R.id.dairy: {
                    changeView("food", "dairy");
                    break;
                }
                case R.id.sweet: {
                    changeView("food", "sweet");
                    break;
                }
                case R.id.alcohol: {
                    changeView("beverages", "alcoholic");
                    break;
                }
                case R.id.non_alcohol: {
                    changeView("beverages", "non_alcoholic");
                    break;
                }
                case R.id.my_list:{
                    openActivity();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }




    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("food_alch.JSON");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            json = new String(buffer, "UTF-8");
            is.close();


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void changeView(String type, String fieldName) throws JSONException {


        JSONObject obj = new JSONObject(loadJSONFromAsset(this));
        List<String> getOnlyFieldType = new ArrayList<>();


//        for (int i = 0; i < obj.length(); i++) {
//            JSONObject jo = obj.getJSONObject(i);
//            if (jo.has(type)) {
//                JSONArray myArray = jo.getJSONArray(type);
//                for (int j = 0; j < myArray.length(); j++) {
//                    JSONObject jObj = myArray.getJSONObject(j);
//                    if (jObj.has(fieldName)) {
//                        JSONArray tempJsonArray = jObj.getJSONArray(fieldName);
//                        for (int k = 0; k < tempJsonArray.length(); k++)
//                            //getOnlyFieldType.add(tempJsonArray.getString(k));
//                            getOnlyFieldType.add(tempJsonArray.toString());
//                    }
//                }
//            }
//        }

        Intent i = new Intent(this, ProductsTemplate.class);
        i.putExtra("fieldData", obj.get(type).toString());
        i.putExtra("fieldName", fieldName);
        startActivity(i);
    }


    public void openActivity() {
        Intent intent = new Intent(this, MyListActivity.class);
        startActivity(intent);
    }

}
