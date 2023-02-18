package com.arain.v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserData extends AppCompatActivity implements View.OnClickListener{

    private Button report, status, profile, chart;
    private TextView title;
    BarChart barChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        title = (TextView) findViewById(R.id.title);
        title.setOnClickListener(this);

        report = (Button) findViewById(R.id.report);
        status = (Button) findViewById(R.id.status);
        profile = (Button) findViewById(R.id.profile);
        chart = (Button) findViewById(R.id.chart);

        //Assign variable
        barChart = findViewById(R.id.barChart);

        //Initialize array list
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        //user forloop
        for(int i=1; i<10; i++) {
            //Convert to float
            float value = (float) (i * 10.0);
            //Initialize bar chart entry
            BarEntry barEntry = new BarEntry(i, value);
            //Add values in array list
            barEntries.add(barEntry);
        }
        //Initialize bar data set
        BarDataSet barDataSet = new BarDataSet(barEntries, "Water Consumption per month");
        //Set Colors
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //Hide draw value
        barDataSet.setDrawValues(false);
        //set bar data
        barChart.setData(new BarData(barDataSet));
        //set animation
        barChart.animateY(5000);
        //set description text and color
        barChart.getDescription().setText("Water Consumption");
        barChart.getDescription().setTextColor(Color.WHITE);


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserData.this, UserProfile.class));
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserData.this, UserStatus.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserData.this, UserSampleProfile.class));
            }
        });

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserData.this, chart.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title:
                startActivity(new Intent(this, UserProfile.class));
                break;
        }
    }
}