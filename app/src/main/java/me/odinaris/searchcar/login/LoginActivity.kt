package me.odinaris.searchcar.login

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import butterknife.bindView
import me.odinaris.searchcar.R

/**
 * Created by Odinaris on 2017/3/13.
 */

class LoginActivity : AppCompatActivity() {
    private val phone: EditText by bindView(R.id.et_phone)
    private val password: EditText by bindView(R.id.et_password)
    private val submit: Button by bindView(R.id.btn_submit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        setContentView(R.layout.act_login)

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