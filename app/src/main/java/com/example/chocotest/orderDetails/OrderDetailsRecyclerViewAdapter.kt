package com.example.chocotest.orderDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chocotest.databinding.OrderItemLayoutBinding
import com.example.chocotest.orders.domain.OrderModel

class OrderDetailsRecyclerViewAdapter :
    ListAdapter<OrderModel, OrderDetailsRecyclerViewAdapter.OrderViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OrderModel>() {
            override fun areItemsTheSame(
                oldItem: OrderModel,
                newItem: OrderModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: OrderModel,
                newItem: OrderModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        OrderViewHolder(
            OrderItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val pm = getItem(position)
        holder.bind(pm)
    }

    inner class OrderViewHolder(
        private val binding: OrderItemLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderModel) {
            binding.orderName.text = order.name
            binding.totalCost.text = order.sum.toString()
        }
    }
}