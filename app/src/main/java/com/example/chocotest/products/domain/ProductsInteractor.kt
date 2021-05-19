package com.example.chocotest.products.domain

import io.reactivex.Observable
import io.reactivex.Single

interface ProductsInteractor {

    fun loadProducts(token: String): Observable<List<ProductResponse>>

    fun getOrderProducts(orderId: Long): Single<List<ProductEntity>>
}
