package me.odinaris.searchcar.user


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import me.odinaris.searchcar.R

/**
 * Created by Odinaris on 2017/3/5.
 */

class UserFragment : Fragment() {

    private val user_name : TextView by bindView(R.id.tv_user_name)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_user,container,false)
        return view

    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        user_name.text = "dd"
    }


}