package com.huyingbao.module.github.ui.main.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.core.common.utils.TimeUtils
import com.huyingbao.core.image.ImageLoader
import com.huyingbao.core.image.ImageLoaderUtils
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.model.Event

/**
 * Created by liujunfeng on 2019/6/15.
 */
class EventAdapter(data: List<Event>?) : BaseQuickAdapter<Event, BaseViewHolder>(R.layout.github_layout_item_event, data) {

    override fun convert(helper: BaseViewHolder, item: Event) {
        helper.setText(R.id.tv_event_action, item.type + "    " + item.repo?.name)
        helper.setText(R.id.tv_event_user_name, item.actor?.login ?: item.org?.name)
        helper.setText(R.id.tv_event_time, TimeUtils.getNewsTimeStr(item.createdAt))
        val imageLoader = ImageLoader.Builder<String>()
        imageLoader.isCircle = true
        imageLoader.resource = item.actor?.avatarUrl ?: item.org?.avatarUrl
        imageLoader.errorHolder = android.R.drawable.ic_menu_camera
        imageLoader.imgView = helper.getView<ImageView>(R.id.iv_event_user_head)
        ImageLoaderUtils.loadImage(mContext, imageLoader.build())
    }
}
