package com.example.rxkotlin.obsever

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxkotlin.databinding.ActivityObserverBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ObserverActivity : AppCompatActivity() {

    private val subject: PublishSubject<Int> = PublishSubject.create()
   // private val observable = Observable.just(1, 2, 3, 4)
   // private val observable = Observable.range(1,5)
    private var value = 0
    private var disposables: Disposable? = null
    private lateinit var binding: ActivityObserverBinding
    private val TAG = ObserverActivity::class.java.simpleName

    private val observer = object : Observer<Int> {
        override fun onSubscribe(d: Disposable) {
            disposables = d
            Log.e(TAG, "onSubscribe")
        }

        override fun onNext(t: Int) {
            Log.e(TAG, "onNext: $t")
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, "onError: message: ${e.message}")
        }

        override fun onComplete() {
            Log.e(TAG, "onComplete")
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObserverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Observer")
        binding.btnOnNext.setOnClickListener {
            subject.onNext(value)
            value++
        }

        binding.btnOnDispose.setOnClickListener {
            disposables?.dispose()
        }

        binding.btnOnCompleted.setOnClickListener {
            subject.onComplete()
        }

        binding.btnOnError.setOnClickListener {
            subject.onError(Exception("Lỗi"))
        }

        binding.btnSubscriber.setOnClickListener {
            subject.subscribe(observer)
        }


       // observable.subscribe(observer)
/*
        val firstNames = Observable.just("James", "Jean-Luc", "Benjamin", "Hung")
        val lastNames = Observable.just("Kirk", "Picard", "Sisko")
        firstNames.zipWith(lastNames) { first: String, last: String -> "$first $last" }
            .subscribe { item: String? ->
                println(
                    item
                )
            }

        Observable.just("A", "B", "C")
            .flatMap { a: String ->
                Observable.intervalRange(1, 3, 0, 4, TimeUnit.SECONDS)
                    .map { b: Long -> "($a, $b)" }
            }
            .subscribe() { x: String? ->
                println(
                    x
                )
            }*/
    }
}