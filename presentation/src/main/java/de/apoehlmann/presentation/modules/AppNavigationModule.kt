package de.apoehlmann.presentation.modules

import dagger.Module
import dagger.Provides
import de.apoehlmann.domain2.navigation.repository.AppNavigationRepository
import de.apoehlmann.repository.navigation.AppNavigationConnectedRepository
import javax.inject.Singleton

@Module
class AppNavigationModule {

    @Singleton
    @Provides
    fun providesAppNavigationRepository():AppNavigationRepository {
        return AppNavigationConnectedRepository()
    }
}
