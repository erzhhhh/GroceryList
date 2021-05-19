package com.example.chocotest.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chocotest.utils.CustomRunner
import com.example.chocotest.auth.data.LoginRepositoryImpl
import com.example.chocotest.auth.domain.LoginRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@RunWith(CustomRunner::class)
class LoginRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: LoginRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = LoginRepositoryImpl()
    }

    @Test
    fun assertSuccessLogin() {
        repository.setToken("token")

        assertThat(repository.getToken(), `is`("token"))
    }
}