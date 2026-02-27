package com.portfolio.automoykago

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.automoykago.databinding.ActivityAdminBinding
import com.portfolio.automoykago.databinding.ItemAdminOrderBinding
import com.portfolio.automoykago.databinding.ItemAdminUserBinding
import com.portfolio.automoykago.db.AppDb
import com.portfolio.automoykago.db.Order
import com.portfolio.automoykago.db.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val appDb = AppDb.getInstance(this)
        val users = runBlocking {
            withContext(Dispatchers.IO) { appDb.getAllUsers(this@AdminActivity) }
        }
        val orders = runBlocking {
            withContext(Dispatchers.IO) { appDb.getAllOrders(this@AdminActivity) }
        }
        val totalRevenue = runBlocking {
            withContext(Dispatchers.IO) { appDb.getTotalRevenue(this@AdminActivity) }
        }

        binding.textTotalRevenue.text = getString(R.string.admin_total_revenue, totalRevenue)

        binding.recyclerUsers.layoutManager = LinearLayoutManager(this)
        binding.recyclerUsers.adapter = UserAdapter(users, dateFormat)

        binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
        binding.recyclerOrders.adapter = OrderAdapter(orders, dateFormat)
    }

    private class UserAdapter(
        private val users: List<User>,
        private val dateFormat: SimpleDateFormat
    ) : RecyclerView.Adapter<UserAdapter.VH>() {

        class VH(val binding: ItemAdminUserBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val binding = ItemAdminUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return VH(binding)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val u = users[position]
            holder.binding.textUserName.text = u.name
            holder.binding.textUserDate.text = dateFormat.format(Date(u.createdAt))
        }

        override fun getItemCount() = users.size
    }

    private class OrderAdapter(
        private val orders: List<Order>,
        private val dateFormat: SimpleDateFormat
    ) : RecyclerView.Adapter<OrderAdapter.VH>() {

        class VH(val binding: ItemAdminOrderBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val binding = ItemAdminOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return VH(binding)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val o = orders[position]
            holder.binding.textOrderDate.text = dateFormat.format(Date(o.createdAt))
            holder.binding.textOrderUser.text = o.userName
            holder.binding.textOrderDetails.text = "${o.address} — Модуль ${o.moduleNumber}\n${o.services}"
            holder.binding.textOrderAmount.text = "${o.totalAmount} ₽"
        }

        override fun getItemCount() = orders.size
    }
}
