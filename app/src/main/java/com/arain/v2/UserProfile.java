package com.arain.v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserProfile extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    //variables
    private TextView textView3;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TableLayout tableLayout;
    private FirebaseDatabase database;
    private DatabaseReference schedulesRef;
    private DatabaseReference completedSchedulesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        // Get a reference to the Firebase database
        database = FirebaseDatabase.getInstance();
        schedulesRef = database.getReference("schedules");
        completedSchedulesRef = database.getReference("completed_schedules");

        // Get a reference to the TableLayout
        tableLayout = findViewById(R.id.tableLayout);

        // Load the schedules from Firebase
        loadSchedules();

        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setOnClickListener(this);

        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //toolbar
        setSupportActionBar(toolbar);

        //Navigation Drawer Menu
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_profile).setVisible(true);
        menu.findItem(R.id.nav_logout).setVisible(true);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_report);

    }

    private void loadSchedules() {
        // Add a listener to retrieve the schedules from Firebase
        schedulesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the existing rows from the table
                tableLayout.removeAllViews();

                // Create a list to hold the schedules
                List<Schedule> schedules = new ArrayList<>();

                // Loop through the schedules and add them to the list
                for (DataSnapshot scheduleSnapshot : snapshot.getChildren()) {
                    // Get the schedule object from the snapshot
                    Schedule schedule = scheduleSnapshot.getValue(Schedule.class);
                    schedules.add(schedule);
                }

                // Sort the schedules by date in ascending order
                Collections.sort(schedules, new Comparator<Schedule>() {
                    @Override
                    public int compare(Schedule s1, Schedule s2) {
                        Date d1 = parseScheduleDate(s1.getDate(), s1.getTime());
                        Date d2 = parseScheduleDate(s2.getDate(), s2.getTime());
                        return d1.compareTo(d2);
                    }
                });

                // Loop through the sorted schedules and add them to the table
                for (Schedule schedule : schedules) {
                    // Parse the schedule date and time into a single date object
                    Date scheduleDate = parseScheduleDate(schedule.getDate(), schedule.getTime());

                    // Get the current date and time
                    Calendar now = Calendar.getInstance();
                    now.set(Calendar.SECOND, 0);
                    now.set(Calendar.MILLISECOND, 0);

                    // Check if the schedule date matches the current date
                    /*if (scheduleDate.compareTo(now.getTime()) == 0) {
                        // If the schedule date matches, turn on the water pump
                        turnOnWaterPump();

                        // Show a message
                        Toast.makeText(UserProfile.this, "Water pump turned on", Toast.LENGTH_SHORT).show();
                    }*/

                    // Create a new row for the schedule in the table
                    TableRow row = new TableRow(UserProfile.this);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(params);

                    // Create TextViews for the schedule date, time, and duration
                    TextView dateTextView = new TextView(UserProfile.this);
                    dateTextView.setText(schedule.getDate());
                    row.addView(dateTextView);

                    TextView timeTextView = new TextView(UserProfile.this);
                    timeTextView.setText(schedule.getTime());
                    row.addView(timeTextView);

                    TextView durationTextView = new TextView(UserProfile.this);
                    durationTextView.setText(schedule.getDuration());
                    row.addView(durationTextView);

                    // Add the row to the table
                    tableLayout.addView(row);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
                Toast.makeText(UserProfile.this, "Error retrieving schedules", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Date parseScheduleDate(String date, String time) {
        // Concatenate the date and time strings
        String dateString = date + " " + time;

        // Parse the date string into a Date object
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView3:
                startActivity(new Intent(this, UserStatus.class));
                break;


        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        switch (menuitem.getItemId()) {
            case R.id.nav_report:
                break;
            case R.id.nav_data:
                Intent intent = new Intent(this, UserData.class);
                startActivity(intent);
                break;
            case R.id.nav_status:
                Intent intent1 = new Intent(this, UserStatus.class);
                startActivity(intent1);
                break;
            case R.id.nav_profile:
                Intent intent2 = new Intent(this, UserSampleProfile.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                Intent intent3 = new Intent(this, MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_schedules:
                Intent intent4 = new Intent(this, UserSchedule.class);
                startActivity(intent4);
                break;
            case R.id.nav_schedule:
                Intent intent5 = new Intent(this, addSchedule.class);
                startActivity(intent5);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}