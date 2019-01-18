package de.apoehlmann.domain2.navigation.usecase

import de.apoehlmann.domain.UseCase
import de.apoehlmann.domain2.navigation.model.NavigationStep
import de.apoehlmann.domain2.navigation.repository.AppNavigationRepository
import io.reactivex.schedulers.Schedulers

class Navigate(val appNavigationRepository: AppNavigationRepository, val navigationStep: NavigationStep): UseCase<Void>() {

    override fun getScheduler() = Schedulers.single()

    override fun buildObservable() = appNavigationRepository.navigate(navigationStep)

}