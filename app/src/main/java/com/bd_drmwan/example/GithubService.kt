package com.bd_drmwan.example

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    fun searchUser(
        @Header("Authorization") auth: String = "YOUR API KEY",
        @Query("q") username: String
    ): Call<Users>

    @GET("users/{username}")
    fun getDetailUser(
        @Header("Authorization") auth: String = "YOUR API KEY",
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Header("Authorization") auth: String = "YOUR API KEY",
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Header("Authorization") auth: String = "YOUR API KEY",
        @Path("username") username: String
    ): Call<List<User>>
}