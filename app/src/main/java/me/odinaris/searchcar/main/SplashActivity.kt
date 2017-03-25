package me.odinaris.searchcar.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import cn.bmob.v3.Bmob
import me.odinaris.searchcar.R
import kotlinx.android.synthetic.main.act_start.*

/**
 * Created by Odinaris on 2017/3/3.
 */

class SplashActivity : Activity() {

    private var bmp: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_start)
        Bmob.initialize(this, "1e664726dca54fa3ba5667e97e25e9ed")
        initImage()
    }

    private fun initImage() {
        bmp = readBitMap(R.drawable.wallpaper_sj)
        iv_start.setImageBitmap(bmp)
        //设置缩放动画
        val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.anim_start)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                //可以在这里先进行某些操作
            }
            override fun onAnimationEnd(animation: Animation) {
                startActivity()
            }
            override fun onAnimationRepeat(animation: Animation) { }
        })
        iv_start.startAnimation(animation)
    }

    private fun startActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
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
