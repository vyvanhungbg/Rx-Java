package com.example.rxkotlin.obsever

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxkotlin.databinding.ActivityObserverBinding
import com.example.rxkotlin.databinding.ActivitySingleBinding
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

class SingleActivity : AppCompatActivity() {

    private val singleOb = Single.create<Int> { it -> it.onSuccess(5)  }

    private var value = 0
    private var disposables: Disposable? = null
    private lateinit var binding: ActivitySingleBinding
    private val TAG = ObserverActivity::class.java.simpleName

    private val singleObserver = object : SingleObserver<Int> {
        override fun onSubscribe(d: Disposable) {
            disposables = d
            Log.e(TAG, "Single : onSubscribe")
        }

        override fun onSuccess(t: Int) {
            Log.e(TAG, "Single : onSuccess: $t")
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, "onError: message: ${e.message}")
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Single")
        binding.btnSubscriber.setOnClickListener {
            singleOb.subscribe(singleObserver)
        }

    }
}