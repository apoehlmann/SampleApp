package de.apoehlmann.sampleapp.presentation

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import de.apoehlmann.presentation.fragment.Fragment
import de.apoehlmann.presentation.presenter.AndroidPresenter
import de.apoehlmann.presentation.presenter.AndroidView
import de.apoehlmann.sampleapp.AppComponent
import de.apoehlmann.sampleapp.R
import de.apoehlmann.sampleapp.data.NfcTag
import de.apoehlmann.sampleapp.domain.SubscribeNfcTags
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.blue_fragment.*
import kotlinx.android.synthetic.main.green_fragment.*
import javax.inject.Inject
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

class BlueFragment: Fragment<ColorView, BluePresenter>() {

    @Inject
    lateinit var colorPresenter: BluePresenter

    override fun getPresenter() = colorPresenter

    override fun createNewView() = ColorView(this::changeColor, this::showInfo)

    override fun onCreate(savedInstanceState: Bundle?) {
        MainActivity.appComponent?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.blue_fragment, null)
    }

    fun changeColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view?.setBackgroundColor(context!!.getColor(R.color.my_blue))
        } else {
            view?.setBackgroundColor(context!!.resources!!.getColor(R.color.my_blue))
        }
    }

    fun showInfo(info: String) {
        this.activity?.runOnUiThread {
            latestTag.text = info
        }
    }
}

class OrangeFragment: Fragment<ColorView, OrangePresenter>() {

    @Inject
    lateinit var colorPresenter: OrangePresenter
    override fun createNewView() = ColorView(this::changeColor, this::showInfo)

    override fun getPresenter() = colorPresenter

    private var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        MainActivity.appComponent!!.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1)
        return inflater.inflate(R.layout.green_fragment, null)
    }

    override fun onResume() {
        super.onResume()
        tag_list.adapter = adapter
    }

    fun changeColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view?.setBackgroundColor(context!!.getColor(R.color.my_orange))
        } else {
            view?.setBackgroundColor(context!!.resources!!.getColor(R.color.my_orange))
        }    }

    fun showInfo(info: String) {
        activity?.runOnUiThread {
            adapter!!.add(info)
        }
    }
}