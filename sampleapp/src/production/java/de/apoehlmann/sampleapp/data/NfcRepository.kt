package de.apoehlmann.sampleapp.data

import android.app.Activity
import android.nfc.NfcAdapter
import android.os.Bundle
import io.reactivex.subjects.Subject
import javax.inject.Inject

class NfcAdapterWrapperImpl @Inject constructor(val nfcAdapter: NfcAdapter): NfcAdapterWrapper {

    override fun startListening(activityWrapper: ActivityWrapper, listener: Subject<NfcTag>) {
        nfcAdapter.enableReaderMode(activityWrapper.activity, {listener.onNext(NfcTag(it.id, it.techList))}, NfcAdapter.STATE_ON, Bundle.EMPTY)
    }

    override fun stopListening(activityWrapper: ActivityWrapper) {
        nfcAdapter.disableReaderMode(activityWrapper.activity)
    }

}

data class ActivityWrapper(val activity: Activity)
