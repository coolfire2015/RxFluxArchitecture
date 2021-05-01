//package com.huyingbao.module.common.utils
//
//import android.content.Context
//import android.view.Gravity
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.coordinatorlayout.widget.CoordinatorLayout
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.huyingbao.core.test.R
//import com.huyingbao.module.common.behavior.ScaleDownShowBehavior
//
///**
// * 公用添加[FloatingActionButton]方法
// */
//fun ViewGroup.addFloatingActionButton(context: Context, onClickListener: View.OnClickListener) {
//    addView(
//            FloatingActionButton(context).apply {
//                //设置图片
//                setImageResource(R.drawable.ic_arrow_upward)
//                //图片自适应居中
//                scaleType = ImageView.ScaleType.FIT_CENTER
//                //设置点击事件
//                setOnClickListener(onClickListener)
//            },
//            CoordinatorLayout.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            ).apply {
//                //位于右下
//                gravity = Gravity.BOTTOM or Gravity.END
//                //边距16dp
//                setMargins(0, 0,
//                        resources.getDimensionPixelSize(R.dimen.dp_16),
//                        resources.getDimensionPixelSize(R.dimen.dp_16)
//                )
//                //设置滑动隐藏行为
//                behavior = ScaleDownShowBehavior(context, null)
//            })
//}