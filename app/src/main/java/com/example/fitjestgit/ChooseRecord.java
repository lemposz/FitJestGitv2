package com.example.fitjestgit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseRecord extends AppCompatActivity {
private Button buttonAddFood;
private Button buttonAddExercise;
    private ImageView imageViewOverview;
    private ImageView imageViewHistory;
    private ImageView imageViewMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_record);
        buttonAddFood= findViewById(R.id.buttonAddFood);
        buttonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddFood();
            }
        });
        buttonAddExercise= findViewById(R.id.buttonAddExercise);
        buttonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddExercise();
            }
        });
        imageViewHistory= findViewById(R.id.imageButtonHistory);
        imageViewMore= findViewById(R.id.imageButtonMore);
        imageViewOverview= findViewById(R.id.imageButtonMore);


    }
    public void toSettings(){
        Intent intent= new Intent(this,UserSettings.class);
        startActivity(intent);
    }

    public void toOverview(View view){
        Intent intent= new Intent(this,Overview.class);
        startActivity(intent);
    }


    public void toHistory(View view){
        Intent intent= new Intent(this,HistoryController.class);
        startActivity(intent);
    }

    public void toMore(View view){
        Intent intent= new Intent(this,UserSettings.class);
        startActivity(intent);
    }
    public void toAddFood(){
        Intent intent= new Intent(this, RecordActivity.class);
        startActivity(intent);
    }
    public void toAddExercise(){
        Intent intent= new Intent( this,ExerciseActivity.class);
        startActivity(intent);
    }
}
