package de.apoehlmann.sampleapp

import android.content.Context
import android.nfc.NfcAdapter
import dagger.Component
import dagger.Module
import dagger.Provides
import de.apoehlmann.presentation.modules.AppNavigationModule
import de.apoehlmann.sampleapp.data.NfcAdapterWrapper
import de.apoehlmann.sampleapp.data.NfcAdapterWrapperImpl
import de.apoehlmann.sampleapp.data.NfcRepository
import de.apoehlmann.sampleapp.data.NfcRepositoryImpl
import de.apoehlmann.sampleapp.presentation.BlueFragment
import de.apoehlmann.sampleapp.presentation.OrangeFragment
import de.apoehlmann.sampleapp.presentation.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppNavigationModule::class, NfcModule::class))
public interface AppComponent {
    fun inject(app: MainActivity)
    fun inject(fragment: BlueFragment)
    fun inject(fragment: OrangeFragment)
}

@Module
class NfcModule(val context: Context) {

    @Singleton
    @Provides
    fun providesNfcRepository(nfcAdapterWrapper: NfcAdapterWrapper): NfcRepository {
        return NfcRepositoryImpl(nfcAdapterWrapper)
    }

    @Singleton
    @Provides
    fun providesNfcAdapterWrapper(): NfcAdapterWrapper {
        return NfcAdapterWrapperImpl(NfcAdapter.getDefaultAdapter(context))
    }
}