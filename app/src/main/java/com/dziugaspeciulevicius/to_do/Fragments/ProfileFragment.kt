package com.dziugaspeciulevicius.to_do.Fragments

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dziugaspeciulevicius.to_do.Activities.LoginActivity
import com.dziugaspeciulevicius.to_do.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.String


class ProfileFragment : Fragment() {


//    val nameInput: EditText? = activity?.findViewById(R.id.name_profile) as EditText
//    val emailInput: EditText? = activity?.findViewById(R.id.email_profile) as EditText
//    val passwordInput: EditText? = activity?.findViewById(R.id.password_profile) as EditText
private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val logout: Button? = activity?.findViewById(R.id.logout)
        val name: TextView? = activity?.findViewById(R.id.name)
        val mail: TextView? = activity?.findViewById(R.id.mail)
        val profileImage: ImageView? = activity?.findViewById(R.id.imageView)
        val mediaPlayer = MediaPlayer.create(context, R.raw.logout_sound)

        //  get users signed in activity info (google)
        val acct = FirebaseAuth.getInstance().currentUser
//        val acct = GoogleSignIn.getSignedInAccountFromIntent(activity)

        // check and show user info (google)
        if (acct != null) {

            Log.v("USER ACC USER ACC", acct.toString());

            val personName = acct.displayName
            val personEmail = acct.email
            val image: Uri? = acct.photoUrl
//            val personId = acct.id

            name!!.text = personName
            mail!!.text = personEmail

            // glide is for image view to be displayed
            Glide.with(this).load(String.valueOf(image)).into(imageView)
        }

        // get user info and display logged in user from a db
        // show all data
//        showAllUserData()

        // log out button click action
        logout?.setOnClickListener {
            Toast.makeText(context, "Logging out", Toast.LENGTH_LONG).show();

            FirebaseAuth.getInstance().signOut();
//            mGoogleSignInClient?.signOut()

            // Inflate the layout for this fragment
            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
            (activity as Activity?)!!.overridePendingTransition(0, 0)

            // play windows log out
            mediaPlayer.start();

        }
    }

//    private fun showAllUserData() {
//        val intent = getIntent()
//        val user_name = intent.getStringExtra("name")
//        val user_email = intent.getStringExtra("email")
//        val user_password = intent.getStringExtra("password")
//
//
//        nameInput.getEditText().setText(user_name)
//        emailInput.getEditText().setText(user_email)
//        passwordInput.getEditText().setText(user_password)
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}