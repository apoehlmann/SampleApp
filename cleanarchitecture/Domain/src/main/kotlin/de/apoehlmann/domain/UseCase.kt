package de.apoehlmann.domain

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

abstract class UseCase<T> {

    fun execute(onNext: Consumer<T>): Disposable {
        return this.execute(onNext, onError = Consumer { println(it.toString()) })
    }

    fun execute(onNext: Consumer<T>, onError: Consumer<Throwable>): Disposable {
        return this.execute(onNext, onError, Action {  })
    }

    fun execute(onNext: Consumer<T>, onError: Consumer<Throwable>, onComplete: Action): Disposable {
        return wrapObservable().observeOn(getScheduler()).subscribe(onNext, onError, onComplete)
    }

    fun execute(observer: Observer<T>) {
        wrapObservable().observeOn(getScheduler()).subscribe(observer)
    }

    /**
     * Only for internal communication with other UseCases
     */
    fun connect() = wrapObservable()

    private fun wrapObservable(): Observable<T> {
        return Observable.defer { buildObservable() }
    }

    abstract fun getScheduler(): Scheduler

    abstract fun buildObservable(): Observable<T>
}