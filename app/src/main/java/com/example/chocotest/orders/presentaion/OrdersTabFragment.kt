package com.example.chocotest.orders.presentaion

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.chocotest.R
import com.example.chocotest.orders.presentaion.OrdersFragment

class OrdersTabFragment : Fragment(R.layout.fragment_orders_tab) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.commit {
                val ordersFragment = OrdersFragment()
                replace(R.id.ordersContent, ordersFragment)
                setPrimaryNavigationFragment(ordersFragment)
            }
        }
    }

    fun clearStack() {
        if (isStateSaved) {
            return
        }
        childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}