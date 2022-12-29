package com.feijoo.legostore.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feijoo.legostore.BuildConfig
import com.feijoo.legostore.R
import com.feijoo.legostore.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    /** Attributes **/
    private lateinit var binding: ActivityMainBinding

    /** Methods **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()

    }

    private fun initComponents() {
        binding.textViewVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)

        initListener()

    }

    private fun initListener() {


    }

}