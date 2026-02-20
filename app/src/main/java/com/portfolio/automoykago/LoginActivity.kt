package com.portfolio.automoykago

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.automoykago.databinding.ActivityLoginBinding
import com.portfolio.automoykago.db.AppDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnLogin.setOnClickListener {
            val name = binding.editName.text?.toString()?.trim().orEmpty()
            val password = binding.editPassword.text?.toString().orEmpty()
            if (name.isBlank()) {
                binding.inputName.error = getString(R.string.error_name_empty)
                return@setOnClickListener
            }
            binding.inputName.error = null
            if (password.isBlank()) {
                binding.inputPassword.error = getString(R.string.error_login_wrong)
                return@setOnClickListener
            }
            binding.inputPassword.error = null
            val user = runBlocking {
                withContext(Dispatchers.IO) { AppDb.getInstance(this@LoginActivity).getUserByName(this@LoginActivity, name) }
            }
            if (user == null || user.password != password) {
                binding.inputPassword.error = getString(R.string.error_login_wrong)
                return@setOnClickListener
            }
            Prefs.register(this, name, password)
            Toast.makeText(this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, WelcomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
        }
    }
}
