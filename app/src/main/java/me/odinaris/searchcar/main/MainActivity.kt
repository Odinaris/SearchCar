package me.odinaris.searchcar.main

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import butterknife.ButterKnife
import butterknife.bindView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import me.odinaris.searchcar.R
import java.util.*

class MainActivity : AppCompatActivity() {

    val main_navigator: BottomNavigationBar by bindView(R.id.main_navigator)
    val main_container: LinearLayout by bindView(R.id.main_container)
    var fragmentsList: ArrayList<Fragment> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)//状态栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)//可不加
        }
        setContentView(R.layout.act_main)
        ButterKnife.bind(this)
        initView()
    }

    private fun initView() {

    }
}
