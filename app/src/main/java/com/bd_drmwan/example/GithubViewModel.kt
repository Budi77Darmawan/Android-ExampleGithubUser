package com.bd_drmwan.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel: ViewModel() {
    var isLoading = MutableLiveData(false)
    var isLoadingTab = MutableLiveData(false)
    var listUser = MutableLiveData<List<User>>()
    var detailUser = MutableLiveData<DetailUser>()

    private val apiKey = BuildConfig.API_KEY

    fun searchUsers(username: String) {
        isLoading.value = true
        val service = NetworkService.getApiClient()?.create(GithubService::class.java)
        val call = service?.searchUser(apiKey, username)
        call?.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                isLoading.value = false
                listUser.value = response.body()?.items
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                isLoading.value = false
                listUser.value = listOf()
            }
        })
    }

    fun getDetailUser(username: String) {
        isLoading.value = true
        val service = NetworkService.getApiClient()?.create(GithubService::class.java)
        val call = service?.getDetailUser(apiKey, username)
        call?.enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                isLoading.value = false
                detailUser.value = response.body()
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun getFollowersUser(username: String) {
        isLoadingTab.value = true
        val service = NetworkService.getApiClient()?.create(GithubService::class.java)
        val call = service?.getFollowersUser(apiKey, username)
        call?.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                isLoadingTab.value = false
                listUser.value = response.body()
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                isLoadingTab.value = false
                listUser.value = listOf()
            }
        })
    }

    fun getFollowingUser(username: String) {
        isLoadingTab.value = true
        val service = NetworkService.getApiClient()?.create(GithubService::class.java)
        val call = service?.getFollowingUser(apiKey, username)
        call?.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                isLoadingTab.value = false
                listUser.value = response.body()
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                isLoadingTab.value = false
                listUser.value = listOf()
            }
        })
    }
}