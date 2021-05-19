package com.example.chocotest

import android.content.Context
import com.example.chocotest.auth.domain.LoginInteractor
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.orders.domain.OrdersInteractor
import com.example.chocotest.products.domain.ProductsInteractor
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
class TestAppModule(@get:Provides val context: Context) {

    @Provides
    @Singleton
    fun provideProductsInteractor(): ProductsInteractor = mock(ProductsInteractor::class.java)

    @Provides
    @Singleton
    fun provideOrdersInteractor(): OrdersInteractor = mock(OrdersInteractor::class.java)

    @Provides
    @Singleton
    fun provideLoginInteractor(): LoginInteractor = mock(LoginInteractor::class.java)


    @Provides
    @Singleton
    fun provideLoginRepository(): LoginRepository = mock(LoginRepository::class.java)
}
