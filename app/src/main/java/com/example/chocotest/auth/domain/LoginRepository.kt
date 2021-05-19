package com.example.chocotest.auth.domain

interface LoginRepository {

    fun setToken(token: String)

    fun getToken(): String
}