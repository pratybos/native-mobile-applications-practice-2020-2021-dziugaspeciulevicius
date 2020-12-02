package com.dziugaspeciulevicius.nativeappstasks.onboarding.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.dziugaspeciulevicius.nativeappstasks.R
import kotlinx.android.synthetic.main.fragment_screen1.view.*

class Screen1Fragment : Fragment(R.layout.fragment_screen1) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPagerOnboarding)
        view.screen1_btn.setOnClickListener{
            viewPager?.currentItem = 1
        }
    }

}