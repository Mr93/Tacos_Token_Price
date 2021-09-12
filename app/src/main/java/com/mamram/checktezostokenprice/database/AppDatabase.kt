package com.mamram.checktezostokenprice.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mamram.checktezostokenprice.data.entity.Contract

@Database(entities = arrayOf(Contract::class), version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contractDao(): ContractDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "tezostokenprice")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}