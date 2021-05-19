package com.example.chocotest.orderDetails

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
import com.example.chocotest.products.domain.ProductEntity
import com.example.chocotest.products.domain.ProductsInteractor
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class OrderDetailsFragmentTest {

    @Inject
    lateinit var interactor: ProductsInteractor

    private val productEntitiesList = listOf(
        ProductEntity(
            1,
            "id12",
            "carrot",
            "carrot is vegetable",
            123.0,
            "id12.com",
            false
        ),
        ProductEntity(
            2,
            "id13",
            "apple",
            "apple is fruit",
            124.0,
            "id13.com",
            false
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
    fun testProductFragmentSuccessLoad() {
        `when`(interactor.getOrderProducts(1))
            .thenReturn(Single.just(productEntitiesList))

        ActivityScenario.launch(TestFragmentActivity::class.java).use { scenario ->
            scenario.onActivity {
                it.supportFragmentManager.commit {
                    replace(
                        android.R.id.content,
                        OrderDetailFragment.newInstance(OrderModel(1, "orderName", 123.0))
                    )
                }
            }

            onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testProductFragmentErrorLoad() {
        `when`(interactor.getOrderProducts(1))
            .thenReturn(Single.error(RuntimeException("error")))

        ActivityScenario.launch(TestFragmentActivity::class.java).use { scenario ->
            scenario.onActivity {
                it.supportFragmentManager.commit {
                    replace(
                        android.R.id.content,
                        OrderDetailFragment.newInstance(OrderModel(1, "orderName", 123.0)),
                        null
                    )
                }
            }

            onView(withText("error"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
        }
    }
}
