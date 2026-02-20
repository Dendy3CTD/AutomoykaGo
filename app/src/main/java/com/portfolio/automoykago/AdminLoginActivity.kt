package com.portfolio.automoykago

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.automoykago.databinding.ActivityAdminLoginBinding

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminLoginBinding

    companion object {
        private const val ADMIN_LOGIN = "admin"
        private const val ADMIN_PASSWORD = "admin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdminLogin.setOnClickListener {
            val login = binding.editLogin.text?.toString()?.trim().orEmpty()
            val password = binding.editPassword.text?.toString().orEmpty()
            if (login != ADMIN_LOGIN || password != ADMIN_PASSWORD) {
                binding.inputPassword.error = getString(R.string.error_admin_wrong)
                return@setOnClickListener
            }
            binding.inputPassword.error = null
            startActivity(Intent(this, AdminActivity::class.java))
            finish()
        }
    }
}
