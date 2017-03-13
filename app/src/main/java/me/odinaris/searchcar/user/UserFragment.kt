package me.odinaris.searchcar.user


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import me.odinaris.searchcar.R
import me.odinaris.searchcar.login.LoginActivity

/**
 * Created by Odinaris on 2017/3/5.
 */

class UserFragment : Fragment() {

    private val user_name : TextView by bindView(R.id.tv_user_name)
    private val login_box : LinearLayout by bindView(R.id.ll_login)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_user,container,false)
        return view

    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        login_box.setOnClickListener { checkLogin() }
    }

    //检查用户登陆状态并执行相应操作
    private fun checkLogin() {
        if(isLogin()){
            //若当前用户已登陆，跳转到用户设置界面
            gotoUserSetting()
        }else{
            //若当前用户未登陆，跳转到用户登陆界面
            gotoLogin()
        }
    }

    private fun gotoLogin() {
        startActivity(Intent(activity,LoginActivity::class.java))
    }

    private fun gotoUserSetting() {
    }

    private fun isLogin(): Boolean { return false }


}