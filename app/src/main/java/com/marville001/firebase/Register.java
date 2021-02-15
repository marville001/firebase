package com.marville001.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth auth;

    Button btn_register, btn_cancel;
    EditText et_name, et_age, et_email,et_password,et_confirmpassword;
    ProgressBar progressbar_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        btn_register = findViewById(R.id.btn_register);
        btn_cancel = findViewById(R.id.btn_cancel);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirmpassword = findViewById(R.id.et_confirmpassword);
        progressbar_register = findViewById(R.id.progressbar_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = et_name.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String confirmpassword = et_confirmpassword.getText().toString().trim();

                //validate values
                if (fullname.isEmpty()) {
                    et_name.setError("Full Name is required");
                    et_name.requestFocus();
                    return;
                }

                if (age.isEmpty()) {
                    et_age.setError("Age is required");
                    et_age.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    et_email.setError("Email is required");
                    et_email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    et_email.setError("Please provide a valid email");
                    et_email.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    et_password.setError("Password is required");
                    et_password.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    et_password.setError("The password length must be 6 characters long");
                    et_password.requestFocus();
                    return;
                }

                if (confirmpassword.isEmpty()) {
                    et_password.setError("Confirm password");
                    et_password.requestFocus();
                    return;
                }

                if(!confirmpassword.equals(password)){
                    et_confirmpassword.setError("Password do not match");
                    et_confirmpassword.requestFocus();
                    return;
                }
                progressbar_register.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user=new User(fullname, age,email);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressbar_register.setVisibility(View.GONE);
                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this, "User has been registered successfully",Toast.LENGTH_LONG).show();
                                                et_email.setText("");
                                                et_password.setText("");
                                                et_name.setText("");
                                                et_age.setText("");
                                                et_confirmpassword.setText("");
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }else{
                                                Toast.makeText(Register.this, "User has not registered successfully. Try again!",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(Register.this, "Failed to register the user",Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}