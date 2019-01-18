package de.apoehlmann.presentation.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.apoehlmann.presentation.Presenter
import de.apoehlmann.presentation.presenter.AndroidView

abstract class Activity<V: AndroidView, P: Presenter<V>>: AppCompatActivity() {

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

    abstract fun getPresenter(): P
    abstract fun createNewView(): V
}