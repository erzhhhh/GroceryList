package com.example.chocotest.auth.di

import com.example.chocotest.auth.data.LoginInteractorImpl
import com.example.chocotest.auth.domain.LoginApi
import com.example.chocotest.auth.domain.LoginInteractor
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class LoginModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    fun provideLoginInteractor(service: LoginApi): LoginInteractor =
        LoginInteractorImpl(service)
}