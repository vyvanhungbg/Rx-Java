package com.example.rxkotlin.demorxjava.subject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxkotlin.databinding.ActivityAsysncSubjectBinding
import com.example.rxkotlin.databinding.ActivityBehaviorSubjectBinding
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject

class AsysncSubjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAsysncSubjectBinding

    val subject: AsyncSubject<Int> = AsyncSubject.create()
    var value = 1
    var textOnNext = "onNext"
    var textSubscription = "SubScript"

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsysncSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("AsyncSubject")
        binding.btnOnNext.setOnClickListener {
            subject.onNext(value)
            textOnNext = textOnNext + " " + value
            binding.textViewValue.text = textOnNext
            value++
        }

        binding.btnSubscriber.setOnClickListener {
            if (disposable == null) {
                disposable =
                    subject.subscribe(
                        { item ->
                            textSubscription = textSubscription + " " + item
                            binding.textViewSubscribe.text = textSubscription
                        },
                        { error -> Log.e("onError", error.toString()) })
            }
        }


        binding.btnDispose.setOnClickListener {
            disposable?.dispose()
            disposable = null
        }

        binding.btnCompleted.setOnClickListener {
            subject.onComplete()
        }
    }
}