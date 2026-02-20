package com.portfolio.automoykago

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.automoykago.databinding.ActivityTopUpBinding

class TopUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        updateBalanceDisplay()
        binding.btn500.setOnClickListener { applyAmount(500) }
        binding.btn1000.setOnClickListener { applyAmount(1000) }
        binding.btn2000.setOnClickListener { applyAmount(2000) }

        binding.btnByCard.setOnClickListener {
            val amount = getAmountFromInput()
            if (amount == null || amount < 1) {
                binding.inputAmount.error = getString(R.string.error_amount_invalid)
                return@setOnClickListener
            }
            binding.inputAmount.error = null
            Prefs.addBalance(this, amount)
            Toast.makeText(this, getString(R.string.top_up_success, amount), Toast.LENGTH_SHORT).show()
            updateBalanceDisplay()
        }

        binding.btnDemo.setOnClickListener {
            val amount = getAmountFromInput()
            if (amount == null || amount < 1) {
                binding.inputAmount.error = getString(R.string.error_amount_invalid)
                return@setOnClickListener
            }
            binding.inputAmount.error = null
            Prefs.addBalance(this, amount)
            Toast.makeText(this, getString(R.string.top_up_success, amount), Toast.LENGTH_SHORT).show()
            updateBalanceDisplay()
        }
    }

    private fun updateBalanceDisplay() {
        val balance = Prefs.getBalance(this)
        binding.textCurrentBalance.text = getString(R.string.balance, balance)
    }

    private fun applyAmount(amount: Int) {
        binding.editAmount.setText(amount.toString())
        binding.inputAmount.error = null
    }

    private fun getAmountFromInput(): Int? {
        return binding.editAmount.text?.toString()?.toIntOrNull()
    }
}
