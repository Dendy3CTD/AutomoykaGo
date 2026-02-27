package com.portfolio.automoykago

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.automoykago.databinding.ActivityAddReviewBinding
import com.portfolio.automoykago.db.AppDb
import com.portfolio.automoykago.db.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AddReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnSubmit.setOnClickListener {
            val text = binding.editReview.text?.toString()?.trim().orEmpty()
            if (text.isBlank()) {
                binding.inputReview.error = getString(R.string.error_review_empty)
                return@setOnClickListener
            }
            binding.inputReview.error = null
            val rating = when (binding.ratingGroup.checkedRadioButtonId) {
                R.id.rating1 -> 1
                R.id.rating2 -> 2
                R.id.rating3 -> 3
                R.id.rating4 -> 4
                else -> 5
            }
            val userName = Prefs.getUserName(this).orEmpty().ifEmpty { "Гость" }
            runBlocking {
                withContext(Dispatchers.IO) {
                    AppDb.getInstance(this@AddReviewActivity).insertReview(
                        this@AddReviewActivity,
                        userName = userName,
                        text = text,
                        rating = rating
                    )
                }
            }
            Toast.makeText(this, getString(R.string.review_success), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
