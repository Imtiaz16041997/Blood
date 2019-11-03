package com.example.blood;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
   EditText txt_email,txt_password;
   Button btn_login;
   private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        //getSupportActionBar().setTitle("LoginActivity");
        txt_email =(EditText)findViewById(R.id.txt_email);
        txt_password =(EditText)findViewById(R.id.txt_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_email.getText().toString().trim();
                String password = txt_password.getText().toString().trim();

                if(email.isEmpty()){
                    txt_email.setError("Email is Required");
                    txt_email.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    txt_password.setError("Password is Required");
                    txt_password.requestFocus();
                    return;
                }
                if(password.length()<6){

                    txt_password.setError("Minimum length of password should be 6");
                    txt_password.requestFocus();
                    return;

                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                } else {
                                    Toast.makeText(LoginActivity.this,"Login Failed Or User not Available",Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

            }
        });
    }
    public void btn_signupForm(View view){

        startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
    }

}

