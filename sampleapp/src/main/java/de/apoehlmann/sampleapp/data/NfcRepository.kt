package de.apoehlmann.sampleapp.data

import de.apoehlmann.repository.Repository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject
import timber.log.Timber
import javax.inject.Inject

class NfcRepositoryImpl @Inject constructor(val nfcAdapterWrapper: NfcAdapterWrapper): NfcRepository {

    val nfcListener = BehaviorSubject.create<Subject<NfcTag>>()

    override fun subscribeNfcTags(): Observable<NfcTag> {
        return nfcListener.switchMap {
            it.doOnNext { Timber.d("Found new tag ${it.id.contentToString()}") }
        }
    }

    override fun startListening(activityWrapper: ActivityWrapper): Observable<Void> {
        val nfcTagData = ReplaySubject.create<NfcTag>()
        nfcListener.onNext(nfcTagData)
        nfcAdapterWrapper.startListening(activityWrapper, nfcTagData)
        return Observable.empty()
    }

    override fun stopListening(activityWrapper: ActivityWrapper): Observable<Void> {
        nfcListener.onNext(PublishSubject.create())
        nfcAdapterWrapper.stopListening(activityWrapper)
        return Observable.empty()
    }

}

interface NfcRepository: Repository {
    fun subscribeNfcTags(): Observable<NfcTag>
    fun startListening(activityWrapper: ActivityWrapper): Observable<Void>
    fun stopListening(activityWrapper: ActivityWrapper): Observable<Void>
}

interface NfcAdapterWrapper {
    fun startListening(activityWrapper: ActivityWrapper, listener: Subject<NfcTag>)
    fun stopListening(activityWrapper: ActivityWrapper)
}

data class NfcTag(val id: ByteArray, val techList: Array<String>)
