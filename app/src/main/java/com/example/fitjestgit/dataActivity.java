package com.example.fitjestgit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class dataActivity extends AppCompatActivity {

    private String nameofMeal;
    private Integer caloriesofMeal;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        textView= findViewById(R.id.textView);

        Intent intent= getIntent();
        caloriesofMeal= intent.getIntExtra("calories",0);
        textView.setText(caloriesofMeal.toString());
    }
}
