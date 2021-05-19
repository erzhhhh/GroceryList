package com.example.chocotest.products.domain

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

private const val PRODUCTS = "choco/products"

interface ProductsApi {

    @GET(PRODUCTS)
    fun loadProducts(@Query("token") token: String): Observable<List<ProductResponse>>
}