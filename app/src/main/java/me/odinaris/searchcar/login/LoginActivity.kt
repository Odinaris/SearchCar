package me.odinaris.searchcar.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.SaveListener
import me.odinaris.searchcar.R
import me.odinaris.searchcar.bean.userInfo
import kotlinx.android.synthetic.main.act_login.*
import me.odinaris.searchcar.utils.ProgressDialog
import me.odinaris.searchcar.main.MainActivity
import android.content.Intent
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import cn.bmob.v3.exception.BmobException
import me.odinaris.searchcar.utils.Input


class LoginActivity : AppCompatActivity() {
    private val user: userInfo? = null
    private var chars: CharSequence? = null
    private var start: Int = 0
    private var end: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)
        initView()
        initListener()
    }

    private fun initListener() {
        btn_submit.setOnClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()
            val dialog = AlertDialog.Builder(this)
            val user = userInfo()
            user.username = username
            user.setPassword(password)
            user.login(object : SaveListener<userInfo>() {
                override fun done(p0: userInfo?, e: BmobException?) {
                    if (e == null) {
                        Snackbar.make(ll_main, "登陆成功", Snackbar.LENGTH_LONG).setCallback(object : Snackbar.Callback() {
                            override fun onDismissed(sb: Snackbar?, event: Int) {
                                super.onDismissed(sb, event)
                                val bundle = Bundle()
                                bundle.putString("username",username)
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtras(bundle)
                                this@LoginActivity.setResult(RESULT_OK,intent)
                                this@LoginActivity.finish()
                            } }).show()
                    } else {
                        AlertDialog.Builder(this@LoginActivity).setTitle("登录失败")
                                .setMessage("错误信息码:" + e.errorCode + "\n" + "错误信息:" + e.message + "\n")
                                .setPositiveButton("知道了", null)
                                .show()
                    }
                }
            })
            tv_register.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
        tv_register.setOnClickListener{
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
    }
    private fun initView() {
        //设置标题栏返回键
        setBackButton()
    }
    //设置标题栏返回键
    private fun setBackButton() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
    private fun checkLogin(phone: String, password: String): Boolean{
//        if(isMobile(phone))
//            if(isPassword(password)) return "000"//手机号码&&密码符合规范
//            else return "001"//密码不符合规范
//        else return "002"//手机号码不符合规范
            return false

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
    //检测手机号码是否符合规范
    private fun isMobile(mobiles: String): Boolean {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        ------------------------------------------------
        13(老)号段：130、131、132、133、134、135、136、137、138、139
        14(新)号段：145、147
        15(新)号段：150、151、152、153、154、155、156、157、158、159
        17(新)号段：170、171、173、175、176、177、178
        18(3G)号段：180、181、182、183、184、185、186、187、188、189
        */
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
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


