package com.huyingbao.module.common.utils

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.huyingbao.module.common.R
import com.huyingbao.module.common.behavior.ScaleDownShowBehavior

/**
 * 解决[com.google.android.material.appbar.AppBarLayout.setElevation]无效问题。
 *
 * @param view 一般是[com.google.android.material.appbar.AppBarLayout]
 */
fun View.setAppBarElevation(elevation: Float) {
    stateListAnimator = StateListAnimator().apply {
        addState(
                IntArray(0),
                ObjectAnimator.ofFloat(this@setAppBarElevation, "elevation", elevation))
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
 * 公用添加[FloatingActionButton]方法
 */
fun ViewGroup.addFloatingActionButton(context: Context, onClickListener: View.OnClickListener) {
    addView(
            FloatingActionButton(context).apply {
                //设置图片
                setImageResource(R.drawable.ic_arrow_upward)
                //图片自适应居中
                scaleType = ImageView.ScaleType.FIT_CENTER
                //设置点击事件
                setOnClickListener(onClickListener)
            },
            CoordinatorLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                //位于右下
                gravity = Gravity.BOTTOM or Gravity.END
                //边距16dp
                setMargins(0, 0,
                        resources.getDimensionPixelSize(R.dimen.dp_16),
                        resources.getDimensionPixelSize(R.dimen.dp_16)
                )
                //设置滑动隐藏行为
                behavior = ScaleDownShowBehavior(context, null)
            })
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