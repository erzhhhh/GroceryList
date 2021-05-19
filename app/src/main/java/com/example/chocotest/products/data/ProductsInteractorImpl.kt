package com.example.chocotest.products.data

import com.example.chocotest.products.domain.ProductDao
import com.example.chocotest.products.domain.ProductEntity
import com.example.chocotest.products.domain.ProductResponse
import com.example.chocotest.products.domain.ProductsApi
import com.example.chocotest.products.domain.ProductsInteractor
import com.example.chocotest.products.domain.mapToProductEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductsInteractorImpl @Inject constructor(
    private val productsApi: ProductsApi,
    private val productDao: ProductDao
) : ProductsInteractor {

    override fun loadProducts(token: String): Observable<List<ProductResponse>> {
        return productsApi.loadProducts(token)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                productDao.updateBase(it.mapToProductEntity())
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getOrderProducts(orderId: Long): Single<List<ProductEntity>> {
        return productDao.getOrderProducts(orderId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}