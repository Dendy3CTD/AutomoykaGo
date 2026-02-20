package com.portfolio.automoykago

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.portfolio.automoykago.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedBay: Int? = null
    private val bayCards by lazy {
        listOf(binding.cardBay1, binding.cardBay2, binding.cardBay3, binding.cardBay4)
    }
    private val bayTitles by lazy {
        listOf(binding.titleBay1, binding.titleBay2, binding.titleBay3, binding.titleBay4)
    }
    private val bayStatuses by lazy {
        listOf(binding.statusBay1, binding.statusBay2, binding.statusBay3, binding.statusBay4)
    }
    private val chips by lazy {
        listOf(
            binding.chipQuick to 150,
            binding.chipStandard to 250,
            binding.chipPremium to 400,
            binding.chipUnderbody to 100,
            binding.chipWax to 200
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBayTitles()
        setupBaySelection()
        setupServiceChips()
        setupStartButton()
    }

    private fun setupBayTitles() {
        resources.getString(R.string.bay).let { format ->
            bayTitles.forEachIndexed { index, textView ->
                textView.text = String.format(format, index + 1)
            }
        }
        // Демо: пост 2 "занят"
        bayStatuses[1].text = getString(R.string.busy)
        bayStatuses[1].setTextColor(getColor(R.color.busy_red))
    }

    private fun setupBaySelection() {
        bayCards.forEachIndexed { index, card ->
            val statusView = bayStatuses[index]
            if (statusView.text == getString(R.string.available)) {
                card.isClickable = true
                card.setOnClickListener {
                    selectBay(index + 1)
                }
            }
        }
    }

    private fun selectBay(bayNumber: Int) {
        selectedBay = bayNumber
        bayCards.forEachIndexed { index, card ->
            card.isSelected = (index + 1) == bayNumber
            card.setCardBackgroundColor(
                if (card.isSelected) getColor(R.color.primary)
                else getColor(android.R.color.white)
            )
        }
        bayTitles.forEachIndexed { index, textView ->
            textView.setTextColor(
                if ((index + 1) == bayNumber) getColor(android.R.color.white)
                else getColor(R.color.on_surface)
            )
        }
        bayStatuses.forEachIndexed { index, textView ->
            if ((index + 1) == bayNumber) {
                textView.setTextColor(getColor(android.R.color.white))
            } else if (textView.text == getString(R.string.available)) {
                textView.setTextColor(getColor(R.color.available_green))
            }
        }
    }

    private fun setupServiceChips() {
        fun updateTotal() {
            var total = 0
            chips.forEach { (chip, price) ->
                if (chip.isChecked) total += price
            }
            binding.textTotal.text = getString(R.string.price_rub, total)
        }
        chips.forEach { (chip, _) ->
            chip.setOnCheckedChangeListener { _, _ -> updateTotal() }
        }
    }

    private fun setupStartButton() {
        binding.btnStart.setOnClickListener {
            if (selectedBay == null) {
                Toast.makeText(this, "Выберите пост", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val total = chips.filter { it.first.isChecked }.sumOf { it.second }
            if (total == 0) {
                Toast.makeText(this, "Выберите хотя бы одну услугу", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val services = chips
                .filter { it.first.isChecked }
                .joinToString(", ") { it.first.text.toString() }
            Snackbar.make(
                binding.root,
                "Пост $selectedBay: $services — $total ₽",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}
