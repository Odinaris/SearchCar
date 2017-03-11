package me.odinaris.searchcar.rent_car


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.odinaris.searchcar.R

/**
 * Created by Odinaris on 2017/3/5.
 */

class RentCarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater.inflate(R.layout.frag_car_rent,container,false)
        return view
    }
}