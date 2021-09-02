package com.bd_drmwan.example

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bd_drmwan.example.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var user: User? = null

    companion object {
        const val DATA_USER = "DATA_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val colorWhite = ContextCompat.getColor(this, R.color.white);
        binding.toolbar.navigationIcon?.setTint(colorWhite)

        user = intent.getParcelableExtra(DATA_USER)
        setView()
    }

    private fun setView() {
        binding.apply {
            tvName.text = user?.name ?: "Unknown"
            tvUsername.text = "@${user?.username}" ?: "-"

            Glide.with(root.context)
                .load(user?.imageProfile)
                .into(imgProfile)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}