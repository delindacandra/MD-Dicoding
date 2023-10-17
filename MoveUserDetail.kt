package com.dicoding.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.response.DetailResponse
import com.dicoding.githubuserapp.databinding.ActivityMoveUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MoveUserDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMoveUserDetailBinding
    private lateinit var detailViewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(data)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.appName = name.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        if(name != null ) {
            detailViewModel.fetchUserData(name.toString())
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        detailViewModel.userData.observe(this){userData->
            setDetailUser(userData)
        }

    }
    private fun setDetailUser(userData: DetailResponse){
        binding.nameUser.text = userData.login
        binding.username.text = userData.name
        binding.followers.text = "Followers: ${userData.followers}"
        binding.following.text = "Following: ${userData.following}"
        Glide.with(this)
            .load(userData.avatarUrl)
            .into(binding.imgUser)
    }
    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val data = "username"
    }

}

