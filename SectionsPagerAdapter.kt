package com.dicoding.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var appName: String = ""

    override fun createFragment(position: Int): Fragment {
        var fragment = FollowerFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerFragment.ARG_SECTION_NUMBER, position + 1)
            putString(FollowerFragment.ARG_USERNAME, appName)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }

}