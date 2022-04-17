package com.budi.setiawan.githubusersubmission3.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.budi.setiawan.githubusersubmission3.api.ApiConfig
import com.budi.setiawan.githubusersubmission3.data.model.UserItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    private val _listFollowing = MutableLiveData<ArrayList<UserItems>>()
    private val listFollowing: LiveData<ArrayList<UserItems>> = _listFollowing

    fun setListFollowing(username: String){
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<UserItems>>{
            override fun onResponse(
                call: Call<ArrayList<UserItems>>,
                response: Response<ArrayList<UserItems>>
            ) {
                if(response.isSuccessful){
                    _listFollowing.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<UserItems>>, t: Throwable) {
                t.message?.let { Log.d("Failure", it) }
            }
        })
    }

    fun getListFollowing(): LiveData<ArrayList<UserItems>> {
        return listFollowing
    }
}