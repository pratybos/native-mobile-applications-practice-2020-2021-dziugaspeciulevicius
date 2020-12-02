package com.dziugaspeciulevicius.nativeappstasks.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dziugaspeciulevicius.nativeappstasks.R
import com.dziugaspeciulevicius.nativeappstasks.onboarding.screens.Screen1Fragment
import com.dziugaspeciulevicius.nativeappstasks.onboarding.screens.Screen2Fragment
import com.dziugaspeciulevicius.nativeappstasks.onboarding.screens.Screen3Fragment
import kotlinx.android.synthetic.main.fragment_view_pager.view.*


class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            Screen1Fragment(),
            Screen2Fragment(),
            Screen3Fragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        view.viewPagerOnboarding.adapter = adapter
    }
}