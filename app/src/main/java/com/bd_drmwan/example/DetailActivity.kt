package com.bd_drmwan.example

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bd_drmwan.example.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout

class DetailActivity : AppCompatActivity(), ListUserAdapter.OnItemClickCallback {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var githubViewModel: GithubViewModel

    private var detailUser: DetailUser? = null

    companion object {
        const val DATA_USER = "DATA_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val colorWhite = ContextCompat.getColor(this, R.color.white)
        binding.toolbar.navigationIcon?.setTint(colorWhite)

        initViewModel()
        initListener()
        observerLiveData()
    }

    private fun initViewModel() {
        githubViewModel = ViewModelProvider(this).get(GithubViewModel::class.java)

        val user: User? = intent.getParcelableExtra(DATA_USER)
        user?.let {
            githubViewModel.getDetailUser(it.username)
        }
    }

    private fun initListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == binding.tabLayout.getTabAt(0)) {
                    detailUser?.username?.let { username ->
                        githubViewModel.getFollowersUser(username)
                    }
                } else {
                    detailUser?.username?.let { username ->
                        githubViewModel.getFollowingUser(username)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun observerLiveData() {
        githubViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        })

        githubViewModel.detailUser.observe(this, {
            githubViewModel.getFollowersUser(it.username)
            detailUser = it
            setView()
        })

        githubViewModel.isLoadingTab.observe(this, { isLoading ->
            if (isLoading) {
                showMessage(false)
                binding.rvUsers.visibility = View.GONE
                binding.progressCircularTab.visibility = View.VISIBLE
            } else {
                binding.progressCircularTab.visibility = View.GONE
            }
        })

        githubViewModel.listUser.observe(this, {
            if (it.isNullOrEmpty()) {
                binding.rvUsers.visibility = View.GONE
                showMessage(true)
            } else {
                binding.rvUsers.visibility = View.VISIBLE
                setRecyclerView(it)
                showMessage(false)
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

    private fun setView() {
        binding.apply {
            tvName.text = detailUser?.username ?: "Unknown"
            val textUsername = "@${detailUser?.username ?: "-"}"
            tvUsername.text = textUsername
            val textRepository = "Repository : ${detailUser?.public_repos ?: 0}"
            tvRepo.text = textRepository
            tvLocation.text = detailUser?.location ?: "-"

            Glide.with(root.context)
                .load(detailUser?.imageProfile ?: Const.UriDefaultImage)
                .into(imgProfile)
        }
    }

    private fun showMessage(visible: Boolean) {
        val message = if (binding.tabLayout.selectedTabPosition == 0) "They don't have followers."
        else "Not following anyone."

        if (visible) {
            binding.tvMessage.apply {
                visibility = View.VISIBLE
                text = message
            }
        } else {
            binding.tvMessage.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClicked(user: User) {
        /**
         * handle action when selected user followers/following
         */
    }
}