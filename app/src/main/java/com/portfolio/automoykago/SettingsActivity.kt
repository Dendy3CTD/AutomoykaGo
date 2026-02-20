package com.portfolio.automoykago

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.automoykago.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        updateBalanceDisplay()
        binding.cardBalance.setOnClickListener { startActivity(Intent(this, TopUpActivity::class.java)) }
        binding.btnTopUp.setOnClickListener { startActivity(Intent(this, TopUpActivity::class.java)) }

        binding.switchDarkTheme.isChecked = Prefs.isDarkTheme(this)
        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            Prefs.setDarkTheme(this, isChecked)
        }

        updateCardStatus()
        binding.cardLinkCard.setOnClickListener { openLinkCard() }
        binding.btnCardAction.setOnClickListener { openLinkCard() }

        binding.btnLogout.setOnClickListener {
            Prefs.logout(this)
            startActivity(Intent(this, EntryActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        updateBalanceDisplay()
        updateCardStatus()
    }

    private fun updateBalanceDisplay() {
        binding.textBalance.text = getString(R.string.balance, Prefs.getBalance(this))
    }

    private fun updateCardStatus() {
        if (Prefs.isCardLinked(this)) {
            val last4 = Prefs.getCardLast4(this).orEmpty()
            binding.textCardStatus.text = getString(R.string.card_linked_format, last4)
            binding.btnCardAction.text = getString(R.string.unlink_card)
        } else {
            binding.textCardStatus.text = getString(R.string.link_card)
            binding.btnCardAction.text = getString(R.string.link_card)
        }
    }

    private fun openLinkCard() {
        if (Prefs.isCardLinked(this)) {
            Prefs.unlinkCard(this)
            updateCardStatus()
        } else {
            startActivity(Intent(this, LinkCardActivity::class.java))
        }
    }
}
