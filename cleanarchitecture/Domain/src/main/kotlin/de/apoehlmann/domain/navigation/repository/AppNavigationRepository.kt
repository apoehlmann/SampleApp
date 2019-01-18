package de.apoehlmann.domain2.navigation.repository

import de.apoehlmann.domain2.navigation.model.NavigationStep
import de.apoehlmann.domain2.navigation.model.Navigator
import io.reactivex.Observable

interface AppNavigationRepository {
    fun navigate(navigationStep: NavigationStep): Observable<Void>
    fun listenToNavigation(): Observable<NavigationStep>
    fun addNavigator(navigator: Navigator): Observable<Void>
    fun removeNavigator(navigator: Navigator): Observable<Void>
}

