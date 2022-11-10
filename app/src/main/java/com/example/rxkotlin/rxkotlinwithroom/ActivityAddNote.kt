package com.example.rxkotlin.rxkotlinwithroom

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rxkotlin.databinding.ActivityAddNoteBinding
import com.example.rxkotlin.rxkotlinwithroom.room.NoteEntity
import com.example.rxkotlin.rxkotlinwithroom.room.RoomDBHelper
import com.example.rxkotlin.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ActivityAddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding;
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonAddNote.setOnClickListener {
            val loadDisposable = addNote(this).subscribe()
            compositeDisposable.add(loadDisposable)
        }
    }

    private fun addNote(context: Context): Flowable<List<Long>> {
        return Maybe.fromAction<List<Long>>() {
            val database = RoomDBHelper.getInstance(context)
            val notes = NoteEntity(
                title = binding.editTextTitle.text.toString(),
                content = binding.editTextContent.text.toString()
            )
            database.dao.insert(notes)
        }.toFlowable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                toast("Đã thêm")
            }
            .doOnError {
                toast("Lỗi khi thêm")
            }
    }
}