package com.example.chocotest.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AsyncHelperImpl : AsyncHelper {

    private val disposable = CompositeDisposable()

    override fun withDisposable(disposable: Disposable?): Disposable? {
        return disposable?.also { d -> this.disposable.add(d) }
    }

    override fun clearDisposables() {
        disposable.clear()
    }
}