package com.portfolio.automoykago

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.automoykago.databinding.ActivityReviewsBinding
import com.portfolio.automoykago.databinding.ItemReviewBinding
import com.portfolio.automoykago.db.AppDb
import com.portfolio.automoykago.db.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewsBinding
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnAddReview.setOnClickListener {
            startActivity(Intent(this, AddReviewActivity::class.java))
        }

        loadReviews()
    }

    override fun onResume() {
        super.onResume()
        loadReviews()
    }

    private fun loadReviews() {
        val reviews = runBlocking {
            withContext(Dispatchers.IO) { AppDb.getInstance(this@ReviewsActivity).getAllReviews(this@ReviewsActivity) }
        }
        binding.recyclerReviews.layoutManager = LinearLayoutManager(this)
        binding.recyclerReviews.adapter = ReviewAdapter(reviews, dateFormat)
    }

    private class ReviewAdapter(
        private val reviews: List<Review>,
        private val dateFormat: SimpleDateFormat
    ) : RecyclerView.Adapter<ReviewAdapter.VH>() {

        class VH(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return VH(binding)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val r = reviews[position]
            holder.binding.textReviewAuthor.text = r.userName
            holder.binding.textReviewRating.text = "★ ${r.rating}/5"
            holder.binding.textReviewBody.text = r.text
            holder.binding.textReviewDate.text = dateFormat.format(Date(r.createdAt))
        }

        override fun getItemCount() = reviews.size
    }
}
