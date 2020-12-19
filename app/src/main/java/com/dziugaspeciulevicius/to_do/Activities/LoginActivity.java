package com.dziugaspeciulevicius.to_do.Activities;

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

import com.dziugaspeciulevicius.to_do.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private TextView loginRedirect;

    private FirebaseAuth mAuth;
    private ProgressDialog loader;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    // check if user is already signed in
    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide top action bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // get the google sing in button
        findViewById(R.id.google_signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        // ------------------------ NON-GOOGLE LOGIN ------------------------
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

        // NON-GOOGLE LOGIN action call
        loginButton.setOnClickListener(new View.OnClickListener() {
            // when login is clicked run onClick listener
            @Override
            public void onClick(View view) {
                signInWithEmail();
            }
        });
    }

    // sign in function for google to create a request
    private void signInWithGoogle() {
        // clearing previous signin caches
        mGoogleSignInClient.signOut();

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // once user clicks sign in button and it triggers signIn() it should return result
    // this will take req code, it will have the result and data (details of user)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            // Google Sign In was successful, google will return an account
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInWithEmail() {
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
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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

}