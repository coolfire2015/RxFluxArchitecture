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
    fun insertAll(list: List<Repos>)
}
