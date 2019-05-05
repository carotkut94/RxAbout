import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

fun main(){

    exampleOf("Publish Subject"){
        val quotes = PublishSubject.create<String>()

        quotes.onNext(episodeI)

        // Here this will print nothing as it subscribed after thr onNext() above
        // so this will print all future onNext() event
        val subscription1 = quotes.subscribeBy(
            onNext = {println("1) $it")},
            onComplete = { println("1 Complete")}
        )

        quotes.onNext(episodeII)


        val subscription2 = quotes.subscribeBy(
            onNext = {println("2) $it")},
            onComplete = { println("2 Complete")}
        )

        quotes.onNext(episodeIII)

        // subscription1 will receive both the episodeII and episodeIII but,
        // subscription2 will receive only the episodeIII event at it subscribed
        // after the quotes.onNext(episodeII)

        subscription1.dispose()


        // Now this event will only be receive by the subscription2 as subscription1
        // is disposed and no longer remains as the subscriber
        quotes.onNext(episodeIV)

        quotes.onComplete()

        val subscription3 = quotes.subscribeBy(
            onNext = {println("3) $it")},
            onComplete = { println("3 Complete")}
        )

        // Here all the subscribers who subscribed before onCompleted() or after
        // will always get the onComplete() event, consider it like a stop event
        // that it transmitted to every subscriber

        quotes.onNext(episodeV)
        // Nothing happens here, as it was already completed
        subscription2.dispose()
        subscription3.dispose()
    }

    exampleOf("Behavior Subject"){
        val subscriptions = CompositeDisposable()

        val quotes = BehaviorSubject.createDefault(episodeV)

        val subscription1 = quotes.subscribeBy(
            onNext = { println("1) $it")},
            onError = { println("1) $it")},
            onComplete = { println("1) Complete")}
        )

        quotes.onError(Throwable("Never Said that"))

        // it will receive the error event only not the initial default() event
        // stop events such as onError and onCompleted are always delivered to all subscriber
        // current or old.
        subscriptions.add(
            quotes.subscribeBy(
                onNext = { println("2) $it")},
                onError = { println("2) $it")},
                onComplete = { println("2) Complete")}
            )
        )
    }

    exampleOf("Behavior Subject state"){

        val subscription = CompositeDisposable()

        val quotes = BehaviorSubject.createDefault(episodeIX)
        println(quotes.value)

        subscription.add(
            quotes.subscribeBy{
                println("1) $it")
            }
        )

        quotes.onNext(episodeVII)
        println(quotes.value)
    }

    exampleOf("Replay Subject"){
        val subscriptions = CompositeDisposable()
        val subject = ReplaySubject.createWithSize<String>(2)

        subject.onNext(solo)

        // This subscription was added after onNext() above, but still gets the emitted event
        subscriptions.add(
            subject.subscribeBy(
                onNext = {println("1) $it")},
                onError = { println("1) $it")},
                onComplete = { println("1) Completed")}
            )
        )
    }

}