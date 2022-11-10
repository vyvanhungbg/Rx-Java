package com.example.rxkotlin.obsever

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxkotlin.databinding.ActivityCompletableBinding
import com.example.rxkotlin.databinding.ActivitySingleBinding
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

class CompletableActivity : AppCompatActivity() {

    private val singleOb = Completable.create { it.onComplete() }

    private var value = 0
    private var disposables: Disposable? = null
    private lateinit var binding: ActivityCompletableBinding
    private val TAG = ObserverActivity::class.java.simpleName

    private val completableObserver = object : CompletableObserver {
        override fun onSubscribe(d: Disposable) {
            disposables = d
            Log.e(TAG, "Completable : onSubscribe")
        }

        override fun onComplete() {
            Log.e(TAG, "Completable: onComplete")
        }


        override fun onError(e: Throwable) {
            Log.e(TAG, "Completable: message: ${e.message}")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompletableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Completable")
        binding.btnSubscriber.setOnClickListener {
            singleOb.subscribe(completableObserver)
        }

    }
}