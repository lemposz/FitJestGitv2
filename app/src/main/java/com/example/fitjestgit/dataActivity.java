package com.example.fitjestgit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class dataActivity extends AppCompatActivity {

    private String nameofMeal;
    private Integer caloriesofMeal;
    private TextView textViewName;
    private TextView textViewCalories;
    private Button buttonCount;
    private TextView textViewCalories100;
    private EditText editTextGrams;
    private double liczba;
    private String elo ;
    private Integer liczbb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        textViewCalories100= findViewById(R.id.textViewCalories100);
        textViewName= findViewById(R.id.textViewName);
        textViewCalories= findViewById(R.id.textViewCalories);
        buttonCount= findViewById(R.id.buttonCount);
        editTextGrams= findViewById(R.id.editTextGrams);
        Intent intent= getIntent();
        caloriesofMeal= intent.getIntExtra("calories",0);
        nameofMeal=intent.getStringExtra("name");
        textViewName.setText(nameofMeal);
        textViewCalories100.setText("x "+caloriesofMeal*0.01+" kalorii");
        setOnClick(buttonCount, caloriesofMeal);


    }

    private void setOnClick(final Button buttonCount, final Integer integer){
        buttonCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                liczba= integer*0.01* Integer.parseInt(editTextGrams.getText().toString());
                textViewCalories.setText(String.valueOf(Math.round(liczba)));
            }
        });
    }


}
