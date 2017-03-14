package me.odinaris.searchcar.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
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
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        setContentView(R.layout.act_login)

        et_password.hint = "d "
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

