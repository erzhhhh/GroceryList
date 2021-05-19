package com.example.chocotest.auth.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chocotest.auth.domain.LoginInteractor
import com.example.chocotest.auth.domain.LoginRepository

/**
 *
 * @param
 */
class LoginViewModelFactory(
    private val loginInteractor: LoginInteractor,
    private val loginRepository: LoginRepository
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginInteractor, loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}