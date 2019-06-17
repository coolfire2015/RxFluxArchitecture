package com.huyingbao.module.github.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.model.Event

/**
 * Created by liujunfeng on 2019/6/15.
 */
class EventAdapter(data: List<Event>?) : BaseQuickAdapter<Event, BaseViewHolder>(R.layout.github_layout_item_event, data) {

    override fun convert(helper: BaseViewHolder, item: Event) {
        helper.setText(R.id.tv_event_action, item.payload?.action)
                .setText(R.id.tv_event_user_name, item.actor?.name ?: item.org?.name)
                .setText(R.id.tv_event_time, item.createdAt.toString())
    }
}
