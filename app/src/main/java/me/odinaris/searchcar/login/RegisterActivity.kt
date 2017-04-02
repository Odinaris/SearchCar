package me.odinaris.searchcar.login

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.SaveListener
import me.odinaris.searchcar.R
import me.odinaris.searchcar.bean.userInfo
import kotlinx.android.synthetic.main.act_register.*
import me.odinaris.searchcar.main.MainActivity
import android.content.Intent
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import cn.bmob.v3.exception.BmobException
import me.odinaris.searchcar.utils.Input


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)//状态栏透明
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)//可不加
//        }
        setContentView(R.layout.act_register)
        initView()
        initListener()
    }
    private fun initListener() {
        btn_submit.setOnClickListener {
            val username = et_username.text.toString()
            val phone = et_phone.text.toString()
            val password = et_password.text.toString()
            val stateCode = checkRegister(username,phone,password)
            val dialog = AlertDialog.Builder(this)
            when(stateCode){
                "000" -> {
                    Input.hideSoftInput(this)//如果符合登陆条件，则隐藏软键盘
                    val user = userInfo()
                    user.username = username
                    user.mobilePhoneNumber = phone
                    user.setPassword(password)
                    user.signUp(object : SaveListener<userInfo>() {
                        override fun done(s: userInfo?, e: BmobException?) {
                            if (e == null) {
                                Snackbar.make(ll_main, "注册成功", Snackbar.LENGTH_LONG)
                                        .setCallback(object : Snackbar.Callback() {
                                    override fun onDismissed(sb: Snackbar?, event: Int) {
                                        super.onDismissed(sb, event)
                                        //signUp方法在注册的同时已实现了保存新用户信息到本地功能
                                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                                    } }).show() }
                            else {
                                AlertDialog.Builder(this@RegisterActivity).setTitle("注册s失败")
                                        .setMessage("错误信息码:" + e.errorCode + "\n" + "错误信息:" + e.message + "\n")
                                        .setPositiveButton("知道了", null)
                                        .show() } } }) }
                "001" -> { dialog.setTitle("警告").setMessage("密码长度需要在6-12位之间！").setPositiveButton("确定", null).show()}
                "002" -> { dialog.setTitle("警告").setMessage("手机号码输入不正确！").setPositiveButton("确定", null).show()}
                "003" -> { dialog.setTitle("警告").setMessage("用户名不能为空！").setPositiveButton("确定", null).show()}
            }
        }
        tv_login.setOnClickListener{ startActivity(Intent(this@RegisterActivity, LoginActivity::class.java)) }
    }
    private fun initView() {
        setBackButton()//设置标题栏返回键
    }
    //设置标题栏返回键
    private fun setBackButton() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
    private fun checkRegister(username: String, phone: String, password: String): String{
        if(isUsername(username))
            if(isPhone(phone))
                if(isPassword(password)) return "000"//用户名&&手机号码&&密码符合规范
                else return "001"//密码不符合规范
            else return "002"//手机号码不符合规范
        else return "003"//用户名不符合规范
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish() // back button
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //检测用户名是否符合规范
    private fun isUsername(username: String): Boolean{ return !username.isEmpty() }
    //检测手机号码是否符合规范
    private fun isPhone(mobiles: String): Boolean {
        val telRegex = "[1][34578]\\d{9}"
        if (mobiles.isEmpty()) return false
        else return mobiles.matches(telRegex.toRegex())
    }
    //检测密码长度是否在6-12位之间
    private fun isPassword(password: String): Boolean {
        val passwordRegex = "\\w{6,12}"
        if(password.isEmpty()) return false
        else return password.matches(passwordRegex.toRegex())
    }
    override fun onStop() {
        super.onStop()
        finish()
    }
}


