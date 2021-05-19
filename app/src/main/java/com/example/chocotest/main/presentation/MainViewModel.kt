package com.example.chocotest.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chocotest.base.BaseViewModel
import com.example.chocotest.products.domain.ProductResponse

class MainViewModel : BaseViewModel() {

    private var _makeOrder = MutableLiveData<String>()
    val makeOrder: LiveData<String> = _makeOrder

    val shoppingList = mutableListOf<ProductResponse>()

    fun makeOrder(orderName: String) {
        _makeOrder.postValue(orderName)
    }

    fun addToShoppingCart(response: ProductResponse) {
        shoppingList.add(response)
    }

    fun removeFromShoppingCart(response: ProductResponse) {
        shoppingList.remove(response)
    }
}