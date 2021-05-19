package com.example.chocotest.orderDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.example.chocotest.Application
import com.example.chocotest.base.ScreenState
import com.example.chocotest.base.showAlert
import com.example.chocotest.databinding.FragmentOrderDetailBinding
import com.example.chocotest.orders.domain.OrderModel
import com.example.chocotest.products.domain.ProductEntity
import com.example.chocotest.products.domain.ProductsInteractor
import javax.inject.Inject

class OrderDetailFragment : Fragment() {

    @Inject
    lateinit var productsInteractor: ProductsInteractor

    private lateinit var binding: FragmentOrderDetailBinding

    private val ordersViewModel by viewModels<OrderDetailViewModel> {
        OrderViewModelFactory(productsInteractor, requireArguments()[KEY_ORDER] as OrderModel)
    }

    private lateinit var adapter: ConcatAdapter
    private lateinit var orderAdapter: OrderDetailsRecyclerViewAdapter
    private lateinit var productsAdapter: ProductsDetailsRecyclerViewAdapter

    override fun onAttach(context: Context) {
        (context.applicationContext as Application).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = ordersViewModel
        binding.lifecycleOwner = this

        orderAdapter = OrderDetailsRecyclerViewAdapter()
        productsAdapter = ProductsDetailsRecyclerViewAdapter()
        adapter = ConcatAdapter(orderAdapter, productsAdapter)
        binding.recyclerView.adapter = adapter

        initObservers()
    }

    private fun initObservers() {
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
        ordersViewModel.orderModels.observe(viewLifecycleOwner, {
            orderAdapter.submitList(it.takeIf(Collection<OrderModel>::isNotEmpty))
        })
        ordersViewModel.productModels.observe(viewLifecycleOwner, {
            productsAdapter.submitList(it.takeIf(Collection<ProductEntity>::isNotEmpty))
        })
    }

    companion object {

        private const val KEY_ORDER = "key_order"

        fun newInstance(order: OrderModel): OrderDetailFragment {
            val args = bundleOf(KEY_ORDER to order)
            val fragment = OrderDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}