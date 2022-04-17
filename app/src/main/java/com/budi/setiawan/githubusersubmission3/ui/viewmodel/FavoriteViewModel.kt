package com.budi.setiawan.githubusersubmission3.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.budi.setiawan.githubusersubmission3.data.database.FavoriteUser
import com.budi.setiawan.githubusersubmission3.data.database.FavoriteUserDao
import com.budi.setiawan.githubusersubmission3.data.database.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao: FavoriteUserDao?

    init{
        val userDb = UserDatabase.getDatabase(application)
        userDao = userDb.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}