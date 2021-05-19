package com.example.chocotest.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.chocotest.utils.CustomRunner
import com.example.chocotest.auth.domain.LoginInteractor
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.auth.domain.LoginResponse
import com.example.chocotest.auth.presentaion.LoginViewModel
import com.example.chocotest.base.ScreenState
import com.example.chocotest.utils.getOrAwaitValue
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(CustomRunner::class)
class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel

    @Mock
    private lateinit var mockObserver: Observer<ScreenState>

    @Mock
    private lateinit var interactor: LoginInteractor

    @Mock
    private lateinit var repository: LoginRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = LoginViewModel(interactor, repository)
    }

    @Test
    fun assertSuccessLogin() {
        `when`(interactor.login("login", "password"))
            .thenReturn(Observable.just(LoginResponse("token")))

        viewModel.login("login", "password")
        viewModel.screenState.observeForever(mockObserver)

        val value = viewModel.screenState.getOrAwaitValue()
        assertThat(value, `is`(ScreenState.LOADED))
    }

    @Test
    fun assertErrorLogin() {
        `when`(interactor.login("login", "password"))
            .thenReturn(Observable.error(RuntimeException("error")))

        viewModel.login("login", "password")
        viewModel.screenState.observeForever(mockObserver)

        val value = viewModel.screenState.getOrAwaitValue()
        assertThat(value, `is`(ScreenState.error("error")))
    }
}