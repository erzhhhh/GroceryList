package com.example.chocotest.base

import com.example.chocotest.products.domain.ProductResponse

interface OnProductSelectListener {

    fun onAddToCart(response: ProductResponse)

    fun onRemoveFromCart(response: ProductResponse)
}

interface OnOrderClickListener<Order> {

    fun onOrderClick(order: Order)
}