package com.example.chocotest.products.presentaion

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.example.chocotest.Application
import com.example.chocotest.R
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.base.OnProductSelectListener
import com.example.chocotest.base.ScreenState
import com.example.chocotest.base.showAlert
import com.example.chocotest.databinding.FragmentProductsBinding
import com.example.chocotest.main.presentation.MainViewModel
import com.example.chocotest.main.presentation.MainViewModelFactory
import com.example.chocotest.products.domain.ProductResponse
import com.example.chocotest.products.domain.ProductsInteractor
import javax.inject.Inject

class ProductsFragment : Fragment(), ActionMode.Callback {

    private lateinit var binding: FragmentProductsBinding

    @Inject
    lateinit var productsInteractor: ProductsInteractor

    @Inject
    lateinit var loginRepository: LoginRepository

    private lateinit var adapter: ProductsRecyclerViewAdapter

    private val productsViewModel by viewModels<ProductsViewModel> {
        ProductsViewModelFactory(
            productsInteractor,
            loginRepository
        )
    }

    private val mainViewModel by viewModels<MainViewModel>(
        ownerProducer = { requireActivity() },
        factoryProducer = { MainViewModelFactory() }
    )

    private var actionMode: ActionMode? = null
    private var tracker: SelectionTracker<ProductResponse>? = null

    override fun onAttach(context: Context) {
        (context.applicationContext as Application).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = productsViewModel
        binding.lifecycleOwner = this

        adapter = ProductsRecyclerViewAdapter(
            onProductSelectListener = object : OnProductSelectListener {
                override fun onAddToCart(response: ProductResponse) {
                    mainViewModel.addToShoppingCart(response)
                }

                override fun onRemoveFromCart(response: ProductResponse) {
                    mainViewModel.removeFromShoppingCart(response)
                }
            }
        )
        binding.recyclerView.adapter = adapter
        tracker = SelectionTracker.Builder(
            getString(R.string.selectionId),
            binding.recyclerView,
            ProductKeyProvider(adapter),
            MyItemDetailsLookup(binding.recyclerView),
            StorageStrategy.createParcelableStorage(ProductResponse::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<ProductResponse?>() {
                override fun onSelectionChanged() {
                    if (tracker?.selection?.size() ?: 0 > 0) {
                        if (actionMode == null) {
                            actionMode =
                                (requireActivity() as AppCompatActivity).startSupportActionMode(this@ProductsFragment)
                        }
                        actionMode?.title = tracker?.selection?.size().toString()
                    } else {
                        actionMode?.finish()
                        actionMode = null
                    }
                }
            })

        adapter.tracker = tracker

        initObservers()
    }

    override fun onDestroyView() {
        actionMode?.finish()
        actionMode = null
        super.onDestroyView()
    }

    private fun initObservers() {
        productsViewModel.screenState.observe(viewLifecycleOwner, {
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
        productsViewModel.childModels.observe(viewLifecycleOwner, {
            adapter.submitList(it.takeIf(Collection<ProductResponse>::isNotEmpty))
        })
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.products_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.makeOrder -> {
                nameOrder()
                return true
            }
        }
        return false
    }

    private fun nameOrder() {
        val edittext = EditText(requireContext())
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.nameTheOrder))
            setView(edittext)
            setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                mainViewModel.makeOrder(edittext.text.toString())
                actionMode?.finish()
                dialog.dismiss()
            }
            show()
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        tracker?.clearSelection()
        this.actionMode = null
    }
}
