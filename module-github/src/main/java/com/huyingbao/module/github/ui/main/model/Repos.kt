package com.huyingbao.module.github.ui.main.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 仓库相关UI类型
 * Created by guoshuyu
 * Date: 2018-10-29
 */
@Entity(tableName = "repos")
class Repos {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "id")
    var reposId: Int = 0
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