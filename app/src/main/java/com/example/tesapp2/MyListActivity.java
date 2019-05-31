package com.example.tesapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MyListActivity extends AppCompatActivity {

    ListView final_list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        final_list = (ListView) findViewById(R.id.mylist);
        final_list.setEnabled(true);
      String final_selection = "";

        for (String Selections : CheckedItem.myList){
            final_selection = final_selection + Selections + "\n";
        }
       // final_list.setText("plm plm plm plm ");
        final_list.setEnabled(true);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                CheckedItem.myList);
        final_list.setAdapter(adapter);
    }

    public  void finalSelection (View view){

        String final_selection = "";

        for (String Selections : CheckedItem.myList){
           final_selection = final_selection + Selections + "\n";
       }
      // final_list.setText("plm plm plm plm ");
      final_list.setEnabled(true);
    }
}
