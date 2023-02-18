package com.arain.v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class UserSampleProfile extends AppCompatActivity
{

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private Button  back, logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sample_profile);

        back = (Button) findViewById(R.id.back);
        logout = (Button) findViewById(R.id.logout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSampleProfile.this, UserStatus.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(this, MainActivity.class);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView nameDisplayTextView = (TextView) findViewById(R.id.nameDisplay);
        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView emailTextView = (TextView) findViewById(R.id.email);
        final TextView phoneNumberTextView = (TextView) findViewById(R.id.phoneNumber);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String phoneNumber = userProfile.phoneNumber;

                    nameDisplayTextView.setText(fullName);
                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    phoneNumberTextView.setText(phoneNumber);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserSampleProfile.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void finishActivity(View.OnClickListener onClickListener, Class<MainActivity> mainActivityClass) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
    }
}