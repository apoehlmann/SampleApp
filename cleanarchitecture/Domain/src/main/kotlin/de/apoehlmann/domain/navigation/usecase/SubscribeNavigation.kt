package de.apoehlmann.domain2.navigation.usecase

import de.apoehlmann.domain.UseCase
import de.apoehlmann.domain2.navigation.model.NavigationStep
import de.apoehlmann.domain2.navigation.repository.AppNavigationRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SubscribeNavigation @Inject constructor(val appNavigationRepository: AppNavigationRepository): UseCase<NavigationStep>() {

    override fun getScheduler() = Schedulers.newThread()

    override fun buildObservable() = appNavigationRepository.listenToNavigation().doOnNext {  }

}