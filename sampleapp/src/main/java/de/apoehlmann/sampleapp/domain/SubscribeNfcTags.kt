package de.apoehlmann.sampleapp.domain

import de.apoehlmann.domain.UseCase
import de.apoehlmann.sampleapp.data.NfcRepository
import de.apoehlmann.sampleapp.data.NfcTag
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

class SubscribeNfcTags @Inject constructor(val nfcRepository: NfcRepository): UseCase<NfcTag>() {

    override fun getScheduler() = Schedulers.newThread()

    override fun buildObservable() = nfcRepository.subscribeNfcTags()

}