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

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huyingbao.module.github.ui.main.model.Repos

/**
 *GithubAppDatabase是一个继承[RoomDatabase]的抽象类。
 *
 *在[Database]注解中包含与数据库相关联的实体列表。
 *
 *包含一个具有0个参数的抽象方法，并返回用@Dao注释的类。
 */
@Database(entities = [Repos::class], version = 3, exportSchema = false)
abstract class GithubAppDatabase : RoomDatabase() {
    abstract fun plantDao(): ReposDao
}
