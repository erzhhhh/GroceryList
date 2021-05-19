package com.example.chocotest.orders.presentaion

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chocotest.Application
import com.example.chocotest.base.OnOrderClickListener
import com.example.chocotest.base.ScreenState
import com.example.chocotest.base.showAlert
import com.example.chocotest.databinding.FragmentOrdersBinding
import com.example.chocotest.main.presentation.MainViewModel
import com.example.chocotest.main.presentation.MainViewModelFactory
import com.example.chocotest.orderDetails.OrderDetailFragment
import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.orders.domain.OrdersInteractor
import javax.inject.Inject


class OrdersFragment : Fragment() {

    @Inject
    lateinit var ordersInteractor: OrdersInteractor

    private lateinit var binding: FragmentOrdersBinding

    private val ordersViewModel by viewModels<OrdersViewModel> {
        OrdersViewModelFactory(ordersInteractor)
    }

    private val mainViewModel by viewModels<MainViewModel>(
        ownerProducer = { requireActivity() },
        factoryProducer = { MainViewModelFactory() }
    )

    private lateinit var adapter: OrdersRecyclerViewAdapter

    override fun onAttach(context: Context) {
        (context.applicationContext as Application).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = ordersViewModel
        binding.lifecycleOwner = this

        adapter = OrdersRecyclerViewAdapter(
            onOrderClickListener = object : OnOrderClickListener<OrderModel> {
                override fun onOrderClick(order: OrderModel) {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(id, OrderDetailFragment.newInstance(order), "2")
                        .addToBackStack(null)
                        .commit()
                }
            })
        binding.recyclerView.adapter = adapter

        initObservers()
    }

    private fun initObservers() {
        ordersViewModel.childModels.observe(viewLifecycleOwner, {
            adapter.submitList(it.takeIf(Collection<OrderModel>::isNotEmpty))
        })
        ordersViewModel.screenState.observe(viewLifecycleOwner, {
            when (it.status) {
                ScreenState.Status.RUNNING -> {
                    binding.progressContainer.isVisible = true
                    binding.recyclerView.isVisible = false
                }
                ScreenState.Status.SUCCESS_LOADED -> {
                    binding.progressContainer.isVisible = false
                    binding.recyclerView.isVisible = true
                }
                ScreenState.Status.FAILED -> {
                    binding.progressContainer.isVisible = false
                    binding.recyclerView.isVisible = false
                    showAlert(it.message.orEmpty(), requireContext())
                }
            }
        })
        mainViewModel.makeOrder.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                ordersViewModel.createNewOrder(it, mainViewModel.shoppingList)
            }
        })
    }
}