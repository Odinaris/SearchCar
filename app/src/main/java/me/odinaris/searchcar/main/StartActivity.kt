package me.odinaris.searchcar.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

import butterknife.ButterKnife
import butterknife.bindView
import me.odinaris.searchcar.R

/**
 * Created by Odinaris on 2017/3/3.
 */

class StartActivity : Activity() {

    val iv_start: ImageView by bindView(R.id.iv_start)
    private var bmp: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_start)
        ButterKnife.bind(this)
        initImage()
    }

    private fun initImage() {
        bmp = readBitMap(R.drawable.wallpaper_sj)
        iv_start!!.setImageBitmap(bmp)
        //设置缩放动画
        val animation = AnimationUtils.loadAnimation(this@StartActivity, R.anim.anim_start)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                //可以在这里先进行某些操作
            }
            override fun onAnimationEnd(animation: Animation) {
                startActivity()
            }
            override fun onAnimationRepeat(animation: Animation) { }
        })
        iv_start!!.startAnimation(animation)
    }

    private fun startActivity() {
        startActivity(Intent(this@StartActivity, MainActivity::class.java))
        finish()
        overridePendingTransition(R.anim.scale_in, R.anim.scale_out)
    }

    private fun readBitMap(resId: Int): Bitmap {
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.RGB_565
        val `is` = resources.openRawResource(resId)
        return BitmapFactory.decodeStream(`is`, null, opt)
    }

    override fun onStop() {
        super.onStop()
        bmp!!.recycle()
        System.gc()
    }
}
