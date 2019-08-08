package com.huyingbao.module.github.ui.main.model

import androidx.room.Entity

/**
 * 默认情况下，Room使用类名作为数据库表名，SQLite中的表名不区分大小写。
 * 如果实体具有复合主键，则可以使用[Entity.primaryKeys]属性
 */
@Entity(tableName = "repos",
        primaryKeys = ["repositoryName", "ownerName"])
class Repos {
    var ownerName: String = ""
    var ownerPic: String = ""
    var repositoryName: String = "-"
    var repositoryStar: String = "-"
    var repositoryFork: String = "-"
    var repositoryWatch: String = "-"
    var hideWatchIcon: Boolean = true
    var repositoryType: String = "-"
    var repositoryDes: String = ""
    var repositorySize: String = ""
    var repositoryLicense: String = ""
    var repositoryAction: String = ""
    var repositoryIssue: String = ""
}