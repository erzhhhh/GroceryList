package com.example.chocotest.auth.domain

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

private const val LOGIN = "choco/login"

interface LoginApi {

    @POST(LOGIN)
    fun login(@Body loginRequest: LoginRequest): Observable<LoginResponse>
}