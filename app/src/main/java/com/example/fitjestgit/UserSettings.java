package com.example.fitjestgit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;

public class UserSettings extends AppCompatActivity {

    private EditText editTextWeight;
    private EditText editTextHeight;
    private RadioButton radioButton;
    Date currentDay= Calendar.getInstance().getTime();
    String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(currentDay);
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private Button buttonSend;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_settings);
        editTextHeight= findViewById(R.id.editTextHeight);
        editTextWeight= findViewById(R.id.editTextWeight);

        buttonSend= findViewById(R.id.buttonSendSettings);
        radioGroup= findViewById(R.id.radioGroup);


    }


    public void SendSettings(View view){
    int radioID= radioGroup.getCheckedRadioButtonId();
    radioButton= findViewById(radioID);
        Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();


        Double weight=Double.valueOf(editTextWeight.getText().toString());
        Double height= Double.valueOf(editTextHeight.getText().toString());
        String sex= radioButton.getText().toString();
        User user= new User(weight,height,sex);
        db.collection("UserSettings").document(modifiedDate).set(user);
        nextSlide();
    }
    public void nextSlide(){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
