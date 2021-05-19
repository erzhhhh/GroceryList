package com.example.chocotest.auth.data

import com.example.chocotest.auth.domain.LoginApi
import com.example.chocotest.auth.domain.LoginInteractor
import com.example.chocotest.auth.domain.LoginRequest
import com.example.chocotest.auth.domain.LoginResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginInteractorImpl(private val loginApi: LoginApi) : LoginInteractor {

    override fun login(login: String, password: String): Observable<LoginResponse> {
        return loginApi.login(LoginRequest(login, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
