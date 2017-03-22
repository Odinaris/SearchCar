package me.odinaris.searchcar.utils

import android.app.Dialog
import android.content.Context
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import android.widget.ImageView
import me.odinaris.searchcar.R


/**
 * Created by Odinaris on 2017/3/20.
 */
class ProgressDialog : Dialog {

    private var context1: Context? = null
    private var ivProgress: ImageView? = null


    constructor(context: Context) : super(context) {
        this.context1 = context
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        this.context1 = context

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && dialog != null) {
//            ivProgress = dialog!!.findViewById(R.id.ivProgress) as ImageView
//            val animation = AnimationUtils.loadAnimation(context1, R.anim.dialog_progress_anim)
//            ivProgress!!.startAnimation(animation)
        }
    }

    companion object {
        private var dialog: ProgressDialog? = null
        //显示dialog的方法
        fun showDialog(context: Context): ProgressDialog {
            dialog = ProgressDialog(context, R.style.ProgressDialog)//dialog样式
            dialog!!.setContentView(R.layout.dialog_progress)//dialog布局文件
            dialog!!.setCanceledOnTouchOutside(false)//点击外部不允许关闭dialog
            return dialog as ProgressDialog
        }
    }
}