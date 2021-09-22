package com.bd_drmwan.example

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bd_drmwan.example.databinding.ActivitySpinnerBinding

class SpinnerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpinnerBinding
    private lateinit var githubViewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        observerLiveData()
    }

    private fun initViewModel() {
        githubViewModel = ViewModelProvider(this).get(GithubViewModel::class.java)
        githubViewModel.searchUsers("budi")
    }

    private fun observerLiveData() {
        githubViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        })

        githubViewModel.listUser.observe(this, {
            if (it.isNullOrEmpty()) {
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show()
            } else {
                setupSpinner(it)
            }
        })
    }

    private fun setupSpinner(data: List<User>) {
        val listName = mutableListOf<String>()
        for (index in data.indices) {
            listName.add(data[index].username)
        }

        val adapter = ArrayAdapter(
            this,
        android.R.layout.simple_spinner_dropdown_item,
        listName
        )
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@SpinnerActivity, listName[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

}