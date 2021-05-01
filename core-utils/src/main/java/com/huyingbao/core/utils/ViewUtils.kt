package com.huyingbao.core.utils

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

/**
 * 解决[com.google.android.material.appbar.AppBarLayout.setElevation]无效问题。
 *
 * @param view 一般是[com.google.android.material.appbar.AppBarLayout]
 */
fun View.setAppBarElevation(elevation: Float) {
    stateListAnimator = StateListAnimator().apply {
        addState(
            IntArray(0),
            ObjectAnimator.ofFloat(this@setAppBarElevation, "elevation", elevation)
        )
    }
}

/**
 * 设置View滑动联动[AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL]和[AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS]
 *
 * @param view 一般是[com.google.android.material.appbar.AppBarLayout]中的子布局。
 */
fun View.setAppBarScroll() {
    if (layoutParams is AppBarLayout.LayoutParams) {
        (layoutParams as AppBarLayout.LayoutParams).scrollFlags =
            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
    }
}

/**
 * [RecyclerView]滑动到顶部
 */
fun RecyclerView.scrollToTop() {
    if (layoutManager is LinearLayoutManager) {
        if ((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() > 20) {
            scrollToPosition(0)
        } else {
            smoothScrollToPosition(0)
        }
    }
}