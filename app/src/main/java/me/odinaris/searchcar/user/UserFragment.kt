package me.odinaris.searchcar.user


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import kotlinx.android.synthetic.main.frag_user.*
import me.odinaris.searchcar.R
import me.odinaris.searchcar.login.LoginActivity

class UserFragment : Fragment() {
    private val  REQUSET_CODE_LOGIN = 1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_user,container,false)
        return view

    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        if(isLogin()){ tv_user_name.text = BmobUser.getCurrentUser().username }
    }

    private fun initData() {
    }

    private fun initView() {
        if(isLogin()){ tv_user_name.text = BmobUser.getCurrentUser().username }
    }

    private fun initListener() {
        ll_login.setOnClickListener { checkLogin() }
        tv_signOut.setOnClickListener {
            BmobUser.logOut()
            tv_user_name.text = "立即登陆"
        }
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
    //若未登陆则跳转到登陆模块
    private fun gotoLogin() {
        startActivityForResult(Intent(activity,LoginActivity::class.java),REQUSET_CODE_LOGIN)
    }
    //若已登陆则跳转到用户设置模块
    private fun gotoUserSetting() {
    }
    private fun isLogin(): Boolean {
        val user = BmobUser.getCurrentUser()
        return user != null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUSET_CODE_LOGIN && resultCode == RESULT_OK && data!=null){
            val bundle = data.extras
            val name = bundle.getString("username")
            tv_user_name.text = name
        }
    }

}