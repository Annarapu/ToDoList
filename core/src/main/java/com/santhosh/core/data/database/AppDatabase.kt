package com.santhosh.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.santhosh.core.data.database.dao.ToDoDao
import com.santhosh.core.data.database.entity.ToDoEntity

@Database(
    entities = [ToDoEntity::class],
    version = AppDatabase.DATABASE_VERSION,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME: String = "todo_db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}