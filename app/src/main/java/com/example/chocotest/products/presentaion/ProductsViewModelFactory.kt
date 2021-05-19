package com.example.chocotest.products.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.products.domain.ProductsInteractor

class ProductsViewModelFactory(
    private val productsInteractor: ProductsInteractor,
    private val loginRepository: LoginRepository
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(productsInteractor, loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}