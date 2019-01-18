package de.apoehlmann.sampleapp.presentation

import android.content.BroadcastReceiver
import android.content.IntentFilter
import de.apoehlmann.domain2.navigation.model.NavigationStep
import de.apoehlmann.domain2.navigation.model.Navigator
import de.apoehlmann.domain2.navigation.usecase.NavigationUseCaseFactory
import de.apoehlmann.presentation.presenter.AndroidPresenter
import de.apoehlmann.presentation.presenter.AndroidView
import de.apoehlmann.sampleapp.data.ActivityWrapper
import de.apoehlmann.sampleapp.data.NfcTag
import de.apoehlmann.sampleapp.domain.NfcUseCaseFactory
import de.apoehlmann.sampleapp.domain.SubscribeNfcTags
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import javax.inject.Inject
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

class ColorView(
    val changeColorFunction: KFunction0<Unit>,
    val showInfoFunction: KFunction1<@ParameterName(name = "info") String, Unit>
) :
    AndroidView {

    fun changeColor() {
        changeColorFunction()
    }

    fun showInfo(info: String) {
        showInfoFunction(info)
    }

}

class OrangePresenter @Inject constructor(val subscribeNfcTags: SubscribeNfcTags) : AndroidPresenter<ColorView>() {

    override fun onCreate() {
    }

    override fun onResume() {
        getView()?.changeColor()
        compositeDisposable.add(subscribeNfcTags.execute(Consumer {
            getView()?.showInfo(NfcTagMapper.transform(it))
        }))
    }

    override fun onPrePause() {
    }

    override fun onPreDestroy() {
    }

}

class BluePresenter @Inject constructor(val subscribeNfcTags: SubscribeNfcTags) : AndroidPresenter<ColorView>() {

    override fun onCreate() {
    }

    override fun onResume() {
        getView()?.changeColor()
        compositeDisposable.add(subscribeNfcTags.execute(Consumer { getView()?.showInfo(NfcTagMapper.transform(it)) }))
    }

    override fun onPrePause() {
    }

    override fun onPreDestroy() {
    }

}

class NfcTagMapper {
    companion object {
        const val EMPTY_ID = "leeres Tag"
        fun transform(tag: NfcTag): String {
            val idString = tag.id.contentToString()
            val techString = tag.techList.map { it.substring(it.lastIndexOf('.') + 1) }.joinToString { "$it " }
            val returnText = "$idString\n$techString"
            return if (returnText.contains("[]")) { EMPTY_ID } else { returnText }
        }
    }
}


class MainPresenter @Inject
constructor(val navigationUseCaseFactory: NavigationUseCaseFactory, val nfcUseCaseFactory: NfcUseCaseFactory) :
    AndroidPresenter<MainView>() {

    override fun onCreate() {
    }

    override fun onResume() {
        compositeDisposable.add(
            nfcUseCaseFactory.createStartNfcListening(getView()!!.getActivityWrapper()).execute(
                Consumer { })
        )
        getView()?.getAppNavigator()?.let {
            compositeDisposable.addAll(
                navigationUseCaseFactory.buildAddNewNavigatorUseCase(it).execute(Consumer { }),
                navigationUseCaseFactory.buildNavigationUseCase(MainActivity.blueNavigationStep).execute(Consumer { })
            )
        }

        compositeDisposable.add(getView()!!.getNavigationSteps().subscribe({
            navigationUseCaseFactory.buildNavigationUseCase(it).execute(Consumer { })
        }))
    }

    override fun onPrePause() {
        getView()?.getAppNavigator()?.let {
            compositeDisposable.add(
                navigationUseCaseFactory.buildRemoveNavigator(it).execute(
                    Consumer { })
            )
        }

        getView()?.getActivityWrapper()?.let {
            compositeDisposable.add(
                nfcUseCaseFactory.createStopNfcListening(it).execute(
                    Consumer { })
            )
        }
    }

    override fun onPreDestroy() {
    }

}

interface MainView : AndroidView {
    fun getAppNavigator(): Navigator
    fun getNavigationSteps(): Observable<NavigationStep>
    fun registerBroadcastReceiver(first: BroadcastReceiver, second: IntentFilter)
    fun getActivityWrapper(): ActivityWrapper

}