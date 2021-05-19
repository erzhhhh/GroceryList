package com.example.chocotest.products.di

import com.example.chocotest.products.data.ProductsInteractorImpl
import com.example.chocotest.products.domain.ProductDao
import com.example.chocotest.products.domain.ProductsApi
import com.example.chocotest.products.domain.ProductsInteractor
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ProductsModule {

    @Singleton
    @Provides
    fun provideProductsApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }

    @Provides
    fun provideProductsInteractor(
        productsApi: ProductsApi,
        productDao: ProductDao
    ): ProductsInteractor =
        ProductsInteractorImpl(productsApi, productDao)
}