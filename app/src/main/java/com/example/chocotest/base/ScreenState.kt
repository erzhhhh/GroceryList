package com.example.chocotest.base

@Suppress("DataClassPrivateConstructor")
data class ScreenState private constructor(
    val status: Status,
    val message: String? = null
) {

    enum class Status {
        RUNNING,
        SUCCESS_LOADED,
        FAILED
    }

    companion object {

        val LOADED = ScreenState(Status.SUCCESS_LOADED)
        val LOADING = ScreenState(Status.RUNNING)
        fun error(msg: String?) = ScreenState(Status.FAILED, msg)
    }
}