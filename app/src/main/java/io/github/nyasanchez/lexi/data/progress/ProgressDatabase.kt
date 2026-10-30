package io.github.nyasanchez.lexi.data.progress

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedGame::class], version = 1, exportSchema = true)
abstract class ProgressDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile private var instance: ProgressDatabase? = null

        fun get(context: Context): ProgressDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext, ProgressDatabase::class.java, "lexi-progress.db",
            ).build().also { instance = it }
        }
    }
}
