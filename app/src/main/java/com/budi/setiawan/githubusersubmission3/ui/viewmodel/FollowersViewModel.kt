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

class FollowersViewModel: ViewModel() {
    private val _listFollowers = MutableLiveData<ArrayList<UserItems>>()
    private val listFollowers: LiveData<ArrayList<UserItems>> = _listFollowers

    fun setListFollowers(username: String){
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<UserItems>>{
            override fun onResponse(
                call: Call<ArrayList<UserItems>>,
                response: Response<ArrayList<UserItems>>
            ) {
                if(response.isSuccessful){
                    _listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<UserItems>>, t: Throwable) {
                t.message?.let { Log.d("Failure", it) }
            }
        })
    }

    fun getListFollowers(): LiveData<ArrayList<UserItems>> {
        return listFollowers
    }
}