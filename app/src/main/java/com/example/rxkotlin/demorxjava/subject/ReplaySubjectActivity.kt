package com.example.rxkotlin.demorxjava.subject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxkotlin.databinding.ActivityReplaySubjectBinding
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.ReplaySubject

class ReplaySubjectActivity : AppCompatActivity() {

  private lateinit var binding: ActivityReplaySubjectBinding

  //val subject: ReplaySubject<Int> = ReplaySubject.createWithSize(3)
  val subject: ReplaySubject<Int> = ReplaySubject.create()
  var value = 1
  var textOnNext = "onNext"
  var textSubscription = "SubScript"

  private var disposable: Disposable? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityReplaySubjectBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.setTitle("ReplaySubject")
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

  }
}
