package com.huyingbao.module.github.ui.main.model

/**
 * 趋势抽象类（推荐）
 * Created by guoshuyu
 * Date: 2018-10-29
 */
class TrendingRepo {
    var fullName: String = ""
    var url: String = ""
    var description: String = ""
    var language: String = ""
    var meta: String = ""
    var contributors: List<String> = arrayListOf()
    var contributorsUrl: String = ""
    var starCount: String = ""
    var forkCount: String = ""
    var name: String = ""
    var reposName: String = ""
}