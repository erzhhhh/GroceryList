package com.example.chocotest.orders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.chocotest.base.ScreenState
import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.orders.domain.OrdersInteractor
import com.example.chocotest.orders.presentaion.OrdersViewModel
import com.example.chocotest.products.domain.ProductResponse
import com.example.chocotest.utils.CustomRunner
import com.example.chocotest.utils.getOrAwaitValue
import io.reactivex.Completable
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(CustomRunner::class)
class OrdersViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockObserver: Observer<ScreenState>

    @Mock
    private lateinit var interactor: OrdersInteractor

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
    }

    @Test
    fun assertSuccessInit() {
        `when`(interactor.fetchOrders())
            .thenReturn(Observable.just(ordersList))

        val viewModel = OrdersViewModel(interactor)

        viewModel.screenState.observeForever(mockObserver)

        val state = viewModel.screenState.getOrAwaitValue()
        val response = viewModel.childModels.getOrAwaitValue()

        assertThat(state, `is`(ScreenState.LOADED))
        assertThat(response, `is`(ordersList))
    }

    @Test
    fun assertErrorInit() {
        `when`(interactor.fetchOrders())
            .thenReturn(Observable.error(RuntimeException("error")))

        val viewModel = OrdersViewModel(interactor)

        viewModel.screenState.observeForever(mockObserver)

        val value = viewModel.screenState.getOrAwaitValue()
        assertThat(value, `is`(ScreenState.error("error")))
    }

    @Test
    fun assertCreateNewOrderError() {
        `when`(interactor.fetchOrders())
            .thenReturn(Observable.error(RuntimeException("error")))

        `when`(interactor.createOrder("Month supply", productsList))
            .thenReturn((Completable.complete()))

        val viewModel = OrdersViewModel(interactor)

        viewModel.screenState.observeForever(mockObserver)

        val state = viewModel.screenState.getOrAwaitValue()

        assertThat(state, `is`(ScreenState.error("error")))
    }
}