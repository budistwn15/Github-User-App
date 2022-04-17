package com.budi.setiawan.githubusersubmission3.ui.viewmodel
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.budi.setiawan.githubusersubmission3.api.ApiConfig
import com.budi.setiawan.githubusersubmission3.data.database.FavoriteUser
import com.budi.setiawan.githubusersubmission3.data.database.FavoriteUserDao
import com.budi.setiawan.githubusersubmission3.data.database.UserDatabase
import com.budi.setiawan.githubusersubmission3.data.model.UserDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private val _user = MutableLiveData<UserDetailResponse>()
    private val user : LiveData<UserDetailResponse> = _user

    private val userDao: FavoriteUserDao?

    init{
        val userDb = UserDatabase.getDatabase(application)
        userDao = userDb.favoriteUserDao()
    }

    fun setUserDetail(username: String){
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetailResponse>{
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if(response.isSuccessful){
                    _user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                t.message?.let { Log.d("Failure", it) }
            }

        })
    }

    fun getUserDetail(): LiveData<UserDetailResponse>{
        return user
    }

    fun addToFavorite(id: Int, username: String, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                id,
                username,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeUFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}