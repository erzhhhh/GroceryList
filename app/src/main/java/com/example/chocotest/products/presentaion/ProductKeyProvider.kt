package com.example.chocotest.products.presentaion

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.chocotest.products.domain.ProductResponse

class ProductKeyProvider(private val adapter: ProductsRecyclerViewAdapter) :
    ItemKeyProvider<ProductResponse>(SCOPE_CACHED) {

    override fun getKey(position: Int): ProductResponse? = adapter.currentList[position]

    override fun getPosition(key: ProductResponse) =
        adapter.currentList.indexOfFirst { it.id == key.id }
}

class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<ProductResponse>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<ProductResponse>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        return (recyclerView.getChildViewHolder(view!!) as ProductsRecyclerViewAdapter.ProductViewHolder).getItemDetails()
    }
}
