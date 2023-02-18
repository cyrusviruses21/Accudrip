package com.arain.v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserStatus extends AppCompatActivity implements View.OnClickListener{

    TextView textView, title;
    Switch aSwitch;

    private Button report, data, profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);

        title = (TextView) findViewById(R.id.title);
        title.setOnClickListener(this);

        report = (Button) findViewById(R.id.report);
        data = (Button) findViewById(R.id.data);
        profile = (Button) findViewById(R.id.profile);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserStatus.this, UserProfile.class));
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserStatus.this, UserData.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserStatus.this, UserSampleProfile.class));
            }
        });

        textView = findViewById(R.id.text);
        aSwitch = findViewById(R.id.switch1);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (aSwitch.isChecked()){
                    Toast.makeText(UserStatus.this,"Switch is ON", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(UserStatus.this, "Switch is OFF", Toast.LENGTH_LONG).show();
                }
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