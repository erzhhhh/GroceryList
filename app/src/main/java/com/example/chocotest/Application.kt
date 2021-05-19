package com.example.chocotest

import androidx.multidex.MultiDexApplication

private const val BASE_URL = "https://qo7vrra66k.execute-api.eu-west-1.amazonaws.com/"

open class Application : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    open fun getComponent(): AppComponent = appComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = createComponent()
    }

    open fun createComponent(): AppComponent =
        DaggerAppComponent
            .builder()
            .appModule(
                AppModule(
                    this,
                    BASE_URL
                )
            )
            .build()
}