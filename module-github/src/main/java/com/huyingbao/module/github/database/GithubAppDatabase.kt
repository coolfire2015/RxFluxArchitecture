/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huyingbao.module.github.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.huyingbao.module.github.database.GithubAppDatabase.Key.DATABASE_NAME
import com.huyingbao.module.github.ui.main.model.Repos

/**
 * The Room database for this app
 */
@Database(entities = [Repos::class], version = 1, exportSchema = false)
abstract class GithubAppDatabase : RoomDatabase() {
    abstract fun plantDao(): ReposDao

    object Key {
        const val DATABASE_NAME = "sunflower-db"
        const val PLANT_DATA_FILENAME = "plants.json"
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: GithubAppDatabase? = null

        fun getInstance(context: Context): GithubAppDatabase {
            return instance ?: synchronized(this) {
                instance
                        ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): GithubAppDatabase {
            return Room.databaseBuilder(context, GithubAppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    })
                    .build()
        }
    }
}
