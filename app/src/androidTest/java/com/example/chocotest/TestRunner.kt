package com.example.chocotest

import android.app.Application
import android.content.Context

@Suppress("unused")
class TestRunner : androidx.test.runner.AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application? {
        return newApplication(TestApplication::class.java, context)
    }
}