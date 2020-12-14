package com.dziugaspeciulevicius.to_do;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {

//    private Toolbar toolbar;
    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private TextView loginRedirect;

    private FirebaseAuth mAuth; // declare an instance of FirebaseAuth
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

//        toolbar = findViewById(R.id.loginToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Login");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        loginRedirect = findViewById(R.id.loginPageRegister);

        // linking login to register
        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        // login action
        loginButton.setOnClickListener(new View.OnClickListener() {
            // when login is clicked run onClick listener
            @Override
            public void onClick(View view) {
                // get email and password
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                // we check for email and password
                if(TextUtils.isEmpty(email)) {
                    loginEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    loginPassword.setError("Password is required");
                    return;
                // if it's not empty perform login functionality
                } else {
                    loader.setMessage("Login in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    // login user
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // if login successful
                            if (task.isSuccessful()) {
                                // create intent
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                // start and finish activity
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            // if login unsuccessful
                            } else {
                                // get error
                                String error = task.getException().toString();
                                // show error
                                Toast.makeText(LoginActivity.this, "Login failed " + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}