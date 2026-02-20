package com.portfolio.automoykago

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.automoykago.databinding.ActivityWelcomeBinding
import com.portfolio.automoykago.databinding.ItemAddressBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Prefs.isRegistered(this)) {
            startActivity(Intent(this, EntryActivity::class.java))
            finish()
            return
        }
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconReviews.setOnClickListener {
            startActivity(Intent(this, ReviewsActivity::class.java))
        }
        binding.iconSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        val name = Prefs.getUserName(this).orEmpty().ifEmpty { getString(R.string.app_name) }
        binding.textGreeting.text = getString(R.string.greeting, name)

        val addresses = resources.getStringArray(R.array.addresses_rostov)
        val container = binding.listAddresses as LinearLayout
        addresses.forEachIndexed { index, address ->
            val itemBinding = ItemAddressBinding.inflate(layoutInflater, container, false)
            itemBinding.textAddress.text = address
            itemBinding.root.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra(MainActivity.EXTRA_ADDRESS_INDEX, index)
                })
            }
            container.addView(itemBinding.root)
        }
    }
}
