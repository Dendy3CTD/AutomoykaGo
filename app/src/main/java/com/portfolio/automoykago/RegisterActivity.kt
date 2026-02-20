package com.portfolio.automoykago

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.automoykago.databinding.ActivityRegisterBinding
import com.portfolio.automoykago.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.editName.text?.toString()?.trim().orEmpty()
            val password = binding.editPassword.text?.toString().orEmpty()
            if (name.isBlank()) {
                binding.inputName.error = getString(R.string.error_name_empty)
                return@setOnClickListener
            }
            binding.inputName.error = null
            if (password.length < 4) {
                binding.inputPassword.error = getString(R.string.error_password_short)
                return@setOnClickListener
            }
            binding.inputPassword.error = null
            val db = AppDatabase.getInstance(this)
            val existing = runBlocking {
                withContext(Dispatchers.IO) { db.userDao().getByName(name) }
            }
            if (existing != null) {
                binding.inputName.error = getString(R.string.error_name_exists)
                return@setOnClickListener
            }
            runBlocking {
                withContext(Dispatchers.IO) {
                    db.userDao().insert(com.portfolio.automoykago.db.User(name = name, password = password))
                }
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
