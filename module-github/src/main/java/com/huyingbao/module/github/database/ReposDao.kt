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

import androidx.lifecycle.LiveData
import androidx.room.*
import com.huyingbao.module.github.ui.main.model.Repos

/**
 * 包含用于访问数据库的方法。
 * 除非调用[RoomDatabase.Builder.allowMainThreadQueries]，否则Room不支持在主线程上的数据访问。
 * 而返回[LiveData]或者[io.reactivex.Flowable]实例的异步查询可免除此规则，因为它们在需要时异步地在后台线程上运行查询。
 */
@Dao
interface ReposDao {
    @Query("SELECT * FROM repos")
    fun getReposListLiveData(): LiveData<List<Repos>>

    /**
     * SQLite处理@Insert(onConflict = OnConflictStrategy.REPLACE)作为一套REMOVE和REPLACE操作而不是单一的更新操作。
     * 本替换冲突值的方法有可能影响外键约束
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(plants: List<Repos>)
}
