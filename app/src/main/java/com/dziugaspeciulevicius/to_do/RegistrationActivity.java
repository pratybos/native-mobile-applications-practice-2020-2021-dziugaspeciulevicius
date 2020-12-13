package com.dziugaspeciulevicius.to_do;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    //    private Toolbar toolbar;
    private EditText registrationEmail, registrationPassword;
    private Button registrationButton;
    private TextView registrationRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        //        toolbar = findViewById(R.id.loginToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Register");

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
    }
}