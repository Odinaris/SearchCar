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
import me.odinaris.searchcar.model.userInfo
import kotlinx.android.synthetic.main.act_login.*
import me.odinaris.searchcar.utils.ProgressDialog
import me.odinaris.searchcar.main.MainActivity
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import cn.bmob.v3.exception.BmobException


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
            val phone = et_phone.text.toString()
            val password = et_password.text.toString()
            val stateCode = checkLogin(phone,password)
            val dialog = AlertDialog.Builder(this)
            //val progress = ProgressDialog.showDialog(this)//加载动画dialog
            when(stateCode){
                "000" -> {
                    //progress.show()
                    hideSoftInput()//如果符合登陆条件，则隐藏软键盘
                    val user = userInfo()
                    user.username = phone
                    user.setPassword(password)
                    user.login(object : SaveListener<userInfo>() {
                        //注意：不能用save方法进行注册
                        override fun done(s: userInfo?, e: BmobException?) {
                            if (e == null) {
                                Snackbar.make(ll_main, "登录成功", Snackbar.LENGTH_LONG).setCallback(object : Snackbar.Callback() {
                                    override fun onDismissed(sb: Snackbar?, event: Int) {
                                        super.onDismissed(sb, event)
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                }).show()
                            } else {
                                val builder = AlertDialog.Builder(this@LoginActivity)
                                builder.setTitle("登录失败")
                                        .setMessage("错误信息码:" + e.errorCode + "\n" + "错误信息:" + e.message + "\n")
                                        .setPositiveButton("知道了", null)
                                        .show()
                            }
                        }
                    })

                }
                "001" -> { dialog.setTitle("警告").setMessage("密码长度需要在6-12位之间！").show()}
                "002" -> { dialog.setTitle("警告").setMessage("手机号码输入不正确！").show()}
            }
        }
        tv_register.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        //用于实时监测输入变化反馈信息（较复杂，目前没时间完成）
//        et_phone.addTextChangedListener(object: TextWatcher{
//            override fun beforeTextChanged(s: CharSequence, var2: Int, var3: Int, var4: Int) {
//                chars = s
//            }
//            override fun onTextChanged(var1: CharSequence, var2: Int, var3: Int, var4: Int) {}
//            override fun afterTextChanged(s: Editable) {
//                start = et_phone.selectionStart
//                end = et_phone.selectionEnd
//                if(chars!!.length>11){
//                    til_phone.isErrorEnabled = true
//                    til_phone.error = "手机号码必须为11位!"
//                    s.delete(start-1,end)
//                    val ad = AlertDialog.Builder(this@LoginActivity)
//                    ad.setTitle("手机号码必须为11位!").show()
//                    btn_submit.isClickable = false
//                    btn_submit.setBackgroundColor(Color.parseColor("#cccccc"))
//                }else if(chars!!.length<=11){
//                    til_phone.isErrorEnabled = false
//                    til_phone.error = ""
//                    btn_submit.isClickable = true
//                    btn_submit.setBackgroundColor(R.color.colorPrimary)
//                }
//            }
//        })
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

    private fun checkLogin(phone: String, password: String): String{
        if(isMobile(phone))
            if(isPassword(password)) return "000"//手机号码&&密码符合规范
            else return "001"//密码不符合规范
        else return "002"//手机号码不符合规范

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

    private fun hideSoftInput(){
        val view = window.peekDecorView()
        if(view!=null){
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(view.windowToken,0)
        }

    }
}


