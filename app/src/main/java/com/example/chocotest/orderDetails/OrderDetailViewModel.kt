package com.example.chocotest.orderDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chocotest.base.BaseViewModel
import com.example.chocotest.base.ScreenState
import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.products.domain.ProductEntity
import com.example.chocotest.products.domain.ProductsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class OrderDetailViewModel @Inject constructor(
    private val productsInteractor: ProductsInteractor,
    private val order: OrderModel
) :
    BaseViewModel() {

    val orderModels: LiveData<List<OrderModel>> = MutableLiveData(listOf(order))

    private var _productModels = MutableLiveData<List<ProductEntity>>()
    val productModels: LiveData<List<ProductEntity>> = _productModels

    private val _screenState: MutableLiveData<ScreenState> = MutableLiveData()
    val screenState: LiveData<ScreenState> = _screenState

    init {
        fetchOrder()
    }

    private fun fetchOrder() {
        productsInteractor
            .getOrderProducts(order.id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _screenState.value = ScreenState.LOADING }
            .subscribe(
                {
                    _screenState.value = ScreenState.LOADED
                    _productModels.value = it
                },
                {
                    _screenState.value = ScreenState.error(it.message)
                }
            )
            .addToComposite()
    }
}