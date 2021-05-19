package com.example.chocotest.orders.data

import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.orders.domain.Order
import com.example.chocotest.orders.domain.OrderDao
import com.example.chocotest.orders.domain.OrdersInteractor
import com.example.chocotest.products.domain.ProductResponse
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class OrdersInteractorImpl @Inject constructor(private val orderDao: OrderDao) : OrdersInteractor {

    override fun fetchOrders(): Observable<List<OrderModel>> {
        return orderDao.fetchOrders()
    }

    override fun createOrder(orderName: String, products: List<ProductResponse>): Completable {
        return Completable.fromAction {
            orderDao.createOrder(
                Order(
                    name = orderName,
                    products = products.map(ProductResponse::id)
                )
            )
        }
    }
}
