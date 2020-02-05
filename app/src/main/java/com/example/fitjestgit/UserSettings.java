package com.example.fitjestgit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.fitjestgit.EatingPlans;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class UserSettings extends AppCompatActivity {

    private EditText editTextWeight;
    private EditText editTextHeight;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private Spinner spinnerTypes;
    private EditText editTextAge;
    Date currentDay= Calendar.getInstance().getTime();
    String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(currentDay);
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private Button buttonSend;
    private RadioGroup radioGroup;
    private RadioGroup radioGroup2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_settings);
        editTextHeight= findViewById(R.id.editTextHeight);
        editTextWeight= findViewById(R.id.editTextWeight);
        radioGroup2= findViewById(R.id.radioGroup2);
        buttonSend= findViewById(R.id.buttonSendSettings);
        radioGroup= findViewById(R.id.radioGroup);
        editTextAge= findViewById(R.id.editTextAge);
        spinnerTypes= findViewById(R.id.spinnerTypes);
        final List<EatingPlans> eatingPlansList= new ArrayList<>();
        db.collection("ActivityTypes")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Food food= documentSnapshot.toObject(Food.class);
                            EatingPlans eatingPlans= documentSnapshot.toObject(EatingPlans.class);
                            String ratio= String.valueOf(1);
                            String describe= eatingPlans.getDescribe();
                            EatingPlans eatingPlans1= new EatingPlans(ratio,describe);

                            eatingPlansList.add(eatingPlans1);


                        }
                        ArrayAdapter<EatingPlans> adapter= new ArrayAdapter<EatingPlans>(UserSettings.this,R.layout.support_simple_spinner_dropdown_item,eatingPlansList);

                        spinnerTypes.setAdapter(adapter);

                    }
                });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendSettings(eatingPlansList);
            }
        });


    }




    public void SendSettings(final List<EatingPlans> eatingPlansList){
    int radioID= radioGroup.getCheckedRadioButtonId();
    radioButton= findViewById(radioID);
    int radioID2= radioGroup2.getCheckedRadioButtonId();
    radioButton2= findViewById(radioID2);


        Double weight=Double.valueOf(editTextWeight.getText().toString());
        Double height= Double.valueOf(editTextHeight.getText().toString());
        String sex= radioButton.getText().toString();
        String type= radioButton2.getText().toString();
        Integer age= Integer.valueOf(editTextAge.getText().toString());

        EatingPlans eatingPlans= eatingPlansList.get(spinnerTypes.getSelectedItemPosition());
        //String ratio= eatingPlans.getRatio();
        String ratio= String.valueOf(1);
        User user= new User(weight,height,sex,type,age,ratio);
        db.collection("UserSettings").document("data").set(user);
        nextSlide();
    }
    public void nextSlide(){
        Intent intent= new Intent(this,Overview.class);
        startActivity(intent);
    }
}
