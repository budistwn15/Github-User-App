package com.budi.setiawan.githubusersubmission3.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.budi.setiawan.githubusersubmission3.R
import com.budi.setiawan.githubusersubmission3.data.adapter.UserAdapter
import com.budi.setiawan.githubusersubmission3.data.database.FavoriteUser
import com.budi.setiawan.githubusersubmission3.data.model.UserItems
import com.budi.setiawan.githubusersubmission3.databinding.ActivityFavoriteBinding
import com.budi.setiawan.githubusersubmission3.ui.detail.DetailUserActivity
import com.budi.setiawan.githubusersubmission3.ui.setting.SettingActivity
import com.budi.setiawan.githubusersubmission3.ui.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter : UserAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserAdapter()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserItems) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also{
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if(it != null){
                val list = mapList(it)
                adapter.setList(list)
            }
            if(it.isEmpty()){
                showNoData(true)
            }else{
                showNoData(false)
            }
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

    private fun mapList(users: List<FavoriteUser>) : ArrayList<UserItems>{
        val listUsers = ArrayList<UserItems>()
        for(user in users){
            val userMapped = UserItems(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    private fun showNoData(isNoData: Boolean){
        binding.noData.visibility = if(isNoData) View.VISIBLE else View.GONE
    }
}