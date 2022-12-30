package com.feijoo.legostore.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.feijoo.legostore.BuildConfig
import com.feijoo.legostore.R
import com.feijoo.legostore.databinding.ActivityMainBinding
import com.feijoo.legostore.ui.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    /** Attributes **/
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    /** Methods **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        observe()

    }

    private fun initComponents() {
        binding.textViewVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)

        initListener()

    }

    private fun initListener() {
        binding.editTextEmail.doOnTextChanged { _, _, _, _ ->
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            binding.buttonLogIn.isEnabled = email.isNotEmpty() && password.isNotEmpty()

        }

        binding.editTextPassword.doOnTextChanged { _, _, _, _ ->
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            binding.buttonLogIn.isEnabled = email.isNotEmpty() && password.isNotEmpty()

        }

        binding.buttonLogIn.setOnClickListener { view ->
            view.isEnabled = false

            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if(email.isEmpty()) {
                binding.layoutEmail.error = getString(R.string.email_is_empty)
                binding.layoutPassword.error = ""
                binding.layoutPassword.isErrorEnabled = false
                view.isEnabled = true

            } else if(password.isEmpty()) {
                binding.layoutEmail.error = ""
                binding.layoutEmail.isErrorEnabled = false
                binding.layoutPassword.error = getString(R.string.password_is_empty)
                view.isEnabled = true

            } else {
                binding.layoutEmail.error = ""
                binding.layoutEmail.isErrorEnabled = false
                binding.layoutPassword.error = ""
                binding.layoutPassword.isErrorEnabled = false

                viewModel.logIn(this, email, password)

            }

        }

    }

    private fun observe() {
        viewModel.sessionStarted.observe(this) { response ->
            val status = response.status
            val message = response.message

            if(status) {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(this, R.color.green)).show()

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            } else {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(this, R.color.red)).show()

            }

            binding.buttonLogIn.isEnabled = true

        }

    }

}