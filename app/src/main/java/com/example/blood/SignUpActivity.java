package com.example.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txt_name,txt_email,txt_phone,txt_password,txt_confirm_password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        //getSupportActionBar().setTitle("SignUpActivity");

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_phone = (EditText) findViewById(R.id.txt_phone);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_confirm_password = (EditText) findViewById(R.id.txt_confirm_password);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.buttonRegister).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }
         private  void registerUser(){
                String name = txt_name.getText().toString().trim();
                String email = txt_email.getText().toString().trim();
                String phone = txt_phone.getText().toString().trim();
                String password = txt_password.getText().toString().trim();
                String confirmPassword = txt_confirm_password.getText().toString().trim();

             if(name.isEmpty()){
                 txt_name.setError("Name is Required");
                 txt_name.requestFocus();
                 return;
             }
                if(email.isEmpty()){
                    txt_email.setError("Email is Required");
                    txt_email.requestFocus();
                    return;
                }
             if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                 txt_email.setError("Please enter a valid email");
                 txt_email.requestFocus();
                 return;
             }
                if(phone.isEmpty()){
                    txt_phone.setError("Phone is Required");
                    txt_phone.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    txt_password.setError("Password is Required");
                    txt_password.requestFocus();
                    return;
                }
                if(confirmPassword.isEmpty()){
                    txt_confirm_password.setError("Re-Confirm Passoword is Required");
                    txt_confirm_password.requestFocus();
                    return;
                }

               if(password.length()<6){

                   txt_password.setError("Minimum length of password should be 6");
                   txt_password.requestFocus();
                   return;

               }

               mAuth.createUserWithEmailAndPassword(email, password)
                           .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                       startActivity(intent);
                                       //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                       Toast.makeText(SignUpActivity.this,"Registration Complete",Toast.LENGTH_SHORT).show();
                                       finish();

                                   } else {
                                       if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                           Toast.makeText(getApplication(),"You Are Already Registered",Toast.LENGTH_SHORT).show();
                                       }else {
                                           Toast.makeText(getApplication(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                       }
                                       Toast.makeText(SignUpActivity.this,"Please Change Your Email",Toast.LENGTH_SHORT).show();

                                   }

                               }
                           });

               }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonRegister:
                registerUser();
                break;
            case R.id.btn_login:
                startActivity(new Intent (this, LoginActivity.class));
                break;
        }

    }










    }

