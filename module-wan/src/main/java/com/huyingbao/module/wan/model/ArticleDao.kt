package com.huyingbao.module.wan.model

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

/**
 * 包含用于访问数据库的方法。
 * 通过使用DAO类访问数据库而不是查询构造器或直接查询，可以分离数据库体系结构的不同组件。
 * 此外，DAOs允许您在测试应用程序时轻松模拟数据库访问。
 * DAO既可以是接口，也可以是抽象类。如果是抽象类，它可以有一个构造函数，它把RoomDatabase作为唯一的参数。
 * Room在编译时创建每个DAO实现。
 * 除非调用[RoomDatabase.Builder.allowMainThreadQueries]，否则Room不支持在主线程上的数据访问。
 * 而返回[LiveData]或者[io.reactivex.Flowable]实例的异步查询可免除此规则，因为它们在需要时异步地在后台线程上运行查询。
 */
@Dao
interface ArticleDao {
    /**
     * SQLite处理@Insert(onConflict = OnConflictStrategy.REPLACE)作为一套REMOVE和REPLACE操作而不是单一的更新操作。
     * 本替换冲突值的方法有可能影响外键约束
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: ArrayList<Article>)

    @Query("SELECT * FROM article ORDER BY publish_time DESC")
    fun getArticleList(): DataSource.Factory<Int, Article>

    @Query("DELETE FROM article")
    fun deleteAll()
}
