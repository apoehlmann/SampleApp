package de.apoehlmann.sampleapp.data

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import io.reactivex.functions.Consumer
import io.reactivex.subjects.Subject
import timber.log.Timber
import java.lang.NullPointerException

class ActivityWrapper(val activity: Activity?)

class NfcAdapterWrapperImpl(val nfcAdapter: NfcAdapter?): NfcAdapterWrapper {

    private var receiver: NfcBroadcastReceiver? = null

    override fun startListening(activityWrapper: ActivityWrapper, listener: Subject<NfcTag>) {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION)
        receiver = NfcBroadcastReceiver(listener)

        activityWrapper.activity?.registerReceiver(receiver, intentFilter)
    }

    override fun stopListening(activityWrapper: ActivityWrapper) {
        if (receiver != null) {
            activityWrapper.activity?.unregisterReceiver(receiver)
        } else {
            Timber.e(NullPointerException("receiver == null"))
        }
    }

    companion object {
        const val ACTION = "de.apoehlmann.nfc.action"
        const val KEY_ID = "de.apoehlmann.nfc.key.id"
        const val KEY_TECHLIST = "de.apoehlmann.nfc.key.techlist"
    }
}

class NfcBroadcastReceiver(val listener: Subject<NfcTag>): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val id = it.extras.getByteArray(NfcAdapterWrapperImpl.KEY_ID)
            val techList = it.extras.getStringArray(NfcAdapterWrapperImpl.KEY_TECHLIST)
            listener.onNext(NfcTag(id, techList))
        }
    }

}