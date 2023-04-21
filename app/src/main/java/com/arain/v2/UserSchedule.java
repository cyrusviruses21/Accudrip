package com.arain.v2;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserSchedule extends AppCompatActivity {
    //declare variables

    private Switch switchPump;
    private Button setTime;
    private EditText editTextDuration;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private int selectedTimeInMilliseconds;
    private int selectedDurationInMinutes;
    private boolean isConditionOn;

    private String selectedTime, selectedDuration;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_schedule);

        // Initialize the views
        switchPump = findViewById(R.id.switchPump);
        setTime = findViewById(R.id.setTime);
        editTextDuration = findViewById(R.id.editTextDuration);

        // Initialize the Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("schedule");

        // Set an OnCheckedChangeListener for the switch
        switchPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isConditionOn = isChecked;
                saveUserSelections();
                if (isChecked) {
                    switchPump.setText("ON"); // Set label to "On" when checked
                    //musend sa currentstatus with date, time, duration.

                } else {
                    switchPump.setText("OFF"); // Set label to "Off" when unchecked
                }

                // Update the Firebase Realtime Database with the new pump status
                databaseReference.child("setSchedule").setValue(isChecked ? 1 : 0);
            }
        });

        // Set an OnClickListener for the time button
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        // Load the user's selections from the database
        loadUserSelections();
    }

    // Show a TimePickerDialog to allow the user to select a time
    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Format the selected time as a string and set it in the UI
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                selectedTime = sdf.format(calendar.getTime());
                setTime.setText(selectedTime);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }


    // Load the user's selections from the database
    private void loadUserSelections() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Load the user's selections from the database
                    String selectedTimeString = dataSnapshot.child("time").getValue(String.class);
                    selectedDurationInMinutes = dataSnapshot.child("duration").getValue(Integer.class);
                    isConditionOn = dataSnapshot.child("condition").getValue(Boolean.class);

                    // Convert the selected time string to milliseconds
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    Date date;
                    try {
                        date = simpleDateFormat.parse(selectedTimeString);
                        selectedTimeInMilliseconds = (int) date.getTime();
                    } catch (ParseException e) {
                        // Handle the error
                    }

                    // Update the UI with the loaded selections
                    switchPump.setChecked(isConditionOn);
                    editTextDuration.setText(String.valueOf(selectedDurationInMinutes));
                    setTime.setText(selectedTimeString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

    // Save the user's selections to the database
    private void saveUserSelections() {
        // Convert the selected time in milliseconds to a string in the format "HH:mm"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date(selectedTimeInMilliseconds);
        String selectedTimeString = simpleDateFormat.format(date);

        // Save the user's selections to the database
        databaseReference.child("time").setValue(selectedTimeString);
        databaseReference.child("duration").setValue(selectedDurationInMinutes);
        databaseReference.child("condition").setValue(isConditionOn);
    }
}


