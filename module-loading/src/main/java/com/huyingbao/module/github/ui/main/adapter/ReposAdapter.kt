package com.huyingbao.module.github.ui.main.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.core.image.ImageLoader
import com.huyingbao.core.image.ImageLoaderUtils
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.model.Repos

/**
 * 推荐趋势Item适配器
 *
 * Created by liujunfeng on 2019/6/15.
 */
class ReposAdapter(data: List<Repos>?) : BaseQuickAdapter<Repos, BaseViewHolder>(R.layout.github_layout_item_repos, data) {

    override fun convert(helper: BaseViewHolder, item: Repos) {
        helper.setText(R.id.tv_repos_owner, item.ownerName)
        helper.setText(R.id.tv_repos_name, item.repositoryName)
        helper.setText(R.id.tv_repos_des, item.repositoryDes)
        helper.setText(R.id.tv_repos_language, item.repositoryType)
        helper.setText(R.id.tv_repos_star, item.repositoryStar)
        helper.setText(R.id.tv_repos_fork, item.repositoryFork)
        helper.setText(R.id.tv_repos_watch, item.repositoryWatch)
        val imageLoader = ImageLoader.Builder<String>()
        imageLoader.isCircle = true
        imageLoader.resource = item.ownerPic
        imageLoader.errorHolder = android.R.drawable.ic_menu_camera
        imageLoader.imgView = helper.getView<ImageView>(R.id.iv_repos_user_img)
        ImageLoaderUtils.loadImage(mContext, imageLoader.build())
    }
}
