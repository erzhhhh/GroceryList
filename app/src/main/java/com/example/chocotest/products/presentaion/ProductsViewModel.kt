package com.example.chocotest.products.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.base.BaseViewModel
import com.example.chocotest.base.ScreenState
import com.example.chocotest.products.domain.ProductResponse
import com.example.chocotest.products.domain.ProductsInteractor
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productsInteractor: ProductsInteractor,
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private var _childModels = MutableLiveData<List<ProductResponse>>()
    val childModels: LiveData<List<ProductResponse>> = _childModels

    private val _screenState: MutableLiveData<ScreenState> = MutableLiveData()
    val screenState: LiveData<ScreenState> = _screenState

    init {
        loadProducts()
    }

    private fun loadProducts() {
        productsInteractor
            .loadProducts(loginRepository.getToken())
            .doOnSubscribe { _screenState.value = ScreenState.LOADING }
            .subscribe(
                {
                    _screenState.value = ScreenState.LOADED
                    _childModels.value = it
                },
                {
                    _screenState.value = ScreenState.error(it.message)
                }
            )
            .addToComposite()
    }
}