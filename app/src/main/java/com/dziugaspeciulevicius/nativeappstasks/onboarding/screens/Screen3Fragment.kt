package com.dziugaspeciulevicius.nativeappstasks.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.dziugaspeciulevicius.nativeappstasks.R
import kotlinx.android.synthetic.main.fragment_screen1.view.*
import kotlinx.android.synthetic.main.fragment_screen3.view.*

class Screen3Fragment : Fragment(R.layout.fragment_screen3) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.screen3_btn.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            onBoardingFinish()
        }
    }
    private fun onBoardingFinish(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}