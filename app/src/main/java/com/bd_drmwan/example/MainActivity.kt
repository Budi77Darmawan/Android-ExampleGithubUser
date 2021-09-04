package com.bd_drmwan.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bd_drmwan.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ListUserAdapter.OnItemClickCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var githubViewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showMessage(true, "Waiting to search user ...")
        initViewModel()
        observerLiveData()
        setupView()
    }

    private fun setupView() {
        binding.apply {
            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        /**
                         * clear focus on search bar to hide keyboard
                         */
                        searchBar.clearFocus()
                        searchBar.isFocusable = false
                        /**
                         * get user by query on search bar
                         */
                        githubViewModel.searchUsers(it)
                    }
                    return true
                }

                /**
                 * onQueryTextChange is
                 * function to perform action every time there is a change of 1 word on search bar
                 */
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isEmpty() == true) {
                        rvUsers.visibility = View.GONE
                        showMessage(true, "Waiting to search user ...")
                    }
                    return true
                }
            })
        }
    }

    private fun initViewModel() {
        githubViewModel = ViewModelProvider(this).get(GithubViewModel::class.java)
    }

    private fun observerLiveData() {
        githubViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE
                binding.rvUsers.visibility = View.GONE
                showMessage(false, null)
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        })

        githubViewModel.listUser.observe(this, {
            if (it.isNullOrEmpty()) {
                binding.rvUsers.visibility = View.GONE
                showMessage(true, "User not found!", true)
            } else {
                binding.rvUsers.visibility = View.VISIBLE
                showMessage(false, null)
                setRecyclerView(it)
            }
        })
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

    private fun showMessage(visible: Boolean, msg: String?, isError: Boolean = false) {
        binding.apply {
            if (visible) {
                imgMessage.visibility = View.VISIBLE
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = msg
                if (isError) {
                    imgMessage.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_not_found)
                } else {
                    imgMessage.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_search)
                }
            } else {
                imgMessage.visibility = View.GONE
                tvMessage.visibility = View.GONE
            }
        }
    }

    override fun onItemClicked(user: User) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DATA_USER, user)
        startActivity(intent)
    }
}