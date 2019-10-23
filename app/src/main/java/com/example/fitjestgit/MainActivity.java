package com.example.fitjestgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fitjestgit.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Internal;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";


    private static final String KEY_TITLE= "title";
    private static final String KEY_DESCRIPTION= "description";

    private EditText editTextTitle;
    private EditText editTextCalories;
    private TextView textViewFood;
    private SearchableSpinner searchableSpinner;


    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference foodRef= db.collection("Food");
    private DocumentReference noteRef= db.collection("Notebook").document("Notka");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    editTextTitle= findViewById(R.id.edit_text_title);
    editTextCalories=findViewById(R.id.edit_text_calories);
    searchableSpinner= findViewById(R.id.spinnerFood);
    textViewFood= findViewById(R.id.textViewFood);
    searchableSpinner.setPrompt("Znajdz jedzenie");


    }

    public void addFood(View view){
        String name= editTextTitle.getText().toString();
        Integer calories= Integer.parseInt(editTextCalories.getText().toString());
        Food food= new Food(name,calories);
        foodRef.add(food);
    }
    public void saveFood(View view){
        Map<String,Object> food= new HashMap<>();
        if(!editTextTitle.getText().toString().isEmpty() && !editTextCalories.getText().toString().isEmpty() ) {
        food.put("Nazwa",editTextTitle.getText().toString());
        food.put("Kalorie", Integer.parseInt(editTextCalories.getText().toString()));

            db.collection("Food").document(editTextTitle.getText().toString())
                    .set(food)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "WYSLANE MORDO", Toast.LENGTH_SHORT).show();
                            editTextCalories.getText().clear();
                            editTextTitle.getText().clear();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Błąd", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this, "Wypełnij oba pola", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveNote(View view){
        String title= editTextTitle.getText().toString();
        String calories= editTextCalories.getText().toString();

        Map<String, Object> note= new HashMap<>();
        note.put(KEY_TITLE,title);
        note.put(KEY_DESCRIPTION, calories);

        noteRef.set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "SUKSES", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "NIE wyszło", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }


    public void loadFoods(View view){
        final List<Food> foodList= new ArrayList<>();

        db.collection("Food")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data="";
                    for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        Food food= documentSnapshot.toObject(Food.class);
                        String name= food.getName();
                        Integer calories= food.getCalories();
                        Food food1= new Food(name,calories);
                        foodList.add(food1);
                        data += "Nazwa: "+ name+ "\n Kalorie: "+ calories+ "\n\n";
                    }
ArrayAdapter<Food>adapter= new ArrayAdapter<Food>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,foodList);
                    searchableSpinner.setAdapter(adapter);
                    textViewFood.setText(data);
                    }
                });
    }

    /*public void loadFoods(View view){
        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                textViewFood.setText(documentSnapshot.getData().toString());
                                Log.d(TAG, documentSnapshot.getId()+ "=>" + documentSnapshot.getData());
                            }


                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }*/
   /* public void loadNote(View view){
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String title= documentSnapshot.getString(KEY_TITLE);
                            String description= documentSnapshot.getString(KEY_DESCRIPTION);

                            //Map<String,Object> note= documentSnapshot.getData();


                            textViewData.setText("Title: "+  title+ "\n"+ "Description: "+ description);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Dokument nie istnieje", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void loadAll(View view){
        db.collection("Notebook")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if(task.isSuccessful()){
                           List<String> lista= new ArrayList<>();
                           for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                               lista.add(documentSnapshot.getId());
                               textViewData.setText(""+ lista);
                               Log.d(TAG, documentSnapshot.getId()+ " =>" +documentSnapshot.getData());
                           }
                       }else {
                           Log.d(TAG, "ERROR",task.getException());
                       }
                    }
                });
    }*/
}
