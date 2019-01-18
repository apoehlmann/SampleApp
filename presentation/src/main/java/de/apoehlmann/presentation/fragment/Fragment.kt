package de.apoehlmann.presentation.fragment

import android.os.Bundle
import de.apoehlmann.presentation.presenter.AndroidPresenter
import de.apoehlmann.presentation.presenter.AndroidView

abstract class Fragment<V: AndroidView, P: AndroidPresenter<V>>: android.support.v4.app.Fragment() {

    abstract fun getPresenter(): P
    abstract fun createNewView(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresenter().onCreate()
    }

    override fun onResume() {
        super.onResume()
        getPresenter().setNewView(createNewView())
        getPresenter().onResume()
    }

    override fun onPause() {
        getPresenter().onPrePause()
        getPresenter().onPause()
        super.onPause()
    }

    override fun onDestroy() {
        getPresenter().onPreDestroy()
        getPresenter().onDestroy()
        super.onDestroy()
    }
}
