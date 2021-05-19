package com.example.chocotest.auth.presentaion

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.chocotest.Application
import com.example.chocotest.main.presentation.MainActivity
import com.example.chocotest.R
import com.example.chocotest.auth.domain.LoginInteractor
import com.example.chocotest.auth.domain.LoginRepository
import com.example.chocotest.base.ScreenState
import com.example.chocotest.base.showAlert
import com.example.chocotest.databinding.ActivityLoginBinding
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginInteractor: LoginInteractor

    @Inject
    lateinit var loginRepository: LoginRepository

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(
            loginInteractor,
            loginRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Application).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.login.requestFocus()
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.screenState.observe(this, {
            when (it.status) {
                ScreenState.Status.RUNNING -> {
                    binding.progressContainer.isVisible = true
                    binding.loginContainer.isVisible = false
                }
                ScreenState.Status.SUCCESS_LOADED -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                ScreenState.Status.FAILED -> {
                    binding.progressContainer.isVisible = false
                    binding.loginContainer.isVisible = true
                    showAlert(it.message.orEmpty(), this)
                }
            }
        })
    }

    private fun initListeners() {
        binding.proceedButton.setOnClickListener {
            viewModel.login(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }
}