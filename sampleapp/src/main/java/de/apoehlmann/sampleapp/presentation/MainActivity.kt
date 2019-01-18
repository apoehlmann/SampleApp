package de.apoehlmann.sampleapp.presentation

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import de.apoehlmann.domain2.navigation.model.NavigationStep
import de.apoehlmann.domain2.navigation.model.Navigator
import de.apoehlmann.domain2.navigation.usecase.NavigationUseCaseFactory
import de.apoehlmann.presentation.fragment.Activity
import de.apoehlmann.presentation.modules.AppNavigationModule
import de.apoehlmann.presentation.presenter.AndroidPresenter
import de.apoehlmann.presentation.presenter.AndroidView
import de.apoehlmann.sampleapp.AppComponent
import de.apoehlmann.sampleapp.DaggerAppComponent
import de.apoehlmann.sampleapp.NfcModule
import de.apoehlmann.sampleapp.R
import de.apoehlmann.sampleapp.data.ActivityWrapper
import de.apoehlmann.sampleapp.domain.NfcUseCaseFactory
import de.apoehlmann.sampleapp.presentation.MainActivity.Companion.navigationSteps
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : MainView, Activity<MainView, MainPresenter>() {
    override fun getActivityWrapper(): ActivityWrapper {
        return ActivityWrapper(this)
    }

    override fun registerBroadcastReceiver(first: BroadcastReceiver, second: IntentFilter) {
        Timber.d("register broadcast")
        registerReceiver(first, second)
    }

    val navigationStepsData = PublishSubject.create<NavigationStep>()

    override fun getNavigationSteps(): Observable<NavigationStep> {
        return navigationStepsData
    }

    override fun getAppNavigator(): Navigator {
        if (appNavigator == null) {
            appNavigator = AppNavigator(supportFragmentManager)
        }
        return appNavigator!!
    }

    @Inject
    lateinit var mainPresenter: MainPresenter
    private var appNavigator: Navigator? = null

    override fun getPresenter() = mainPresenter

    override fun createNewView(): MainView {
        return this
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                navigationStepsData.onNext(blueNavigationStep)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                navigationStepsData.onNext(orangeNavigationStep)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                .appNavigationModule(AppNavigationModule())
                .nfcModule(NfcModule(applicationContext))
                .build()
        }
        appComponent?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    companion object {
        var appComponent: AppComponent? = null
        val blueFragment = BlueFragment()
        val orangeFragment = OrangeFragment()
        val blueNavigationStep = NavigationStep.generateNewNavigationStep()
        val orangeNavigationStep = NavigationStep.generateNewNavigationStep()
        val redNavigationStep = NavigationStep.generateNewNavigationStep()
        val navigationSteps: Map<NavigationStep, Fragment> = mapOf(
            Pair(
                blueNavigationStep,
                blueFragment
            ), Pair(
                orangeNavigationStep,
                orangeFragment
            )
        )
    }
}

private class AppNavigator(val fragmentManager: FragmentManager) : Navigator {
    override fun supportNavigationStep(navigationStep: NavigationStep): Boolean {
        return MainActivity.blueNavigationStep == navigationStep || MainActivity.redNavigationStep == navigationStep ||
                MainActivity.orangeNavigationStep == navigationStep
    }

    override fun navigate(navigationStep: NavigationStep) {
        val fragment = navigationSteps[navigationStep]
        fragment?.let {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, it).commitAllowingStateLoss()
        }
    }
}