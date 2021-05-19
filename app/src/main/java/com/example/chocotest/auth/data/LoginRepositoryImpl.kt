package com.example.chocotest.auth.data

import androidx.annotation.VisibleForTesting
import com.example.chocotest.auth.domain.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {

    @VisibleForTesting
    private var token = ""

    override fun getToken(): String {
        return token
    }

    override fun setToken(token: String) {
        this.token = token
    }
}