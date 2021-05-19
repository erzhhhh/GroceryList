package com.example.chocotest.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.chocotest.utils.CustomRunner
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.base.ScreenState
import com.example.chocotest.utils.getOrAwaitValue
import com.example.chocotest.products.domain.ProductResponse
import com.example.chocotest.products.domain.ProductsInteractor
import com.example.chocotest.products.presentaion.ProductsViewModel
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
class ProductsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockObserver: Observer<ScreenState>

    @Mock
    private lateinit var interactor: ProductsInteractor

    @Mock
    private lateinit var repository: LoginRepository

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
            "carrot",
            "apple is fruit",
            124,
            "id13.com"
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun assertSuccessInit() {
        `when`(interactor.loadProducts("token"))
            .thenReturn(Observable.just(productsList))

        `when`(repository.getToken())
            .thenReturn("token")

        val viewModel = ProductsViewModel(interactor, repository)

        viewModel.screenState.observeForever(mockObserver)

        val state = viewModel.screenState.getOrAwaitValue()
        val response = viewModel.childModels.getOrAwaitValue()

        assertThat(state, `is`(ScreenState.LOADED))
        assertThat(response, `is`(productsList))
    }

    @Test
    fun assertErrorInit() {
        `when`(interactor.loadProducts("token"))
            .thenReturn(Observable.just(productsList))

        `when`(repository.getToken())
            .thenReturn("token")

        val viewModel = ProductsViewModel(interactor, repository)

        viewModel.screenState.observeForever(mockObserver)

        val value = viewModel.screenState.getOrAwaitValue()
        assertThat(value, `is`(ScreenState.LOADED))
    }
}