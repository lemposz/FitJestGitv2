package com.example.fitjestgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;

public class HistoryController extends AppCompatActivity implements DatePickerListener {

    private ListView historyListView;
    private TextView textViewDate;

    private ImageButton imageButtonMore;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    Date currentDay= Calendar.getInstance().getTime();
    String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(currentDay);
    private ArrayList<Food> historyArrayList;
    private Integer totalCalories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_controller);
        historyListView=findViewById(R.id.historyListView);
        imageButtonMore=findViewById(R.id.imageButtonMore);
        HorizontalPicker picker= (HorizontalPicker) findViewById(R.id.datePicker);
        picker.setListener(this)
                .init();


        picker.setBackgroundColor(Color.LTGRAY);
        picker.setDate(new DateTime());
        //readFromTheDatabase();

    }

    private void readFromTheDatabase(String data) {
        final PieView pieView = (PieView)findViewById(R.id.pie_view);
        final ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();
        final List<Food> foodList= new ArrayList<>();
        db.collection(data)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Integer totalCalories=0;
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Food food= documentSnapshot.toObject(Food.class);
                            String name= food.getName();
                            Integer calories= food.getCalories();
                            Food food1= new Food(name,calories);
                            foodList.add(food1);
                            totalCalories+=calories;


                        }
                        ArrayAdapter<Food>adapter= new ArrayAdapter<Food>(HistoryController.this,R.layout.support_simple_spinner_dropdown_item,foodList);
                        //Toast.makeText(HistoryController.this, totalCalories, Toast.LENGTH_SHORT).show();
                        historyListView.setAdapter(adapter);
                        if(adapter.isEmpty()){
                            Toast.makeText(HistoryController.this, "Brak danych z tego dnia", Toast.LENGTH_SHORT).show();

                            pieHelperArrayList.add(new PieHelper(100,Color.rgb(245,238,220)));
                            pieView.setDate(pieHelperArrayList);
                            pieView.showPercentLabel(false);
                        }
                        else {
                            Integer totalCaloriesAte=(totalCalories*100)/1680;
                            Integer rest= 100-totalCaloriesAte;
                            Toast.makeText(HistoryController.this, totalCaloriesAte.toString(), Toast.LENGTH_SHORT).show();

                            pieHelperArrayList.add(new PieHelper(totalCaloriesAte,Color.rgb(34,0,121)));
                            pieHelperArrayList.add(new PieHelper(rest,Color.rgb(245,238,220)));
                            pieView.setDate(pieHelperArrayList);
                            pieView.showPercentLabel(false);
                        }

                    }
                });
    }

    public void toMore(View view){
        Intent intent= new Intent(this,UserSettings.class);
        startActivity(intent);
    }

    public void toOverview(View view){
        Intent intent= new Intent(this,Overview.class);
        startActivity(intent);
    }
    @Override
    public void onDateSelected(DateTime dateSelected) {
        String data= dateSelected.toString().split("T")[0];
        //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        readFromTheDatabase(data);
    }
}
