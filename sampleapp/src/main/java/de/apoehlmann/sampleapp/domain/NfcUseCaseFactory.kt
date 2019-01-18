package de.apoehlmann.sampleapp.domain

import de.apoehlmann.sampleapp.data.ActivityWrapper
import de.apoehlmann.sampleapp.data.NfcRepository
import javax.inject.Inject


class NfcUseCaseFactory @Inject constructor(val nfcRepository: NfcRepository) {

    fun createStartNfcListening(activityWrapper: ActivityWrapper) = StartNfcListening(nfcRepository, activityWrapper)

    fun createStopNfcListening(activityWrapper: ActivityWrapper) = StopNfcListening(nfcRepository, activityWrapper)
}