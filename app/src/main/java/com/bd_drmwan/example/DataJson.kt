package com.bd_drmwan.example

import android.content.Context
import com.google.gson.Gson
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object DataJson {
    fun getUsers(mContext: Context): Users {
        val strJson = loadJsonFile(mContext)
        /**
         * Mapping string Json to object with library Gson
         */
        strJson?.let {
            return Gson().fromJson(it, Users::class.java)
        }

        /**
         * Mapping manual string Json to object
         */
//        strJson?.let {
//            val jsonObject = JSONObject(it)
//            val jsonArrayUser = jsonObject.getJSONArray("items")
//            val listUser = mutableListOf<User>()
//
//            for (index in 0 until jsonArrayUser.length()) {
//                val user = User(
//                    jsonArrayUser.getJSONObject(index).getString("id"),
//                    jsonArrayUser.getJSONObject(index).getString("name"),
//                    jsonArrayUser.getJSONObject(index).getString("login"),
//                    jsonArrayUser.getJSONObject(index).getString("avatar_url"),
//                    jsonArrayUser.getJSONObject(index).getString("type")
//                )
//                listUser.add(user)
//            }
//            return Users(listUser)
//        }

        return Users(listOf())
    }

    private fun loadJsonFile(mContext: Context): String? {
        return try {
            val `is`: InputStream = mContext.assets.open("UsersGithub.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}