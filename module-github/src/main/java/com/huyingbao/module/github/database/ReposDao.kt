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
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.huyingbao.module.github.ui.main.model.Repos

/**
 * 包含用于访问数据库的方法。
 */
@Dao
interface ReposDao {
    @Query("SELECT * FROM repos")
    fun getPlants(): LiveData<List<Repos>>


    @Query("SELECT * FROM repos WHERE id = :reposId")
    fun getPlant(reposId: String): LiveData<Repos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(plants: List<Repos>)
}
