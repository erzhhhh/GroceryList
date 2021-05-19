package com.example.chocotest

import com.example.chocotest.auth.LoginActivityTest
import com.example.chocotest.orderDetails.OrderDetailsFragmentTest
import com.example.chocotest.orders.OrdersFragmentTest
import com.example.chocotest.products.ProductsFragmentTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestAppModule::class
    ]
)
interface TestAppComponent : AppComponent {

    fun inject(target: ProductsFragmentTest)

    fun inject(target: OrdersFragmentTest)

    fun inject(target: LoginActivityTest)

    fun inject(target: OrderDetailsFragmentTest)
}