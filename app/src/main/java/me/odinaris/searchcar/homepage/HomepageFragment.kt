package me.odinaris.searchcar.homepage


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.odinaris.searchcar.R

/**
 * Created by Odinaris on 2017/3/5.
 */

class HomepageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_homepage,container,false)
        return view
    }
}