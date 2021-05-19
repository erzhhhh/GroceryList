package com.example.chocotest.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chocotest.utils.CustomRunner
import com.example.chocotest.products.data.ProductsInteractorImpl
import com.example.chocotest.products.domain.ProductDao
import com.example.chocotest.products.domain.ProductEntity
import com.example.chocotest.products.domain.ProductResponse
import com.example.chocotest.products.domain.ProductsApi
import com.example.chocotest.products.domain.ProductsInteractor
import io.reactivex.Observable
import io.reactivex.Single
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
class ProductsInteractorTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var interactor: ProductsInteractor

    @Mock
    private lateinit var productsApi: ProductsApi

    @Mock
    private lateinit var productDao: ProductDao

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

    private val expectedProductsList = listOf(
        ProductEntity(
            0,
            "id12",
            "carrot",
            "carrot is vegetable",
            123.0,
            "id12.com",
            false
        ),
        ProductEntity(
            0,
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
        interactor = ProductsInteractorImpl(productsApi, productDao)
    }

    @Test
    fun assertLoadProducts() {
        `when`(productsApi.loadProducts("token"))
            .thenReturn(Observable.just(productsList))

        `when`(interactor.loadProducts("token"))
            .thenReturn(Observable.just(productsList))

        interactor.loadProducts("token").test()

        verify(productsApi, times(1)).loadProducts("token")
        verify(productDao, times(1)).updateBase(expectedProductsList)
    }

    @Test
    fun assertGetOrderProducts() {
        `when`(productDao.getOrderProducts(124))
            .thenReturn(Single.just(expectedProductsList))

        val test = interactor.getOrderProducts(124).test()

        test.assertValue(expectedProductsList)
        verify(productDao, times(1)).getOrderProducts(124)
    }
}