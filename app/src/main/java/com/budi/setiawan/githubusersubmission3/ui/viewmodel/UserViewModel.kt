package com.budi.setiawan.githubusersubmission3.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.budi.setiawan.githubusersubmission3.api.ApiConfig
import com.budi.setiawan.githubusersubmission3.data.model.UserItems
import com.budi.setiawan.githubusersubmission3.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val _listUsers = MutableLiveData<ArrayList<UserItems>>()
    private val listUsers : LiveData<ArrayList<UserItems>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "UserViewModel"
    }

    fun setSearchUser(query: String){
        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<UserResponse>{
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listUsers.postValue(response.body()?.items)
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    fun getSearchUser(): LiveData<ArrayList<UserItems>>{
        return listUsers
    }
}