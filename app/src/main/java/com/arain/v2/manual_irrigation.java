package com.arain.v2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class manual_irrigation extends AppCompatActivity implements View.OnClickListener {

    //variables
    private DatabaseReference databaseReference;
    private Switch switchPump;
    private Button home,sched;
    private ImageButton back;
    private EditText editTextDuration;
    private Button buttonStart;
    private CountDownTimer countDownTimer;
    private long durationSeconds; // Duration in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_irrigation);

        switchPump = findViewById(R.id.switchPump);
        editTextDuration = findViewById(R.id.editTextDuration);
        buttonStart = findViewById(R.id.buttonStart);

        sched = (Button) findViewById(R.id.sched);

        back = (ImageButton) findViewById(R.id.back);
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String durationString = editTextDuration.getText().toString();
                if (!TextUtils.isEmpty(durationString)) {
                    // Get the duration in seconds from user input
                    int duration = Integer.parseInt(durationString);
                    durationSeconds = duration; // Assign duration to durationSeconds
                    // Convert to milliseconds
                    durationSeconds *= 1000;
                    // Turn on the switch
                    switchPump.setChecked(true);
                    // Start the timer
                    startTimer();
                } else {
                    // Show an error message if duration is not provided
                    Toast.makeText(manual_irrigation.this, "Enter a duration", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    cancelTimer();
                }
            }
        });

        sched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manual_irrigation.this, holy.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manual_irrigation.this, UserStatus.class));
            }
        });

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Get reference to the Switch widget
        switchPump = findViewById(R.id.switchPump);


        // Set a listener for the switch state change
        switchPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchPump.setText("ON"); // Set label to "On" when checked
                    //musend sa currentstatus with date, time, duration.

                } else {
                    switchPump.setText("OFF"); // Set label to "Off" when unchecked
                }

                // Update the Firebase Realtime Database with the new pump status
                databaseReference.child("pumpStatus").setValue(isChecked ? 1 : 0);
            }
        });



    }

    private void startTimer() {
        cancelTimer(); // Cancel any existing timer before starting a new one
        countDownTimer = new CountDownTimer(durationSeconds, durationSeconds) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Do nothing during the timer tick
            }
            @Override
            public void onFinish() {
                // Turn off the switch when the timer finishes
                switchPump.setChecked(false);
            }
        };
        countDownTimer.start();
    }
    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
    // Call this method when the switch is manually turned off by the user
    private void manuallyTurnOffSwitch() {
        switchPump.setChecked(false);
        cancelTimer(); // Cancel the timer if it's running
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