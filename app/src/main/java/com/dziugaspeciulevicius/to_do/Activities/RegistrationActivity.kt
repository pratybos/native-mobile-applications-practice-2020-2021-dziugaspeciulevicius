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
import com.dziugaspeciulevicius.to_do.Models.User
import com.dziugaspeciulevicius.to_do.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    private var registrationName: EditText? = null
    private var registrationEmail: EditText? = null
    private var registrationPassword: EditText? = null
    private var registrationButton: Button? = null
    private var registrationRedirect: TextView? = null
    private var mAuth // according to firebase we need to declare FirebaseAuth instance
            : FirebaseAuth? = null
    private var loader // loader to inform user
            : ProgressDialog? = null

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
        setContentView(R.layout.activity_registration)

        // then we initialize FirebaseAuth instance in the onCreate() method
        mAuth = FirebaseAuth.getInstance()
        loader = ProgressDialog(this) //
        registrationName = findViewById(R.id.RegistrationName)
        registrationEmail = findViewById(R.id.RegistrationEmail)
        registrationPassword = findViewById(R.id.RegistrationPassword)
        registrationButton = findViewById(R.id.RegistrationButton)
        registrationRedirect = findViewById(R.id.RegistrationPageLogin)

        // linking login to register
        registrationRedirect?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        // registration action
        registrationButton?.setOnClickListener(View.OnClickListener {
            // we need to get email and password
            val name = registrationName?.getText().toString().trim { it <= ' ' }
            val email = registrationEmail?.getText().toString().trim { it <= ' ' }
            val password = registrationPassword?.getText().toString().trim { it <= ' ' }

            // we then check for name, email and password
            if (TextUtils.isEmpty(name)) {
                registrationName?.setError("Nickname is required")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                registrationEmail?.setError("Email is required")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                registrationPassword?.setError("Password is required")
                return@OnClickListener
                // if it's not empty we start implementing registration functionality
            } else {
                loader!!.setMessage("Registration in progress")
                loader!!.setCanceledOnTouchOutside(false)
                loader!!.show()
                // creates user
                mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        // if registration is successful
                        if (task.isSuccessful) {
                            val user =
                                User(name, email, password)
                            FirebaseDatabase.getInstance().getReference("Users").child(
                                FirebaseAuth.getInstance().currentUser!!.uid
                            ).setValue(user)

                            // create intent and go to homeActivity
                            val intent = Intent(
                                this@RegistrationActivity,
                                MainActivity::class.java
                            )
                            // start activity and later finish it
                            startActivity(intent)
                            finish()
                            loader!!.dismiss()
                            // if registration unsuccessful
                        } else {
                            // get error
                            val error = task.exception.toString()
                            // show error
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Registration failed $error", Toast.LENGTH_SHORT
                            ).show()
                            loader!!.dismiss()
                        }
                    }
            }
        })
    }
}