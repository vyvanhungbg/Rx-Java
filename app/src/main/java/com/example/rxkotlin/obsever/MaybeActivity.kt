package com.example.rxkotlin.obsever

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxkotlin.databinding.ActivityMaybeBinding
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable

class MaybeActivity : AppCompatActivity() {
    private val maybeOb = Maybe.create<Int> {
            it -> 5
            //it.onError(Exception())
            //it.onComplete()
            it.onSuccess(5)
    }

    private var value = 0
    private var disposables: Disposable? = null
    private lateinit var binding: ActivityMaybeBinding
    private val TAG = ObserverActivity::class.java.simpleName

    private val maybeObserver = object : MaybeObserver<Int> {

        override fun onSubscribe(d: Disposable) {
            disposables = d
            Log.e(TAG, "Maybe : onSubscribe")
        }

        override fun onSuccess(t: Int) {
            Log.e(TAG, "Maybe : onSuccess: $t")
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, "Maybe: error message: ${e.message}")
        }

        override fun onComplete() {
            Log.e(TAG, "Maybe: complete: ")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaybeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Maybe")


        binding.btnSubscriber.setOnClickListener {
            maybeOb.subscribe(maybeObserver)
        }

    }
}