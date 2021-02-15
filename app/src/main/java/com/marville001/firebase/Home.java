package com.marville001.firebase;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = fAuth.getCurrentUser();

        if(fUser != null){
            startActivity(new Intent(Home.this, Profile.class));
        }
    }
}
