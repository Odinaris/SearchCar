package me.odinaris.searchcar.buy_car


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import me.odinaris.searchcar.R

/**
 * Created by Odinaris on 2017/3/5.
 */

class BuyCarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_car_buy,container,false)
        ButterKnife.bind(this,view)
        return view
    }
}