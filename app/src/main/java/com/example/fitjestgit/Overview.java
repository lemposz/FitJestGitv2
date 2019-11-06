package com.example.fitjestgit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import im.dacer.androidcharts.BarView;
import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;

public class Overview extends AppCompatActivity {
    Date currentDay= Calendar.getInstance().getTime();
    String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(currentDay);
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private TextView textViewCaloriesAte;
    private TextView textViewBMR;
    private TextView textViewComunicate;
    private TextView textViewComunicatevalue;
    private TextView textViewCaloriesCap;
    private ImageView imageViewRecord;
    private ImageView imageViewHistory;
    private ImageView imageViewMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        textViewCaloriesAte= findViewById(R.id.textViewCaloriesAte);
        textViewBMR= findViewById(R.id.textViewCaloriesBMR);
        textViewCaloriesCap= findViewById(R.id.textViewCaloriesCap);
        textViewComunicate= findViewById(R.id.textViewComunicate);
        textViewComunicatevalue= findViewById(R.id.textViewComunicatevalue);
        imageViewRecord= findViewById(R.id.imageButtonRecord);
        imageViewHistory= findViewById(R.id.imageButtonHistory);
        imageViewMore= findViewById(R.id.imageButtonMore);
        final PieView pieView = (PieView)findViewById(R.id.pie_view2);
        final ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();


        db.collection(modifiedDate)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Integer totalCalories=0;
                        if(queryDocumentSnapshots.isEmpty()){
                            pieHelperArrayList.add(new PieHelper(100,Color.rgb(245,238,220)));
                            pieView.setDate(pieHelperArrayList);
                            pieView.showPercentLabel(true);
                        }
                        else{

                        }
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Food food= documentSnapshot.toObject(Food.class);
                            totalCalories+= food.getCalories();
                        }
                        Totalcounter(totalCalories);
                        getAllData(totalCalories);



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
        final PieView pieView = (PieView)findViewById(R.id.pie_view2);
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
            String bmrStr= bmrString.substring(0,bmrString.length()-2);
            textViewBMR.setText(bmrStr);

           double ratioValue= Double.valueOf(ratio);
            double typeValue= Double.valueOf(typeofdiet);
            double caloriesNeeded= Math.round(bmr*ratioValue*typeValue);
            String caloriesNeededString= Double.toString(caloriesNeeded);
            String calS= caloriesNeededString.substring(0,caloriesNeededString.length()-2);
            textViewCaloriesCap.setText(calS);

            Integer caloriesNeededINT= Integer.parseInt(calS);

            Integer caloriesAtePercentage= totalCalories*100/caloriesNeededINT;
            Integer restofCalories= 100-caloriesAtePercentage;
            //Toast.makeText(this, caloriesAtePercentage, Toast.LENGTH_SHORT).show();
            pieHelperArrayList.add(new PieHelper(caloriesAtePercentage,Color.rgb(34,0,121)));
            pieHelperArrayList.add(new PieHelper(restofCalories,Color.rgb(245,238,220)));
            pieView.setDate(pieHelperArrayList);
            pieView.showPercentLabel(true);
            if(caloriesNeededINT<totalCalories){
                textViewComunicate.setText("Nadmiar kalorii na dziś: ");
                Integer val= totalCalories-caloriesNeededINT;
                textViewComunicatevalue.setText(val.toString());
            }
            else {
                textViewComunicate.setText("Ilość kalorii do przyjęcia:");
                Integer val= caloriesNeededINT-totalCalories;
                textViewComunicatevalue.setText(val.toString());
            }
        }

    }
    public void Totalcounter(Integer totalCalories){
        textViewCaloriesAte.setText(totalCalories.toString());
    }

    public void toRecord(View view){
        Intent intent= new Intent(this,RecordActivity.class);
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

