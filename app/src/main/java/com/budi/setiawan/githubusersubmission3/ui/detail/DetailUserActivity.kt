package com.budi.setiawan.githubusersubmission3.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.budi.setiawan.githubusersubmission3.R
import com.budi.setiawan.githubusersubmission3.data.adapter.SectionPagerAdapter
import com.budi.setiawan.githubusersubmission3.databinding.ActivityDetailUserBinding
import com.budi.setiawan.githubusersubmission3.ui.favorite.FavoriteActivity
import com.budi.setiawan.githubusersubmission3.ui.setting.SettingActivity
import com.budi.setiawan.githubusersubmission3.ui.viewmodel.DetailUserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this){
            if(it != null){
                binding.apply {
                    supportActionBar?.title = it.login
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers}"
                    tvFollowing.text = "${it.following}"
                    tvRepository.text = "${it.public_repos}"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(ivAvatar)
                }
            }
        }

        var isCheck = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                    if(count > 0){
                        binding.toggleFavorite.isChecked = true
                        isCheck = true
                    }else{
                        binding.toggleFavorite.isChecked = false
                        isCheck = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener{
            isCheck = ! isCheck
            if(isCheck){
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addToFavorite(id, username, avatarUrl)
                        Toast.makeText(this@DetailUserActivity, "Berhasil menambahkan $username ke favorite user", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                viewModel.removeUFromFavorite(id)
                Toast.makeText(this@DetailUserActivity, "Berhasil menghapus $username dari favorite user", Toast.LENGTH_SHORT).show()
            }
            binding.toggleFavorite.isChecked = isCheck
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabs, viewPager){tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting -> {
                Intent(this, SettingActivity::class.java).also{
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
        private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)
    }
}