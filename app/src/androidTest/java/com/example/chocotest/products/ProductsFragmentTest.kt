package com.example.chocotest.products

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
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.products.domain.ProductResponse
import com.example.chocotest.products.domain.ProductsInteractor
import com.example.chocotest.products.presentaion.ProductsFragment
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class ProductsFragmentTest {

    @Inject
    lateinit var interactor: ProductsInteractor

    @Inject
    lateinit var loginRepository: LoginRepository

    private val productsList = listOf(
        ProductResponse(
            "id12",
            "carrot",
            "carrot is vegetable",
            123,
            "id12.com"
        ),
        ProductResponse(
            "id13",
            "apple",
            "apple is fruit",
            124,
            "id13.com"
        )
    )

    @Before
    fun setUp() {
        ApplicationProvider.getApplicationContext<TestApplication>().getComponent().inject(this)
    }

    @After
    fun clean() {
        Mockito.reset(interactor)
        Mockito.reset(loginRepository)
    }

    @Test
    fun testProductsFragmentSuccessLoad() {
        `when`(interactor.loadProducts("token"))
            .thenReturn(Observable.just(productsList))

        `when`(loginRepository.getToken())
            .thenReturn("token")

        ActivityScenario.launch(TestFragmentActivity::class.java).use { scenario ->
            scenario.onActivity {
                it.supportFragmentManager.commit {
                    replace(android.R.id.content, ProductsFragment::class.java, null)
                }
            }

            onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testProductsFragmentErrorLoad() {
        `when`(interactor.loadProducts("token"))
            .thenReturn(Observable.error(RuntimeException("error")))

        `when`(loginRepository.getToken())
            .thenReturn("token")

        ActivityScenario.launch(TestFragmentActivity::class.java).use { scenario ->
            scenario.onActivity {
                it.supportFragmentManager.commit {
                    replace(android.R.id.content, ProductsFragment::class.java, null)
                }
            }

            onView(withText("error"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
        }
    }
}
