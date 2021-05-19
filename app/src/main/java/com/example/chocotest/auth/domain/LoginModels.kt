package com.example.chocotest.auth.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @Expose
    @SerializedName("token")
    val token: String
)