package de.apoehlmann.domain2.navigation.usecase

import de.apoehlmann.domain2.navigation.model.NavigationStep
import de.apoehlmann.domain2.navigation.model.Navigator
import de.apoehlmann.domain2.navigation.repository.AppNavigationRepository
import javax.inject.Inject

class NavigationUseCaseFactory @Inject constructor(val appNavigationRepository: AppNavigationRepository) {

    fun buildNavigationUseCase(navigationStep: NavigationStep) = Navigate(appNavigationRepository, navigationStep)
    fun buildAddNewNavigatorUseCase(navigator: Navigator) = AddNavigator(appNavigationRepository, navigator)
    fun buildRemoveNavigator(navigator: Navigator) = RemoveNavigator(appNavigationRepository, navigator)
}