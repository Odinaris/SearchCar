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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置标题栏返回键
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        setContentView(R.layout.act_login)
        btn_submit.isClickable = false
        et_password.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(var1: CharSequence, var2: Int, var3: Int, var4: Int) {

            }

            override fun onTextChanged(var1: CharSequence, var2: Int, var3: Int, var4: Int) {
                btn_submit.isClickable = true
            }

            override fun afterTextChanged(var1: Editable) {

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

