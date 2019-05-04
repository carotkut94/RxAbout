import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

// lets subscribe to observables
fun main(args: Array<String>){

    exampleOf("Subscribe"){
        val observable = Observable.just(episodeIV, episodeV, episodeVI)

        observable.subscribe {
            println(it)
        }
        // subscribeBy with named arguments, with OnNext, OnComplete and OnError
        // named argument such as onError can be ignore, needs not to be compulsorily implemented,
        // but if some error comes and onError is missing then we will get an exception
        observable.subscribeBy(
            onNext = { println(it)},
            onComplete = { println("Completed")},
            onError = {it.printStackTrace()}
        )

    }

    // Lets create an empty observable
    // it simply emit the onComplete event only.
    exampleOf("Empty Observable"){
        val empty = Observable.empty<Unit>()
        empty.subscribeBy(
            onNext = { println(it)},
            onComplete = { println("Completed")}
        )
    }

    // This will never emit the onComplete event
    exampleOf("Never"){
        val observable = Observable.never<Any>()
        observable.subscribeBy(
            onNext = {println(it)},
            onComplete = {println("Completed")}
        )

    }
}