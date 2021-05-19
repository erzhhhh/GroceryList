package com.example.chocotest.orders.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chocotest.base.BaseViewModel
import com.example.chocotest.base.ScreenState
import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.orders.domain.OrdersInteractor
import com.example.chocotest.products.domain.ProductResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OrdersViewModel @Inject constructor(private val ordersInteractor: OrdersInteractor) :
    BaseViewModel() {

    private var _childModels = MutableLiveData<List<OrderModel>>()
    val childModels: LiveData<List<OrderModel>> = _childModels

    private val _screenState: MutableLiveData<ScreenState> = MutableLiveData()
    val screenState: LiveData<ScreenState> = _screenState

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        ordersInteractor
            .fetchOrders()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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

    fun createNewOrder(orderName: String, shoppingList: List<ProductResponse>) {
        ordersInteractor
            .createOrder(orderName, shoppingList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({},
                {
                    _screenState.value = ScreenState.error(it.message)
                }
            )
            .addToComposite()
    }
}