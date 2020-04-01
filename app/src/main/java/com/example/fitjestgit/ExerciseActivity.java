package com.example.fitjestgit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExerciseActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextCalories;
    Date currentDay= Calendar.getInstance().getTime();
    private Button buttonSendCaloriesBurnt;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(currentDay);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        editTextCalories=findViewById(R.id.editTextCaloriesBurntbyExercise);
        editTextName=findViewById(R.id.editTextNameofExercise);
        buttonSendCaloriesBurnt=findViewById(R.id.buttonSendCaloriesBurnt);
        String name= editTextName.toString();
        String calories= editTextCalories.toString();
        addBurntToHistory(buttonSendCaloriesBurnt);
    }
    private void addBurntToHistory(final Button buttonSendCaloriesBurnt){
        buttonSendCaloriesBurnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= editTextName.getText().toString();
                Integer calories= Math.round(Integer.parseInt(editTextCalories.getText().
                        toString())*-1);
                Exercise exercise= new Exercise(name,calories);
                db.collection(modifiedDate).document().set(exercise);
                toOverview();

            }
        });
    }
    public void toOverview(){
        Intent intent= new Intent(this,Overview.class);
        startActivity(intent);
    }
}
