package me.odinaris.searchcar.homepage


import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AlertDialogLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import me.odinaris.searchcar.R
import kotlinx.android.synthetic.main.frag_homepage.*
import me.odinaris.searchcar.adapter.CarAdapter
import me.odinaris.searchcar.bean.CarIntro
import me.odinaris.searchcar.bean.ShelfCar
import me.odinaris.searchcar.buy_car.BuyCarFragment
import me.odinaris.searchcar.rent_car.RentCarFragment
import me.odinaris.searchcar.sale_car_car.SaleCarFragment


/**
 * Created by Odinaris on 2017/3/5.
 */

class HomepageFragment : Fragment() {
    private var carList: ArrayList<CarIntro>? = ArrayList<CarIntro>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_homepage,container,false)
        return view
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initData()//网络加载、数据请求操作
        initView()//适配器绑定等操作
        initClickListener()//监听器绑定操作
    }

    fun initData(){
        val carIntroQuery = BmobQuery<ShelfCar>()
        carIntroQuery.addWhereEqualTo("applyPhone","400-057-8600")
        carIntroQuery.setLimit(6)
        carIntroQuery.order("-registerTime")
        carIntroQuery.findObjects(object : FindListener<ShelfCar>(){
            override fun done(p0: MutableList<ShelfCar>?, e: BmobException?) {
                if(e==null){
                    p0!!.map { CarIntro(it.name, it.imgUrl1, it.registerTime, it.mileAge, it.price, it.newPrice)
                    }.forEach { carList!!.add(it) }
                    rv_hotCar.layoutManager = LinearLayoutManager(context)
                    rv_hotCar.adapter = CarAdapter(carList!!,context)
                }else{
                    val dialog = AlertDialog.Builder(context)
                    dialog.setMessage("数据请求出错！"+e.errorCode+e.message).show()

                }
            }
        })


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