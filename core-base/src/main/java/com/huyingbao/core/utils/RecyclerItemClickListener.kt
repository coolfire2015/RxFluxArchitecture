package com.huyingbao.core.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 通用[RecyclerView]点击长按监听器，通过[RecyclerView.SimpleOnItemTouchListener]实现。
 *
 * Created by liujunfeng on 2019/8/12.
 */
class RecyclerItemClickListener(
        context: Context?,
        recyclerView: RecyclerView?,
        private val itemClickListener: OnItemClickListener
) : RecyclerView.SimpleOnItemTouchListener() {
    private val gestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener : OnItemClickListener {
        fun onItemLongClick(view: View?, position: Int)
    }

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(motionEvent: MotionEvent) {
                recyclerView?.findChildViewUnder(motionEvent.x, motionEvent.y)?.let {
                    if (itemClickListener is OnItemLongClickListener) {
                        itemClickListener.onItemLongClick(it, recyclerView.getChildAdapterPosition(it))
                    }
                }
            }
        })
    }

    override fun onInterceptTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent): Boolean {
        recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y)?.let {
            if (gestureDetector.onTouchEvent(motionEvent)) {
                itemClickListener.onItemClick(it, recyclerView.getChildAdapterPosition(it))
            }
        }
        return false
    }
}