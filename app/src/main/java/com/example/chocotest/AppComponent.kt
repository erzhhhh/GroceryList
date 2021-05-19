package com.example.chocotest

import com.example.chocotest.auth.presentaion.LoginActivity
import com.example.chocotest.orderDetails.OrderDetailFragment
import com.example.chocotest.orders.presentaion.OrdersFragment
import com.example.chocotest.products.presentaion.ProductsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun inject(target: LoginActivity)

    fun inject(target: ProductsFragment)

    fun inject(target: OrdersFragment)

    fun inject(target: OrderDetailFragment)
}