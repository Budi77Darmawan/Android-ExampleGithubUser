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
    val name: String,
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val imageProfile: String,
    @SerializedName("type")
    val accountType: String
): Parcelable