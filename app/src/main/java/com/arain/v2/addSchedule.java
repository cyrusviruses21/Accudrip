package com.arain.v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class addSchedule extends AppCompatActivity implements View.OnClickListener
{

    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                startActivity(new Intent(this, UserSchedule.class));
                break;
        }
    }
}