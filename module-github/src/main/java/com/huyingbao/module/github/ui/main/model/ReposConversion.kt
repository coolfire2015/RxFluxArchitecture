package com.huyingbao.module.github.ui.main.model

/**
 * 仓库相关实体转换
 * Created by guoshuyu
 * Date: 2018-10-29
 */
object ReposConversion {
    fun trendToReposUIModel(trendModel: Trend): Repos {
        val reposUIModel = Repos()
        reposUIModel.hideWatchIcon = true
        reposUIModel.ownerName = trendModel.name
        reposUIModel.ownerPic = trendModel.contributors[0]
        reposUIModel.repositoryDes = trendModel.description
        reposUIModel.repositoryName = trendModel.reposName
        reposUIModel.repositoryFork = trendModel.forkCount
        reposUIModel.repositoryStar = trendModel.starCount
        reposUIModel.repositoryWatch = trendModel.meta
        reposUIModel.repositoryType = trendModel.language
        return reposUIModel
    }
}