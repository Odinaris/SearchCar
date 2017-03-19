package me.odinaris.searchcar.login

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import cn.bmob.v3.BmobUser
import me.odinaris.searchcar.R
import me.odinaris.searchcar.model.userInfo
import kotlinx.android.synthetic.main.act_login.*

class LoginActivity : AppCompatActivity() {
    private val user: userInfo? = null
    private var chars: CharSequence? = null
    private var start: Int = 0
    private var end: Int = 0
    private val telRegex = "[1][34578]\\d{9}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_login)
        initView()
        initListener()
    }

    private fun initListener() {
        btn_submit.setOnClickListener { checkLogin() }
        //用于实时监测输入变化反馈信息（较复杂，目前没时间完成）
        et_phone.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence, var2: Int, var3: Int, var4: Int) {
                chars = s
                btn_submit.text = chars
            }
            override fun onTextChanged(var1: CharSequence, var2: Int, var3: Int, var4: Int) {}
            override fun afterTextChanged(s: Editable) {
//                start = et_phone.selectionStart
//                end = et_phone.selectionEnd
//                if(chars!!.length>11){
////                    til_phone.isErrorEnabled = true
////                    til_phone.error = "手机号码必须为11位!"
////                    s.delete(start-1,end)
//                    val ad = AlertDialog.Builder(this@LoginActivity)
//                    ad.setTitle("手机号码必须为11位!").show()
//                    btn_submit.isClickable = false
//                    btn_submit.setBackgroundColor(Color.parseColor("#cccccc"))
//                }else if(chars!!.length<=11){
////                    til_phone.isErrorEnabled = false
////                    til_phone.error = ""
//                    btn_submit.isClickable = true
//                    btn_submit.setBackgroundColor(R.color.colorPrimary)
//                }
            }
        })
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

    private fun checkLogin() {
        var phone = et_phone.text
        var password = et_password.text
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



}

