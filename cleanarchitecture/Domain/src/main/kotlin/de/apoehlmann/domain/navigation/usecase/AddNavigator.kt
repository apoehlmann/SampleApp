package de.apoehlmann.domain2.navigation.usecase

import de.apoehlmann.domain.UseCase
import de.apoehlmann.domain2.navigation.model.Navigator
import de.apoehlmann.domain2.navigation.repository.AppNavigationRepository
import io.reactivex.schedulers.Schedulers

class AddNavigator(val appNavigationRepository: AppNavigationRepository, val navigator: Navigator): UseCase<Void>() {

    override fun getScheduler() = Schedulers.trampoline()

    override fun buildObservable() = appNavigationRepository.addNavigator(navigator)

}