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
    private ImageButton imageButtonRecord;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    Date currentDay= Calendar.getInstance().getTime();
    String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(currentDay);
    private ArrayList<Food> historyArrayList;
    private Integer totalCalories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_controller);
        imageButtonRecord= findViewById(R.id.imageButtonRecord);
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
                        getAllData(totalCalories);
                        ArrayAdapter<Food>adapter= new ArrayAdapter<Food>(HistoryController.this,R.layout.support_simple_spinner_dropdown_item,foodList);
                        //Toast.makeText(HistoryController.this, totalCalories, Toast.LENGTH_SHORT).show();
                        historyListView.setAdapter(adapter);
                        if(adapter.isEmpty()){
                            Toast.makeText(HistoryController.this, "Brak danych z tego dnia ", Toast.LENGTH_SHORT).show();

                            pieHelperArrayList.add(new PieHelper(100,Color.rgb(245,238,220)));
                            pieView.setDate(pieHelperArrayList);
                            pieView.showPercentLabel(false);
                        }
                        else {

                        }

                    }
                });
    }


    public void getAllData(final Integer totalCalories){

        db.collection("UserSettings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            User user= documentSnapshot.toObject(User.class);
                            String sex= user.getSex();
                            String type= user.getType();
                            String ratio= user.getRatio();
                            double weight= user.getWeight();
                            double height= user.getHeight();
                            int age= user.getAge();
                            Integer calories= totalCalories;
                            countBMR(sex,ratio,weight,height,age,type,calories);
                        }




                    }
                });
    }

    public void countBMR(String sex,String ratio,double weight, double height, int age,String type,Integer totalCalories){
        double typeofdiet=1;
        final PieView pieView = (PieView)findViewById(R.id.pie_view);
        final ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();
        if(type.equals("Gain")){
            typeofdiet=1.15;
        }
        else if(type.equals("Keep")){
            typeofdiet=1;
        }
        else if(type.equals("Burn")){
            typeofdiet= 0.85;
        }

        if(sex.equals("Male")){
            double bmr= Math.round(66.5+(13.7*weight)+(5*height)-(6.8*age));

            String bmrString= Double.toString(bmr);

            double ratioValue= Double.valueOf(ratio);
            double typeValue= Double.valueOf(typeofdiet);
            double caloriesNeeded= Math.round(bmr*ratioValue*typeValue);
            String caloriesNeededString= Double.toString(caloriesNeeded);
            String calS= caloriesNeededString.substring(0,caloriesNeededString.length()-2);
            Integer caloriesNeededINT= Integer.parseInt(calS);

            Integer caloriesAtePercentage= totalCalories*100/caloriesNeededINT;
            Integer restofCalories= 100-caloriesAtePercentage;
            //Toast.makeText(this, caloriesAtePercentage.toString(), Toast.LENGTH_SHORT).show();
            pieHelperArrayList.add(new PieHelper(caloriesAtePercentage,Color.rgb(34,0,121)));
            pieHelperArrayList.add(new PieHelper(restofCalories,Color.rgb(245,238,220)));

            pieView.setDate(pieHelperArrayList);
            pieView.showPercentLabel(true);
            if(caloriesNeededINT<totalCalories){

                Integer val= totalCalories-caloriesNeededINT;

            }
            else {
                Integer val= caloriesNeededINT-totalCalories;

            }
        }
        if(sex.equals("Female")){
            double bmr= Math.round(665+(9.6*weight)+(1.85*height)-(4.7*age));

            String bmrString= Double.toString(bmr);

            double ratioValue= Double.valueOf(ratio);
            double typeValue= Double.valueOf(typeofdiet);
            double caloriesNeeded= Math.round(bmr*ratioValue*typeValue);
            String caloriesNeededString= Double.toString(caloriesNeeded);
            String calS= caloriesNeededString.substring(0,caloriesNeededString.length()-2);
            Integer caloriesNeededINT= Integer.parseInt(calS);

            Integer caloriesAtePercentage= totalCalories*100/caloriesNeededINT;
            Integer restofCalories= 100-caloriesAtePercentage;
            //Toast.makeText(this, caloriesAtePercentage.toString(), Toast.LENGTH_SHORT).show();
            pieHelperArrayList.add(new PieHelper(caloriesAtePercentage,Color.rgb(34,0,121)));
            pieHelperArrayList.add(new PieHelper(restofCalories,Color.rgb(245,238,220)));

            pieView.setDate(pieHelperArrayList);
            pieView.showPercentLabel(true);
            if(caloriesNeededINT<totalCalories){

                Integer val= totalCalories-caloriesNeededINT;

            }
            else {
                Integer val= caloriesNeededINT-totalCalories;

            }
        }

    }
    public void toMore(View view){
        Intent intent= new Intent(this,UserSettings.class);
        startActivity(intent);
    }

    public void toOverview(View view){
        Intent intent= new Intent(this,Overview.class);
        startActivity(intent);
    }
    public void toRecord(View view){
        Intent intent= new Intent(this,ChooseRecord.class);
        startActivity(intent);
    }
    @Override
    public void onDateSelected(DateTime dateSelected) {
        String data= dateSelected.toString().split("T")[0];
        //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        readFromTheDatabase(data);
    }
}
