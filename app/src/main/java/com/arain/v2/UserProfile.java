package com.arain.v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity implements View.OnClickListener{

    private TextView title;

    private Button report, status, data, profile;
    private ImageButton calendarIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        title = (TextView) findViewById(R.id.title);
        title.setOnClickListener(this);

        report = (Button) findViewById(R.id.report);
        report.setOnClickListener(this);

        status = (Button) findViewById(R.id.status);
        status.setOnClickListener(this);

        data = (Button) findViewById(R.id.data);
        data.setOnClickListener(this);

        profile = (Button) findViewById(R.id.profile);
        profile.setOnClickListener(this);

        calendarIcon = (ImageButton) findViewById(R.id.calendarIcon);
        calendarIcon.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.title:
                    startActivity(new Intent(this, UserProfile.class));
                    break;

                case R.id.report:
                    startActivity(new Intent(this, UserProfile.class));
                    break;

                case R.id.status:
                    startActivity(new Intent(this, UserStatus.class));
                    break;

                case R.id.data:
                    startActivity(new Intent(this, UserData.class));
                    break;

                case R.id.profile:
                    startActivity(new Intent(this, UserSampleProfile.class));
                    break;

                case R.id.calendarIcon:
                    startActivity(new Intent(this, UserSchedule.class));
                    break;

            }
    }
}