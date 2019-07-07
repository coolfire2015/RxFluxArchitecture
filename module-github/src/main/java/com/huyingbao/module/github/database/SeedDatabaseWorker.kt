///*
// * Copyright 2018 Google LLC
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.huyingbao.module.github.database
//
//import android.content.Context
//import android.util.Log
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import com.google.gson.stream.JsonReader
//import com.huyingbao.module.github.database.GithubAppDatabase.Key.PLANT_DATA_FILENAME
//import com.huyingbao.module.github.ui.main.model.Repos
//import kotlinx.coroutines.coroutineScope
//
//class SeedDatabaseWorker(
//        context: Context,
//        workerParams: WorkerParameters
//) : CoroutineWorker(context, workerParams) {
//
//    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }
//
//    override suspend fun doWork(): Result = coroutineScope {
//
//        try {
//            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
//                JsonReader(inputStream.reader()).use { jsonReader ->
//                    val plantType = object : TypeToken<List<Repos>>() {}.type
//                    val plantList: List<Repos> = Gson().fromJson(jsonReader, plantType)
//
//                    val database = GithubAppDatabase.getInstance(applicationContext)
//                    database.plantDao().insertAll(plantList)
//
//                    Result.success()
//                }
//            }
//        } catch (ex: Exception) {
//            Log.e(TAG, "Error seeding database", ex)
//            Result.failure()
//        }
//    }
//}
//
////    object Key {
////        const val DATABASE_NAME = "sunflower-db"
////        const val PLANT_DATA_FILENAME = "plants.json"
////    }
////
////    companion object {
////        // 在实例化AppDatabase对象时，应遵循单例设计模式，
////        // 因为每个Roomdatabase实例都相当消耗性能，并且您很少需要访问多个实例。
////        // For Singleton instantiation
////        @Volatile
////        private var instance: GithubAppDatabase? = null
////
////        fun getInstance(context: Context): GithubAppDatabase {
////            return instance ?: synchronized(this) {
////                instance
////                        ?: buildDatabase(context).also { instance = it }
////            }
////        }
////
////        // Create and pre-populate the database. See this article for more details:
////        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
////        private fun buildDatabase(context: Context): GithubAppDatabase {
////            return Room.databaseBuilder(context, GithubAppDatabase::class.java, DATABASE_NAME)
////                    .addCallback(object : RoomDatabase.Callback() {
////                        override fun onCreate(db: SupportSQLiteDatabase) {
////                            super.onCreate(db)
////                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
////                            WorkManager.getInstance(context).enqueue(request)
////                        }
////                    })
////                    .build()
////        }
////    }