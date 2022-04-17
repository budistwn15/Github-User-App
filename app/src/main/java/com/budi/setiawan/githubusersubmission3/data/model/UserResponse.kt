package com.budi.setiawan.githubusersubmission3.data.model

data class UserResponse(
    val items : ArrayList<UserItems>
)

data class UserItems(
    val login: String,
    val id: Int,
    val avatar_url: String,
)