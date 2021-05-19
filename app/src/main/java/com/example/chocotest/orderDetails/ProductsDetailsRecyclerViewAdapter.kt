package com.example.chocotest.orderDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chocotest.R
import com.example.chocotest.databinding.ProductItemLayoutBinding
import com.example.chocotest.products.domain.ProductEntity

class ProductsDetailsRecyclerViewAdapter :
    ListAdapter<ProductEntity, ProductsDetailsRecyclerViewAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductEntity>() {
            override fun areItemsTheSame(
                oldItem: ProductEntity,
                newItem: ProductEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductEntity,
                newItem: ProductEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ProductItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val pm = getItem(position)
        holder.bind(pm)
    }

    class ProductViewHolder(
        private val binding: ProductItemLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductEntity) {
            binding.name.text =
                binding.root.context.getString(R.string.product, product.name)
            binding.price.text =
                binding.root.context.getString(R.string.price, product.price.toString())
            binding.description.text = product.description
        }
    }
}