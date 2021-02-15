package com.marville001.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    Button btn_login,btn_register;
    EditText et_email, et_password;
    ProgressBar progressbar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        progressbar_login = findViewById(R.id.progressbar_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate
                String email= et_email.getText().toString().trim();
                String password= et_password.getText().toString().trim();

                if(email.isEmpty()){
                    et_email.setError("Email is required");
                    et_email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    et_email.setError("Please enter a valid email");
                    et_email.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    et_password.setError("Password is required");
                    et_password.requestFocus();
                    return;
                }

                if(password.length()<6){
                    et_password.setError("Minimum password length is 6 characters");
                    et_password.requestFocus();
                    return;
                }
                progressbar_login.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar_login.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this, Profile.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}