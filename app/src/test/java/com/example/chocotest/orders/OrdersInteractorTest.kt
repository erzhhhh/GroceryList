package com.example.chocotest.orders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chocotest.orders.data.OrdersInteractorImpl
import com.example.chocotest.orders.domain.OrderDao
import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.orders.domain.OrdersInteractor
import com.example.chocotest.products.domain.ProductResponse
import com.example.chocotest.utils.CustomRunner
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(CustomRunner::class)
class OrdersInteractorTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var interactor: OrdersInteractor

    @Mock
    private lateinit var orderDao: OrderDao

    private val productsList = listOf(
        ProductResponse(
            "id12",
            "carrot",
            "carrot is vegetable",
            123,
            "id12.com"
        ),
        ProductResponse(
            "id13",
            "apple",
            "apple is fruit",
            124,
            "id13.com"
        )
    )

    private val ordersList = listOf(
        OrderModel(
            1,
            "Month supply",
            1400.0,
        ),
        OrderModel(
            2,
            "Week supply",
            400.0,
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = OrdersInteractorImpl(orderDao)
    }

    @Test
    fun assertFetchOrders() {
        `when`(interactor.fetchOrders())
            .thenReturn(Observable.just(ordersList))

        interactor.fetchOrders().test()

        verify(orderDao, times(1)).fetchOrders()
    }
}