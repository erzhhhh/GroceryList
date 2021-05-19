package com.example.chocotest.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chocotest.utils.CustomRunner
import com.example.chocotest.auth.data.LoginInteractorImpl
import com.example.chocotest.auth.domain.LoginApi
import com.example.chocotest.auth.domain.LoginInteractor
import com.example.chocotest.auth.domain.LoginRequest
import com.example.chocotest.auth.domain.LoginResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(CustomRunner::class)
class LoginInteractorTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var interactor: LoginInteractor

    @Mock
    private lateinit var loginApi: LoginApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = LoginInteractorImpl(loginApi)
    }

    @Test
    fun assertSuccessLogin() {
        `when`(loginApi.login(LoginRequest("login", "password")))
            .thenReturn(Observable.just(LoginResponse("token")))

        `when`(interactor.login("login", "password"))
            .thenReturn(Observable.just(LoginResponse("token")))

        interactor.login("login", "password").test()

        verify(loginApi, times(1)).login(LoginRequest("login", "password"))
    }
}