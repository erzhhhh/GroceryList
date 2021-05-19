package com.example.chocotest.orderDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.chocotest.base.ScreenState
import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.products.domain.ProductEntity
import com.example.chocotest.products.domain.ProductsInteractor
import com.example.chocotest.utils.CustomRunner
import com.example.chocotest.utils.getOrAwaitValue
import io.reactivex.Single
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
class OrderDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockObserver: Observer<ScreenState>

    @Mock
    private lateinit var interactor: ProductsInteractor

    private val productEntitiesList = listOf(
        ProductEntity(
            1,
            "id12",
            "carrot",
            "carrot is vegetable",
            123.0,
            "id12.com",
            false
        ),
        ProductEntity(
            2,
            "id13",
            "apple",
            "apple is fruit",
            124.0,
            "id13.com",
            false
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun assertSuccessInit() {
        `when`(interactor.getOrderProducts(1))
            .thenReturn(Single.just(productEntitiesList))

        val viewModel = OrderDetailViewModel(interactor, OrderModel(1, "Monthly supply", 123.0))

        viewModel.screenState.observeForever(mockObserver)

        val state = viewModel.screenState.getOrAwaitValue()
        val response = viewModel.productModels.getOrAwaitValue()

        assertThat(state, `is`(ScreenState.LOADED))
        assertThat(response, `is`(productEntitiesList))
    }

    @Test
    fun assertErrorInit() {
        `when`(interactor.getOrderProducts(1))
            .thenReturn(Single.error(RuntimeException("error")))

        val viewModel = OrderDetailViewModel(interactor, OrderModel(1, "Monthly supply", 123.0))

        viewModel.screenState.observeForever(mockObserver)

        val value = viewModel.screenState.getOrAwaitValue()
        assertThat(value, `is`(ScreenState.error("error")))
    }
}