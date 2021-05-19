package com.example.chocotest.orders.di

import com.example.chocotest.orders.data.OrdersInteractorImpl
import com.example.chocotest.orders.domain.OrderDao
import com.example.chocotest.orders.domain.OrdersInteractor
import dagger.Module
import dagger.Provides

@Module
class OrdersModule {

    @Provides
    fun provideOrdersInteractor(orderDao: OrderDao): OrdersInteractor =
        OrdersInteractorImpl(orderDao)
}