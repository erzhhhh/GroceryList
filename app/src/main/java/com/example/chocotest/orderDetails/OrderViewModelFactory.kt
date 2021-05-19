package com.example.chocotest.orderDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.products.domain.ProductsInteractor

class OrderViewModelFactory(
    private val productsInteractor: ProductsInteractor,
    private val order: OrderModel
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)) {
            return OrderDetailViewModel(productsInteractor, order) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}