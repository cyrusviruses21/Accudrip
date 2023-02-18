package com.arain.v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSchedule extends AppCompatActivity implements View.OnClickListener
{

    private Button home, addSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_schedule);

        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);

        addSchedule = (Button) findViewById(R.id.addSchedule);
        addSchedule.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                startActivity(new Intent(this, UserProfile.class));
                break;

            case R.id.addSchedule:
                startActivity(new Intent(this, addSchedule.class));
                break;
        }
    }
}