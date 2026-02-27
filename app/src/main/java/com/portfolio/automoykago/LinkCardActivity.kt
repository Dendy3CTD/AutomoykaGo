package com.portfolio.automoykago

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.automoykago.databinding.ActivityLinkCardBinding

class LinkCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLinkCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinkCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnLink.setOnClickListener {
            val number = binding.editCardNumber.text?.toString()?.replace(" ", "").orEmpty()
            val expiry = binding.editExpiry.text?.toString()?.trim().orEmpty()
            val cvv = binding.editCvv.text?.toString().orEmpty()
            if (number.length < 16) {
                binding.inputCardNumber.error = getString(R.string.error_card_invalid)
                return@setOnClickListener
            }
            binding.inputCardNumber.error = null
            if (expiry.length < 4) {
                binding.inputExpiry.error = getString(R.string.error_card_invalid)
                return@setOnClickListener
            }
            binding.inputExpiry.error = null
            if (cvv.length < 3) {
                binding.inputCvv.error = getString(R.string.error_card_invalid)
                return@setOnClickListener
            }
            binding.inputCvv.error = null
            val last4 = number.takeLast(4)
            Prefs.linkCard(this, last4)
            Toast.makeText(this, getString(R.string.card_linked_success), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
