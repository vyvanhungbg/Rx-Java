package com.example.rxkotlin.rxkotlinwithroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rxkotlin.databinding.ActivityRxKotlinWithRoomBinding
import com.example.rxkotlin.rxkotlinwithroom.room.ListAdapterNote
import com.example.rxkotlin.rxkotlinwithroom.room.NoteEntity
import com.example.rxkotlin.rxkotlinwithroom.room.RoomDBHelper
import com.example.rxkotlin.toast
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ActivityRxKotlinWithRoom : AppCompatActivity() {
    private lateinit var binding: ActivityRxKotlinWithRoomBinding;
    private val compositeDisposable = CompositeDisposable()
    private val adapter = ListAdapterNote()
    private val database by lazy {
        RoomDBHelper.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRxKotlinWithRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerview.adapter = adapter

        val display = dataDisplay(this).subscribe()
        compositeDisposable.add(display)
        binding.floatingButtonAdd.setOnClickListener {
            val intent = Intent(this, ActivityAddNote::class.java)
            startActivity(intent)
        }

        binding.editTextSearch.textChanges()
            .filter { it -> it.length > 2 }
            //.delay(500, TimeUnit.MILLISECONDS)
            //.delay(500, TimeUnit.MILLISECONDS)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateSearchResults)
    }

    private fun updateSearchResults(title: CharSequence) {
        Maybe.fromAction<List<NoteEntity>>() {
            val list = database.dao.findAllByTitle(title.toString())
            adapter.submitList(list)
        }.toObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                toast("Tìm thấy sản phẩm phù hợp với $title")
            }.doOnError {
                toast("Có lỗi khi tìm kiếm")
            }.subscribe()
    }

    private fun dataDisplay(context: Context): Flowable<List<Long>> {
        return Maybe.fromAction<List<Long>>() {
            //creating and submiting list to the recyclerview
            val myList = database.dao.getAll()
            adapter.submitList(myList)

        }.toFlowable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                context.toast("Lấy thành công")
            }
            .doOnError {
                toast("Lỗi lấy tất cả sản phẩm")
            }
    }
}

