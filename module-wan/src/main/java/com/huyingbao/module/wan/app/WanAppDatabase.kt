package com.huyingbao.module.wan.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huyingbao.module.wan.model.Article
import com.huyingbao.module.wan.model.ArticleDao

/**
 *GithubAppDatabase是一个继承[RoomDatabase]的抽象类。
 *
 *在[Database]注解中包含与数据库相关联的实体列表。
 *
 *包含一个具有0个参数的抽象方法，并返回用@Dao注释的类。
 */
@Database(
        entities = [Article::class],
        version = 3,
        exportSchema = false
)
abstract class WanAppDatabase : RoomDatabase() {
    abstract fun reposDao(): ArticleDao
}
