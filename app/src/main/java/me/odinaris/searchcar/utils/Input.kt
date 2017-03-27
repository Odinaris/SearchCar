package me.odinaris.searchcar.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Created by Odinaris on 2017/3/27.
 */
object Input {
     fun hideSoftInput(act: Activity){
        val view = act.window.peekDecorView()
        if(view!=null){
            val im = act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}