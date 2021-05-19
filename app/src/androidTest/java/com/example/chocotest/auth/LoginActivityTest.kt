package com.example.chocotest.auth

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.chocotest.R
import com.example.chocotest.TestApplication
import com.example.chocotest.auth.domain.LoginInteractor
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.auth.domain.LoginResponse
import com.example.chocotest.auth.presentaion.LoginActivity
import com.example.chocotest.orders.domain.OrdersInteractor
import com.example.chocotest.products.domain.ProductsInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Inject
    lateinit var loginInteractor: LoginInteractor

    @Inject
    lateinit var productsInteractor: ProductsInteractor

    @Inject
    lateinit var ordersInteractor: OrdersInteractor

    @Inject
    lateinit var loginRepository: LoginRepository

    private val loginResponse = LoginResponse("token")

    @Before
    fun setUp() {
        ApplicationProvider.getApplicationContext<TestApplication>().getComponent().inject(this)
    }

    @After
    fun clean() {
        Mockito.reset(loginInteractor)
        Mockito.reset(loginRepository)
    }

    @Test
    fun testLoginActivityLoad() {
        ActivityScenario.launch(LoginActivity::class.java).use { _ ->
            onView(withId(R.id.login)).check(matches(isDisplayed()))
            onView(withId(R.id.password)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testLoginActivityClickLogin() {
        `when`(loginInteractor.login("user@choco.com", "chocorian"))
            .thenReturn(
                Observable.just(loginResponse)
                    .delay(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
            )

        `when`(productsInteractor.loadProducts("token"))
            .thenReturn(Observable.just(emptyList()))

        `when`(loginRepository.getToken())
            .thenReturn("token")

        `when`(ordersInteractor.fetchOrders())
            .thenReturn(Observable.just(emptyList()))

        ActivityScenario.launch(LoginActivity::class.java).use {
            onView(withId(R.id.loginEditText)).perform(typeText("user@choco.com"))
            onView(withId(R.id.passwordEditText)).perform(typeText("chocorian"))
            onView(withId(R.id.proceedButton)).perform(click())
            onView(withId(R.id.progressContainer)).check(matches(isDisplayed()))
        }
    }
}
