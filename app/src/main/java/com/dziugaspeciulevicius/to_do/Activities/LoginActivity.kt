package com.dziugaspeciulevicius.to_do.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dziugaspeciulevicius.to_do.R
import com.dziugaspeciulevicius.to_do.ViewModels.TodoViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

class LoginActivity() : AppCompatActivity() {

    lateinit var todoViewModel: TodoViewModel

    private var loginEmail: EditText? = null
    private var loginPassword: EditText? = null
    private var loginButton: Button? = null
    private var loginRedirect: TextView? = null
    private var mAuth: FirebaseAuth? = null
    private var loader: ProgressDialog? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null

    // check if user is already signed in
    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // hide top action bar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // get the google sing in button
        findViewById<View>(R.id.google_signIn).setOnClickListener { signInWithGoogle() }

        // ------------------------ NON-GOOGLE LOGIN ------------------------
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        loader = ProgressDialog(this)
        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton)
        loginRedirect = findViewById(R.id.loginPageRegister)

        // linking login to register
        loginRedirect?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        })

        // NON-GOOGLE LOGIN action call
        loginButton?.setOnClickListener(
            View.OnClickListener
            // when login is clicked run onClick listener
            { signInWithEmail() })
    }

    // sign in function for google to create a request
    private fun signInWithGoogle() {
        // clearing previous signin caches
        mGoogleSignInClient!!.signOut()
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // once user clicks sign in button and it triggers signIn() it should return result
    // this will take req code, it will have the result and data (details of user)
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            // Google Sign In was successful, google will return an account
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!.idToken)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this,
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth!!.currentUser
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@LoginActivity,
                            "Authentication failed..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    private fun signInWithEmail() {
        // get email and password
        val email = loginEmail!!.text.toString().trim { it <= ' ' }
        val password = loginPassword!!.text.toString().trim { it <= ' ' }

        // we check for email and password
        if (TextUtils.isEmpty(email)) {
            loginEmail!!.error = "Email is required"
            return
        }
        if (TextUtils.isEmpty(password)) {
            loginPassword!!.error = "Password is required"
            return
            // if it's not empty perform login functionality
        } else {
            loader!!.setMessage("Login in progress")
            loader!!.setCanceledOnTouchOutside(false)
            loader!!.show()
            // login user
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
                    override fun onComplete(task: Task<AuthResult?>) {
                        // if login successful
                        if (task.isSuccessful) {
                            // create intent
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            // start and finish activity
                            startActivity(intent)
                            finish()
                            loader!!.dismiss()
                            // if login unsuccessful
                        } else {
                            // get error
                            val error = task.exception.toString()
                            // show error
                            Toast.makeText(
                                this@LoginActivity,
                                "Login failed $error",
                                Toast.LENGTH_SHORT
                            ).show()
                            loader!!.dismiss()
                        }
                    }
                })
        }
    }

    companion object {
        private val RC_SIGN_IN = 123
    }
}