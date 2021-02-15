package com.marville001.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    TextView tv_name,tv_email;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv_email = findViewById(R.id.tv_email);
        tv_name = findViewById(R.id.tv_name);
        btn_logout = findViewById(R.id.btn_logout);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        tv_email.setText(user.getEmail());

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }
}