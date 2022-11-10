package com.example.rxkotlin.rxkotlinwithroom.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], exportSchema = false, version = 1)

abstract class RoomDBHelper : RoomDatabase() {
    abstract val dao: NoteDao

    companion object {
        private var instance: RoomDBHelper? = null

        fun getInstance(context: Context): RoomDBHelper {
            return synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RoomDBHelper::class.java,
                    "Note"
                ).fallbackToDestructiveMigrationFrom().build().also { instance = it }
            }
        }
    }
}
