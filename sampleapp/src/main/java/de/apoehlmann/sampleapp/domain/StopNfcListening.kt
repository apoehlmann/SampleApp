package de.apoehlmann.sampleapp.domain

import de.apoehlmann.domain.UseCase
import de.apoehlmann.sampleapp.data.ActivityWrapper
import de.apoehlmann.sampleapp.data.NfcRepository
import io.reactivex.schedulers.Schedulers

class StopNfcListening(val nfcRepository: NfcRepository, val activityWrapper: ActivityWrapper): UseCase<Void>() {

    override fun getScheduler() = Schedulers.trampoline()

    override fun buildObservable() = nfcRepository.stopListening(activityWrapper)

}