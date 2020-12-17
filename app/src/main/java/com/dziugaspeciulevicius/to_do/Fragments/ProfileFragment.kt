package com.dziugaspeciulevicius.to_do.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dziugaspeciulevicius.to_do.Activities.LoginActivity
import com.dziugaspeciulevicius.to_do.R
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val logout: Button? = activity?.findViewById(R.id.logout)
        val name: TextView? = activity?.findViewById(R.id.name)
        val mail: TextView? = activity?.findViewById(R.id.mail)

        logout?.setOnClickListener {
            Toast.makeText(context, "Logging out", Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            // Inflate the layout for this fragment
            val i = Intent(activity, LoginActivity::class.java)
            startActivity(i)
            (activity as Activity?)!!.overridePendingTransition(0, 0)

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}