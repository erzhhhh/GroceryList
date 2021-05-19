package com.example.chocotest.orders.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chocotest.orders.domain.OrdersInteractor

class OrdersViewModelFactory(
    private val ordersInteractor: OrdersInteractor
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            return OrdersViewModel(ordersInteractor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}