package me.odinaris.searchcar.main

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Window
import android.view.WindowManager
import butterknife.ButterKnife
import butterknife.bindView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import me.odinaris.searchcar.R
import me.odinaris.searchcar.homepage.HomepageFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    val main_navigator : BottomNavigationBar by bindView(R.id.main_navigator)
    var fragmentsList = ArrayList<Fragment>()
    var homepage = HomepageFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)//状态栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)//可不加
        }
        setContentView(R.layout.act_main)
        ButterKnife.bind(this)
        setDefaultFragment()
        initView()
    }

    private fun initView() {
        fragmentsList.add(0, homepage)
        fragmentsList.add(1, homepage)
        fragmentsList.add(2, homepage)
        main_navigator
                .addItem(BottomNavigationItem(R.drawable.ic_homepage,"主页"))
                .addItem(BottomNavigationItem(R.drawable.ic_homepage,"主页"))
                .addItem(BottomNavigationItem(R.drawable.ic_homepage,"主页"))
                .setFirstSelectedPosition(1).initialise()
        main_navigator
                .setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{
                    override fun onTabUnselected(position: Int) {}

                    override fun onTabSelected(position: Int) {
                        val fm = supportFragmentManager
                        val transaction = fm.beginTransaction()
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        when(position){
                            0 -> {
                                hideAllFragments(transaction)
                                if (!fragmentsList[position].isAdded) {
                                    transaction.add(R.id.main_container, homepage).show(homepage)
                                } else {
                                    transaction.show(homepage)
                                }
                            }
                            1 -> {
                                hideAllFragments(transaction)
                                if (!fragmentsList[position].isAdded) {
                                    transaction.add(R.id.main_container, homepage).show(homepage)
                                } else {
                                    transaction.show(homepage)
                                }
                            }
                            2 -> {
                                hideAllFragments(transaction)
                                if (!fragmentsList[position].isAdded) {
                                    transaction.add(R.id.main_container, homepage).show(homepage)
                                } else {
                                    transaction.show(homepage)
                                }
                            }
                        }
                        transaction.commit()
                    }

                    override fun onTabReselected(position: Int) {}
                })
    }

    private fun setDefaultFragment() {
        val fm = supportFragmentManager
        val mFragmentTranscation = fm.beginTransaction()
        mFragmentTranscation.add(R.id.main_container, homepage)
        mFragmentTranscation.commit()
    }

    fun hideAllFragments(transaction: FragmentTransaction) {
        for (i in fragmentsList.indices) {
            transaction.hide(fragmentsList[i])
        }
    }
}
