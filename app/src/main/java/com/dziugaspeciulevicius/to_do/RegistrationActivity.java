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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class RegistrationActivity extends AppCompatActivity {

    //    private Toolbar toolbar;
    private EditText registrationEmail, registrationPassword;
    private Button registrationButton;
    private TextView registrationRedirect;
    private FirebaseAuth mAuth; // according to firebase we need to declare FirebaseAuth instance
    private ProgressDialog loader;  // loader to inform user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide top action bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        //        toolbar = findViewById(R.id.loginToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Register");

        // then we initialize FirebaseAuth instance in the onCreate() method
        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);  //

        registrationEmail = findViewById(R.id.RegistrationEmail);
        registrationPassword = findViewById(R.id.RegistrationPassword);
        registrationButton = findViewById(R.id.RegistrationButton);
        registrationRedirect = findViewById(R.id.RegistrationPageLogin);

        // linking login to register
        registrationRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // registration action
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // we need to get email and password
                String email = registrationEmail.getText().toString().trim();
                String password = registrationPassword.getText().toString().trim();

                // we then check for email and password
                if (TextUtils.isEmpty(email)) {
                   registrationEmail.setError("Email is required");
                   return;
                }

                if (TextUtils.isEmpty(password)) {
                    registrationPassword.setError("Password is required");
                    return;
                 // if it's not empty we start implementing registration functionality
                } else {
                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    // creates user
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // if registration is successful
                            if (task.isSuccessful()) {
                                // create intent and go to homeActivity
                                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                // start activity and later finish it
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            // if registration unsuccessful
                            } else {
                                // get error
                                String error = task.getException().toString();
                                // show error
                                Toast.makeText(RegistrationActivity.this, "Registration failed " + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}