package me.odinaris.searchcar.homepage


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.odinaris.searchcar.R
import kotlinx.android.synthetic.main.frag_homepage.*
import me.odinaris.searchcar.buy_car.BuyCarFragment
import me.odinaris.searchcar.rent_car.RentCarFragment
import me.odinaris.searchcar.sale_car_car.SaleCarFragment


/**
 * Created by Odinaris on 2017/3/5.
 */

class HomepageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_homepage,container,false)
        return view
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickListener()
    }

    private fun initClickListener() {
        bbl_car_buy.setOnClickListener({
            fragmentManager!!.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.main_container, BuyCarFragment())
                    .commit()
        })
        bbl_car_rent.setOnClickListener({
            fragmentManager!!.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.main_container, RentCarFragment())
                    .commit()
        })
        bbl_car_sale.setOnClickListener({
            fragmentManager!!.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.main_container, SaleCarFragment())
                    .commit()
        })

    }

    fun initView(){

    }
}