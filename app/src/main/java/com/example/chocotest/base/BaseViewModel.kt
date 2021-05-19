package com.example.chocotest.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), AsyncHelper by AsyncHelperImpl() {

    override fun onCleared() {
        clearDisposables()
        super.onCleared()
    }
}