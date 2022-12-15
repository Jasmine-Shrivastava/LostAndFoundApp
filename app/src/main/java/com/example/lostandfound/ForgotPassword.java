package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private FirebaseAuth auth;
    //private FirebaseUser user = auth.getCurrentUser();
    private EditText resetEmail;
    private Button resetButton;
    private String email;
    private TextView forloginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();

        resetEmail= findViewById(R.id.forgetp_email);
        resetButton=findViewById(R.id.reset_button);

        forloginRedirectText= findViewById(R.id.forloginRedirectText);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }

            private void validateData() {
                email = resetEmail.getText().toString();
                if(email.isEmpty()){
                    resetEmail.setError("Required");
                }else{
                    forgetPass();
                }
            }

            private void forgetPass() {
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPassword.this, "Check your Email",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(ForgotPassword.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        forloginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
            }
        });


    }
}