import de.apoehlmann.domain.UseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class Time: UseCase<Long>() {

    override fun getScheduler() = Schedulers.computation()

    override fun buildObservable(): Observable<Long> {
        return Observable.interval(1, TimeUnit.MILLISECONDS).map { System.currentTimeMillis() }
    }

}

class OnlyEverySec: UseCase<Long>() {

    val time = Time()

    override fun getScheduler() = Schedulers.trampoline()

    override fun buildObservable(): Observable<Long> {
        return time.connect().filter { it % 1000L == 0L}.distinctUntilChanged()
    }

}

class TimeInStdAndMin: UseCase<Pair<Int, Int>>() {

    val onlyEverySec = OnlyEverySec()

    override fun getScheduler() = Schedulers.trampoline()

    override fun buildObservable(): Observable<Pair<Int, Int>> {

        return onlyEverySec.connect().map {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            val min = calendar.get(Calendar.MINUTE)
            val std = calendar.get(Calendar.HOUR_OF_DAY)
            return@map Pair(std, min)
        }.distinctUntilChanged()
    }

    companion object {
        val instance = TimeInStdAndMin()
    }

}

class PrintTimeWithSmile: UseCase<String>() {

    val timeInStdAndMin = TimeInStdAndMin.instance

    override fun getScheduler() = Schedulers.trampoline()

    override fun buildObservable(): Observable<String> {
        return timeInStdAndMin.connect().map { "${it.first}:${it.second} Uhr :)" }
    }
}

class FormattedTime: UseCase<String>() {

    val timeInStdAndMin = TimeInStdAndMin.instance

    override fun getScheduler() = Schedulers.trampoline()

    override fun buildObservable(): Observable<String> {
        return timeInStdAndMin.connect().map { "${it.first}:${it.second} Uhr" }
    }
}

class Just: UseCase<String>() {
    override fun buildObservable() = Observable.just("Hello World")

    override fun getScheduler() = Schedulers.computation()

}