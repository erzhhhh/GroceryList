package com.example.chocotest.base

import io.reactivex.disposables.Disposable

interface AsyncHelper {

    fun withDisposable(disposable: Disposable?): Disposable?

    fun Disposable?.addToComposite(): Disposable? = withDisposable(this)

    fun clearDisposables()
}