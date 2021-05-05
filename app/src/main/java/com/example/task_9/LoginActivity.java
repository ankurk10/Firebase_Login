package com.example.task_9;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthstatelistner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText login_email = findViewById(R.id.signup_email);
        EditText login_password = findViewById(R.id.signup_password);
        Button login_button = findViewById(R.id.login_button);
        TextView dont_have_account = findViewById(R.id.dont_have_account);
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthstatelistner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseuser = mFirebaseAuth.getCurrentUser();

                if(mFirebaseAuth != null)
                {
                    Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }

                else
                {
                    Toast.makeText(LoginActivity.this, "You are not registered. Please Sign Up!!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if(email.isEmpty())
                {
                    login_email.setError("Enter a valid Email");
                    login_email.requestFocus();
                }

                else if(password.isEmpty())
                {
                    login_password.setError("Enter a valid password");
                    login_password.requestFocus();
                }

                else if(email.isEmpty() && password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please fill your details", Toast.LENGTH_SHORT).show();
                }

                else if(!(email.isEmpty()&& password.isEmpty()))
                {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            }

                            else
                            {
                                Toast.makeText(LoginActivity.this, "Login Error. Try Again", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                }

                else
                {
                    Toast.makeText(LoginActivity.this, "An Error Occurred!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }
}