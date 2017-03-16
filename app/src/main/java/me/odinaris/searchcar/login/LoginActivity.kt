package me.odinaris.searchcar.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import cn.bmob.v3.BmobUser
import me.odinaris.searchcar.R
import me.odinaris.searchcar.model.userInfo
import kotlinx.android.synthetic.main.act_login.*

/**
 * Created by Odinaris on 2017/3/13.
 */

class LoginActivity : AppCompatActivity() {
    private val user: userInfo? = null
    private var chars: CharSequence? = null
    private var start: Int = 0
    private var end: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置标题栏返回键
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        setContentView(R.layout.act_login)
        //btn_submit.isClickable = false

        et_phone.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence, var2: Int, var3: Int, var4: Int) {
                chars = s
                btn_submit.text = chars
            }

            override fun onTextChanged(var1: CharSequence, var2: Int, var3: Int, var4: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                start = et_phone.selectionStart
                end = et_phone.selectionEnd
                if(chars!!.length>11){
                    til_phone.isErrorEnabled = true
                    til_phone.error = "手机号码必须为11位!"
                    s.delete(start-1,end)
                }else if(chars!!.length<=11){
//                    til_phone.isErrorEnabled = false
//                    til_phone.error = ""
                }
            }
        })
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

