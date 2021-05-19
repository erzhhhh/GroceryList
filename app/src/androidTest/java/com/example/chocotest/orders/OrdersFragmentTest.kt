package com.example.chocotest.orders

import androidx.fragment.app.commit
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.chocotest.R
import com.example.chocotest.TestApplication
import com.example.chocotest.TestFragmentActivity
import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.orders.domain.OrdersInteractor
import com.example.chocotest.orders.presentaion.OrdersFragment
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class OrdersFragmentTest {

    @Inject
    lateinit var interactor: OrdersInteractor

    private val ordersList = listOf(
        OrderModel(
            1,
            "Month supply",
            1400.0,
        ),
        OrderModel(
            2,
            "Week supply",
            400.0,
        )
    )

    @Before
    fun setUp() {
        ApplicationProvider.getApplicationContext<TestApplication>().getComponent().inject(this)
    }

    @After
    fun clean() {
        Mockito.reset(interactor)
    }

    @Test
    fun testProductsFragmentSuccessLoad() {
        `when`(interactor.fetchOrders())
            .thenReturn(Observable.just(ordersList))

        ActivityScenario.launch(TestFragmentActivity::class.java).use { scenario ->
            scenario.onActivity {
                it.supportFragmentManager.commit {
                    replace(android.R.id.content, OrdersFragment::class.java, null)
                }
            }

            onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testProductsFragmentErrorLoad() {
        `when`(interactor.fetchOrders())
            .thenReturn(Observable.error(RuntimeException("error")))

        ActivityScenario.launch(TestFragmentActivity::class.java).use { scenario ->
            scenario.onActivity {
                it.supportFragmentManager.commit {
                    replace(android.R.id.content, OrdersFragment::class.java, null)
                }
            }

            onView(withText("error"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
        }
    }
}
