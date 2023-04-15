package com.arain.v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class manual_irrigation extends AppCompatActivity implements View.OnClickListener {

    //variables
    private DatabaseReference databaseReference;
    Switch switchPump;
    Button home, sampol;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_irrigation);

        back = (ImageButton) findViewById(R.id.back);
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);
        sampol = (Button) findViewById(R.id.sampol);

        sampol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manual_irrigation.this, for_examples.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manual_irrigation.this, UserStatus.class));
            }
        });

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Get reference to the Switch widget
        switchPump = findViewById(R.id.switchPump);


        // Set a listener for the switch state change
        switchPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchPump.setText("ON"); // Set label to "On" when checked
                } else {
                    switchPump.setText("OFF"); // Set label to "Off" when unchecked
                }

                // Update the Firebase Realtime Database with the new pump status
                databaseReference.child("pumpStatus").setValue(isChecked ? 1 : 0);
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                startActivity(new Intent(this, UserStatus.class));
                break;

        }
    }
}