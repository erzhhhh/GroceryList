package com.example.chocotest.orders.domain

import com.example.chocotest.products.domain.ProductResponse
import io.reactivex.Completable
import io.reactivex.Observable

interface OrdersInteractor {

    fun fetchOrders(): Observable<List<OrderModel>>

    fun createOrder(orderName: String, products: List<ProductResponse>): Completable
}
