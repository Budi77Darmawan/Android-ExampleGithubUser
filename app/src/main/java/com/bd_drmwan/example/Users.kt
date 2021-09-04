package com.bd_drmwan.example

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    val items: List<User>
): Parcelable

@Parcelize
data class User(
    val id: String,
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val imageProfile: String?,
    @SerializedName("type")
    val accountType: String
): Parcelable

@Parcelize
data class DetailUser(
    val id: String,
    val name: String,
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val imageProfile: String?,
    val company: String,
    val location: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int,
    @SerializedName("type")
    val accountType: String
): Parcelable