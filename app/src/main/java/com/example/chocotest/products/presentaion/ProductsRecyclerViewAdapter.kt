package com.example.chocotest.products.presentaion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chocotest.R
import com.example.chocotest.base.OnProductSelectListener
import com.example.chocotest.databinding.ProductItemLayoutBinding
import com.example.chocotest.products.domain.ProductResponse

class ProductsRecyclerViewAdapter(
    var onProductSelectListener: OnProductSelectListener,
) : ListAdapter<ProductResponse, ProductsRecyclerViewAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    var tracker: SelectionTracker<ProductResponse>? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductResponse>() {
            override fun areItemsTheSame(
                oldItem: ProductResponse,
                newItem: ProductResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductResponse,
                newItem: ProductResponse
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
            onProductSelectListener
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val pm = getItem(position)
        tracker?.let {
            holder.bind(pm, it.isSelected(pm))
        }
    }

    inner class ProductViewHolder(
        private val binding: ProductItemLayoutBinding,
        private val onProductSelectListener: OnProductSelectListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductResponse, isActivated: Boolean = false) {
            binding.name.text =
                binding.root.context.getString(R.string.product, product.name)
            binding.price.text =
                binding.root.context.getString(R.string.price, product.price.toString())
            binding.description.text = product.description
            binding.productView.isSelected = isActivated
            if (isActivated) {
                onProductSelectListener.onAddToCart(product)
            } else {
                onProductSelectListener.onRemoveFromCart(product)
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<ProductResponse> =

            object : ItemDetailsLookup.ItemDetails<ProductResponse>() {

                override fun getPosition(): Int = adapterPosition

                override fun getSelectionKey(): ProductResponse? = getItem(adapterPosition)
            }
    }
}