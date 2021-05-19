package com.example.chocotest.auth.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chocotest.auth.domain.LoginInteractor
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.base.BaseViewModel
import com.example.chocotest.base.ScreenState
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor,
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private val _screenState: MutableLiveData<ScreenState> = MutableLiveData()
    val screenState: LiveData<ScreenState> = _screenState

    fun login(login: String, password: String) {
        loginInteractor.login(login, password)
            .doOnSubscribe { _screenState.value = ScreenState.LOADING }
            .subscribe(
                {
                    _screenState.value = ScreenState.LOADED
                    loginRepository.setToken(it.token)
                },
                {
                    _screenState.value = ScreenState.error(it.message)
                }
            )
            .addToComposite()
    }
}