package com.example.chocotest.main.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.chocotest.R
import com.example.chocotest.auth.presentaion.LoginActivity
import com.example.chocotest.databinding.ActivityMainBinding
import com.example.chocotest.db.AppDatabase
import com.example.chocotest.orders.presentaion.OrdersTabFragment
import com.example.chocotest.products.presentaion.ProductsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.concurrent.thread

private const val productFragmentTag = "1"
private const val ordersFragmentTag = "2"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory()
    }

    private val activeFragment: Fragment?
        get() {
            return supportFragmentManager.primaryNavigationFragment
        }

    private val productsFragment: Fragment
        get() {
            return supportFragmentManager.findFragmentByTag(productFragmentTag)!!
        }

    private val ordersTabFragment: Fragment
        get() {
            return supportFragmentManager.findFragmentByTag(ordersFragmentTag)!!
        }

    private val navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.products -> {
                    showProductsFragment()
                    true
                }
                R.id.orders -> {
                    showOrdersTabFragment()
                    true
                }
                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            setFragments()
        }
        initListeners()
        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logOut -> {
                logout()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun logout() {
        thread {
            AppDatabase.getInstance(this).clearAllTables()
        }
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setFragments() {
        val ordersTabFragment = OrdersTabFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container, ordersTabFragment, ordersFragmentTag)
            .hide(ordersTabFragment)
            .commit()

        val productsFragment = ProductsFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container, productsFragment, productFragmentTag)
            .setPrimaryNavigationFragment(productsFragment)
            .commit()
    }

    private fun initListeners() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    private fun initObservers() {
        viewModel.makeOrder.observe(this, {
            if (it.isNotEmpty()) {
                binding.bottomNavigation.setOnNavigationItemSelectedListener(null)
                binding.bottomNavigation.selectedItemId = R.id.orders
                showNewOrdersFragment()
                binding.bottomNavigation.setOnNavigationItemSelectedListener(
                    navigationItemSelectedListener
                )
            }
        })
    }

    private fun showProductsFragment() {
        val active = activeFragment
        if (activeFragment !is ProductsFragment && active != null) {
            supportFragmentManager
                .beginTransaction()
                .hide(active)
                .show(productsFragment)
                .setPrimaryNavigationFragment(productsFragment)
                .commit()
        }
    }

    private fun showOrdersTabFragment() {
        val active = activeFragment
        if (active !is OrdersTabFragment && active != null) {
            supportFragmentManager
                .beginTransaction()
                .hide(active)
                .show(ordersTabFragment)
                .setPrimaryNavigationFragment(ordersTabFragment)
                .commit()
        } else if (active is OrdersTabFragment) {
            active.clearStack()
        }
    }

    private fun showNewOrdersFragment() {
        val active = activeFragment
        if (active != null) {
            val newOrdersTabFragment = OrdersTabFragment()
            supportFragmentManager
                .beginTransaction()
                .hide(active)
                .remove(ordersTabFragment)
                .add(R.id.main_container, newOrdersTabFragment, ordersFragmentTag)
                .show(newOrdersTabFragment)
                .setPrimaryNavigationFragment(newOrdersTabFragment)
                .commit()
        }
    }
}