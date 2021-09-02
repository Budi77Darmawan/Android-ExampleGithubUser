package com.bd_drmwan.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bd_drmwan.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ListUserAdapter.OnItemClickCallback {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUsersFromJson()
    }

    private fun getUsersFromJson() {
        val users = DataJson.getUsers(this)
        setRecyclerView(users.items)
    }

    private fun setRecyclerView(list: List<User>) {
        val adapter = ListUserAdapter(list)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.setOnItemClickCallback(this)
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager = layoutManager

        /**
         * Setter and getter interface.
         * If you want to use the code below,
         * you can delete the extended interface ListUserAdapter.OnItemClickCallback on class MainActivity line 9,
         * function setter interface on line 28
         * and override fun onItemClicked() line 46
         * because it has the same function.
         */
//        adapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback{
//            override fun onItemClicked(user: User) {
//                val intent = Intent(this@MainActivity, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.DATA_USER, user)
//                startActivity(intent)
//            }
//        })
    }

    override fun onItemClicked(user: User) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DATA_USER, user)
        startActivity(intent)
    }
}