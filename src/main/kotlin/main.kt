import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable

/**
 * how to create observables
 */

fun main(args:Array<String>){

    exampleOf("Creation of observables"){
        // Specifying type here
        val mostPopular : Observable<String> = Observable.just(episodeV)
        // With type inference
        val orignalTriology = Observable.just(episodeIV, episodeV, episodeVI)
        // observable of list of string, here the data type will be Observable<List<String>>
        val prequelTriology = Observable.just(listOf(episodeI, episodeII, episodeIII))
        // but here the type of object will be a type i.e String.
        val sequelTriology = Observable.fromIterable(listOf(episodeVII, episodeVIII, episodeIX))

        // Now lets use RxKotlin to create observable
        // here stories will be of Observable<String>
        val stories = listOf(solo, rogueOne).toObservable()
    }

}