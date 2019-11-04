package com.example.fitjestgit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import im.dacer.androidcharts.PieHelper;

public class Overview extends AppCompatActivity {
    Date currentDay= Calendar.getInstance().getTime();
    String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(currentDay);
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        db.collection(modifiedDate)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Integer totalCalories=0;
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Food food= documentSnapshot.toObject(Food.class);

                            if(documentSnapshot.exists()){
                            Integer calories= food.getCalories();

                            totalCalories+=calories;
                            }
                            else {
                                totalCalories=0;
                            }

                        }
                        Totalcounter(totalCalories);




                    }
                });
    }

    public void Totalcounter(Integer totalCalories){
        Toast.makeText(this, totalCalories.toString(), Toast.LENGTH_SHORT).show();
    }
}
