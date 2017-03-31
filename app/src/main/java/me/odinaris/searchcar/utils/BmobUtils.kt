package me.odinaris.searchcar.utils

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import me.odinaris.searchcar.adapter.CarAdapter
import me.odinaris.searchcar.bean.CarIntro
import me.odinaris.searchcar.bean.ShelfCar
import java.util.ArrayList

object BmobUtils{
    //二手车查询函数
    fun searchCar(loadingNum : Int, skipNum : Int,
                  city : String, vendor : String,
                  sortRule : String, emission : String,
                  rv : RecyclerView, pb : ProgressBar,
                  context : Context, canScroll : Boolean = true){

        //售价范围查询
//        val priceLowQuery = BmobQuery<ShelfCar>()
//        val priceUpQuery = BmobQuery<ShelfCar>()
//        priceLowQuery.addWhereGreaterThanOrEqualTo("price",priceLow)
//        priceUpQuery.addWhereLessThanOrEqualTo("Price",priceUp)

        //里程范围查询
//        val mileAgeLowQuery = BmobQuery<ShelfCar>()
//        val mileAgeUpQuery = BmobQuery<ShelfCar>()
//        mileAgeLowQuery.addWhereGreaterThanOrEqualTo("mileAge",mileAgeLow)
//        mileAgeUpQuery.addWhereLessThanOrEqualTo("mileAge",mileAgeUp)

        //城市限定查询
        val cityQuery = BmobQuery<ShelfCar>()
        if(city!=""){
            cityQuery.addWhereEqualTo("city",city)
        }else cityQuery.addWhereNotEqualTo("city","")

        //品牌限定查询
        val vendorQuery = BmobQuery<ShelfCar>()
        if(vendor!=""){
            vendorQuery.addWhereContains("vendor",vendor)
        }else vendorQuery.addWhereNotEqualTo("vendor","")

        //排放标准限定查询
        val emissionQuery = BmobQuery<ShelfCar>()
        when(emission){
            "国二及以上" -> { emissionQuery.addWhereNotEqualTo("emissionStandard","") }
            "国三及以上" -> { emissionQuery.addWhereNotEqualTo("emissionStandard","") }
            "国四及以上" -> { emissionQuery.addWhereNotEqualTo("emissionStandard","国三") }
            "国五" -> { emissionQuery.addWhereEqualTo("emissionStandard","国五") }
            "不限" -> { emissionQuery.addWhereNotEqualTo("emissionStandard","") }}

        val queries = ArrayList<BmobQuery<ShelfCar>>()
//        queries.add(priceLowQuery)
//        queries.add(priceUpQuery)
//        queries.add(mileAgeLowQuery)
//        queries.add(mileAgeUpQuery)
        queries.add(cityQuery)
        queries.add(vendorQuery)
        queries.add(emissionQuery)
        val carIntroQuery = BmobQuery<ShelfCar>()
        carIntroQuery.and(queries)
        val carList: ArrayList<CarIntro>? = ArrayList()
        carIntroQuery.setLimit(loadingNum)
        carIntroQuery.setSkip(skipNum)
        when(sortRule){
            "" -> { carIntroQuery.order("-objectId") }
            "最新上架" -> { carIntroQuery.order("-registerTime") }
            "价格最高" -> { carIntroQuery.order("-price") }
            "价格最低" -> { carIntroQuery.order("price") }
            "里程最少" -> { carIntroQuery.order("mileAge") } }
        carIntroQuery.findObjects(object : FindListener<ShelfCar>(){
            override fun done(p0: MutableList<ShelfCar>?, e: BmobException?) {
                if(e==null){
                    p0!!.map { CarIntro(it.name, it.cover, it.registerTime,
                                it.mileAge, it.price, it.newPrice,it.objectId)
                    }.forEach { carList!!.add(it) }
                    val layoutManager = DisScrollLinearLayoutManager(context)
                    layoutManager.setScrollEnabled(canScroll)
                    rv.layoutManager = layoutManager
                    rv.adapter = CarAdapter(carList!!,context)
                    pb.visibility = View.GONE
                    rv.visibility = View.VISIBLE
                }else{
                    val dialog = AlertDialog.Builder(context)
                    dialog.setMessage("数据请求出错！"+e.errorCode+e.message).show()
                }
            }
        })

    }
}