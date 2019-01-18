package de.apoehlmann.presentation.presenter

import de.apoehlmann.presentation.Presenter
import de.apoehlmann.presentation.View

abstract class AndroidPresenter<V: AndroidView>: Presenter<V>() {

}

interface AndroidView: View

