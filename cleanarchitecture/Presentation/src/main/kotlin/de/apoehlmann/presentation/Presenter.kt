package de.apoehlmann.presentation

import io.reactivex.disposables.CompositeDisposable

abstract class Presenter<V: View> {

    private var view: V? = null
    val compositeDisposable = CompositeDisposable()

    abstract fun onCreate()

    abstract fun onResume()

    abstract fun onPrePause()

    abstract fun onPreDestroy()

    fun onPause() {
        view = null
        compositeDisposable.clear()
    }

    fun onDestroy() {
    }

    fun setNewView(view: V) {
        this.view = view
    }

    fun getView() = view
}

interface View