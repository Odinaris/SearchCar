package me.odinaris.searchcar.main

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Window
import android.view.WindowManager
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import me.odinaris.searchcar.R
import me.odinaris.searchcar.buy_car.BuyCarFragment
import me.odinaris.searchcar.homepage.HomepageFragment
import me.odinaris.searchcar.sale_car_car.SaleCarFragment
import me.odinaris.searchcar.user.UserFragment
import java.util.*
import kotlinx.android.synthetic.main.act_main.*

class MainActivity : AppCompatActivity() {

    val fragmentsList = ArrayList<Fragment>()
    val homepage = HomepageFragment()
    val buyCar = BuyCarFragment()
    val saleCar = SaleCarFragment()
    val user = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)//状态栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)//可不加
        }
        setContentView(R.layout.act_main)
        setDefaultFragment()
        initView()
    }

    private fun initView() {
        fragmentsList.add(0, homepage)
        fragmentsList.add(1, buyCar)
        fragmentsList.add(2, saleCar)
        fragmentsList.add(3, user)
        main_navigator
                .addItem(BottomNavigationItem(R.drawable.ic_homepage,"主页"))
                .addItem(BottomNavigationItem(R.drawable.ic_car_buy,"买车"))
                .addItem(BottomNavigationItem(R.drawable.ic_car_sale,"卖车"))
                .addItem(BottomNavigationItem(R.drawable.ic_user,"我的"))
                .setFirstSelectedPosition(0).initialise()
        main_navigator
                .setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{
                    override fun onTabUnselected(position: Int) {}
                    override fun onTabSelected(position: Int) {
                        val SFM = supportFragmentManager!!
                        val transaction = SFM.beginTransaction()!!
//                        transaction.setCustomAnimations(
//                                R.animator.slide_in_left,R.animator.slide_in_right)
//                        transaction.setCustomAnimations(
//                                R.anim.slide_in_left,R.anim.slide_out_right)
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        hideAllFragments(transaction)
                        when(position){
                            0 -> {
                                if (!homepage.isAdded) {
                                    transaction.add(R.id.main_container, homepage).show(homepage)
                                } else {
                                    transaction.show(homepage) }
                            }
                            1 -> {
                                if (!buyCar.isAdded) {
                                    transaction.add(R.id.main_container, buyCar).show(buyCar)
                                } else {
                                    transaction.show(buyCar) }
                            }
                            2 -> {
                                if (!saleCar.isAdded) {
                                    transaction.add(R.id.main_container, saleCar).show(saleCar)
                                } else {
                                    transaction.show(saleCar) }
                            }
                            3 -> {
                                if (!user.isAdded) {
                                    transaction.add(R.id.main_container, user).show(user)
                                } else {
                                    transaction.show(user) }
                            }
                        }
                        transaction.commit()
                    }
                    override fun onTabReselected(position: Int) {}
                })
    }

    private fun setDefaultFragment() {
        if(!homepage.isAdded){
            val SFM = supportFragmentManager!!
            val transaction = SFM.beginTransaction()!!
            transaction.add(R.id.main_container, homepage)
            transaction.commit()
        }
    }
    fun hideAllFragments(transaction: FragmentTransaction) {
        for (i in fragmentsList.indices) {
            transaction.hide(fragmentsList[i])
        }
    }
}
