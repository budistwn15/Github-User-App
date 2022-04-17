package com.budi.setiawan.githubusersubmission3.api

import com.budi.setiawan.githubusersubmission3.BuildConfig
import com.budi.setiawan.githubusersubmission3.data.model.UserDetailResponse
import com.budi.setiawan.githubusersubmission3.data.model.UserItems
import com.budi.setiawan.githubusersubmission3.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization:$MY_SECRET_KEY")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization:$MY_SECRET_KEY")
    fun getUserDetail(
        @Path("username") username : String
    ) : Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization:$MY_SECRET_KEY")
    fun getFollowers(
        @Path("username") username : String
    ) : Call<ArrayList<UserItems>>

    @GET("users/{username}/following")
    @Headers("Authorization:$MY_SECRET_KEY")
    fun getFollowing(
        @Path("username") username : String
    ) : Call<ArrayList<UserItems>>

    companion object{
        private const val MY_SECRET_KEY = BuildConfig.KEY
    }
}