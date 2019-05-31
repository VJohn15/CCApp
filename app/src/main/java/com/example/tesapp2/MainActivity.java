package com.example.tesapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private RadioGroup radioGroup;
    private RadioButton radioButton;


    int age;
    int height;
    int weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText) findViewById(R.id.editText8);
        editText2 = (EditText) findViewById(R.id.editText9);
        editText3 = (EditText) findViewById(R.id.editText10);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        submitButton = (Button) findViewById(R.id.button);


        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();


      int age1 = preferences.getInt("age", 0);
        editText1.setText(""+age1);

        int height1 = preferences.getInt("height",0);
        editText2.setText(""+ height1);

        int weight1 = preferences.getInt("weight", 0);
        editText3.setText(""+ weight1);




        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                int radioId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(radioId);

                // Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();

               age = Integer.parseInt(editText1.getText().toString());
               height = Integer.parseInt(editText2.getText().toString());
               weight = Integer.parseInt(editText3.getText().toString());



               editor.putInt("age", age);
                editor.commit();
               editor.putInt("height", height);
                editor.commit();
               editor.putInt("weight", weight);
                editor.commit();


                openMainActivity();
            }


        });


    }



    public void openMainActivity(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = (RadioButton) findViewById(radioId);

    }
}
