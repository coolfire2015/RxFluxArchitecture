package com.huyingbao.module.history.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * 默认情况下，Room使用类名作为数据库表名，SQLite中的表名不区分大小写。
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Entity(tableName = "article")
data class Article(

    /**
     * apkLink :
     * author : 郭霖
     * chapterId : 409
     * chapterName : 郭霖
     * collect : false
     * courseId : 13
     * desc :
     * envelopePic :
     * fresh : false
     * id : 7668
     * link : https://mp.weixin.qq.com/s/s4WgLFN0A-vO0ko0wi25mA
     * niceDate : 2018-12-17
     * origin :
     * projectLink :
     * publishTime : 1544976000000
     * superChapterId : 408
     * superChapterName : 公众号
     * tags : [{"name":"公众号","url":"/wxarticle/list/409/1"}]
     * title : 一起玩转Android项目中的字节码
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */
    var apkLink: String? = null,
    var author: String? = null,
    var chapterId: Int = 0,
    var chapterName: String? = null,
    var isCollect: Boolean = false,
    var courseId: Int = 0,
    var desc: String? = null,
    var envelopePic: String? = null,
    var isFresh: Boolean = false,
    @PrimaryKey
    var id: Int = 0,
    var link: String? = null,
    var niceDate: String? = null,
    var origin: String? = null,
    var projectLink: String? = null,
    //具有不同的名称
    @ColumnInfo(name = "publish_time")
    var publishTime: Long = 0,
    var superChapterId: Int = 0,
    var superChapterName: String? = null,
    var title: String? = null,
    var type: Int = 0,
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0,
    @Ignore //不想持久的字段
    var tags: List<Tag>? = null
)
