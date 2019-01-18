package de.apoehlmann.repository.navigation

import de.apoehlmann.domain2.navigation.model.NavigationStep
import de.apoehlmann.domain2.navigation.model.Navigator
import de.apoehlmann.domain2.navigation.repository.AppNavigationRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.lang.NullPointerException
import java.security.InvalidParameterException

class AppNavigationConnectedRepository : AppNavigationRepository {

    private val listOfNavigators: MutableList<Navigator> = mutableListOf()
    private val navigationStepsData = BehaviorSubject.create<NavigationStep>()

    override fun navigate(navigationStep: NavigationStep): Observable<Void> {
        val selectedNavigator = listOfNavigators.filter { it.supportNavigationStep(navigationStep) }.firstOrNull()
        selectedNavigator?.navigate(navigationStep)
        if (selectedNavigator == null) {
            return Observable.error(NullPointerException("Found no navigator for navigationstep ${navigationStep.key}"))
        } else {
            navigationStepsData.onNext(navigationStep)
            return Observable.empty()
        }
    }

    override fun listenToNavigation(): Observable<NavigationStep> = navigationStepsData

    override fun addNavigator(navigator: Navigator): Observable<Void> {
        if (listOfNavigators.contains(navigator)) {
            return Observable.error(InvalidParameterException("Found duplicate navigator instance"))
        } else {
            listOfNavigators.add(navigator)
            return Observable.empty()
        }
    }

    override fun removeNavigator(navigator: Navigator): Observable<Void> {
        if (listOfNavigators.remove(navigator)) {
            return Observable.empty()
        } else {
            return Observable.error(InvalidParameterException("Found no navigator instance"))
        }
    }

}