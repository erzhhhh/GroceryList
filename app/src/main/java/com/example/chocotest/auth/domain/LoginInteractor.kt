package com.example.chocotest.auth.domain

import io.reactivex.Observable

interface LoginInteractor {

    fun login(login: String, password: String): Observable<LoginResponse>

}
