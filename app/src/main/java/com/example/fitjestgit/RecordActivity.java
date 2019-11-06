package com.example.fitjestgit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class RecordActivity extends AppCompatActivity {

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private Button buttonNext;
    private SearchableSpinner searchableSpinner;
    private ImageView imageViewOverview;
    private ImageView imageViewHistory;
    private ImageView imageViewMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        buttonNext=findViewById(R.id.buttonNext);
        imageViewHistory= findViewById(R.id.imageButtonHistory);
        imageViewMore= findViewById(R.id.imageButtonMore);
        imageViewOverview= findViewById(R.id.imageButtonMore);
        searchableSpinner=findViewById(R.id.spinnerFoods);
        db.collection("UserSettings").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots.isEmpty()){
                    toSettings();
                }
                else{

                }
            }
        });
        final List<Food> foodList= new ArrayList<>();

        db.collection("Food")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Food food= documentSnapshot.toObject(Food.class);
                            String name= food.getName();
                            Integer calories= food.getCalories();
                            Food food1= new Food(name,calories);
                            foodList.add(food1);


                        }
                        ArrayAdapter<Food> adapter= new ArrayAdapter<Food>(RecordActivity.this,R.layout.support_simple_spinner_dropdown_item,foodList);

                        searchableSpinner.setAdapter(adapter);
                        searchableSpinner.setTitle("Wybierz danie");


                    }
                });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFood(foodList);
            }
        });
    }

    public  void selectedFood(final List<Food> foodList ){
        final Intent intent= new Intent(this,dataActivity.class);
        Food food= foodList.get(searchableSpinner.getSelectedItemPosition());
        intent.putExtra("name", food.getName());
        intent.putExtra("calories",food.getCalories());
        startActivity(intent);
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
}
