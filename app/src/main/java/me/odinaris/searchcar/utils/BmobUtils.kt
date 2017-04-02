package me.odinaris.searchcar.utils

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobQueryResult
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import me.odinaris.searchcar.adapter.CarAdapter
import me.odinaris.searchcar.bean.CarIntro
import me.odinaris.searchcar.bean.ShelfCar
import kotlin.collections.ArrayList

object BmobUtils{
    //二手车查询函数
    fun searchCar(car : HashMap<String,String>, carList: ArrayList<CarIntro>?,
                  loadingNum : Int, skipNum : Int,
                  rv : RecyclerView, loadingView : LinearLayout,
                  context : Context, canScroll : Boolean = true){
        val city = context.getSharedPreferences("cityInfo",Context.MODE_PRIVATE).getString("city","合肥")
        //val vendor = car["vendor"]
        val sortRule = if(car["sortRule"]!=null) car["sortRule"] else "最新上架"
        val emission = if(car["emission"]!=null) car["emission"] else "不限"
        //建立查询
        val cityQuery = BmobQuery<ShelfCar>()
        val vendorQuery = BmobQuery<ShelfCar>()
        val emissionQuery = BmobQuery<ShelfCar>()
        //城市限定查询
        cityQuery.addWhereEqualTo("city",city)
        //品牌限定查询
//        if(vendor!=null) //vendorQuery.addWhereContains("vendor",vendor)
//            vendorQuery.setSQL("select * from ShelfCar where vendor = $vendor")
//        else vendorQuery.addWhereNotEqualTo("vendor","")
        //排放标准限定查询
        when(emission) {
            "国二及以上" -> { emissionQuery.addWhereNotEqualTo("emissionStandard","") }
            "国三及以上" -> { emissionQuery.addWhereNotEqualTo("emissionStandard","") }
            "国四及以上" -> { emissionQuery.addWhereNotEqualTo("emissionStandard","国三") }
            "国五" -> { emissionQuery.addWhereEqualTo("emissionStandard","国五") }
            "不限" -> { emissionQuery.addWhereNotEqualTo("emissionStandard","") }}
        //对查询条件进行与运算
        val queries = ArrayList<BmobQuery<ShelfCar>>()
        queries.add(cityQuery)
        queries.add(vendorQuery)
        queries.add(emissionQuery)
        val carIntroQuery = BmobQuery<ShelfCar>()
        carIntroQuery.and(queries)
        carIntroQuery.setLimit(loadingNum)
        carIntroQuery.setSkip(skipNum)
        if (sortRule!=null){
            when(sortRule){
                "最新上架" -> { carIntroQuery.order("-registerTime") }
                "价格最高" -> { carIntroQuery.order("-price") }
                "价格最低" -> { carIntroQuery.order("price") }
                "里程最少" -> { carIntroQuery.order("mileAge") } }
        }else carIntroQuery.order("-objectId")
        loadingView.visibility = View.VISIBLE
        rv.visibility = View.GONE
        carIntroQuery.findObjects(object : FindListener<ShelfCar>(){
            override fun done(p0: MutableList<ShelfCar>?, e: BmobException?) {
                loadingView.visibility = View.GONE
                if(e==null){
                    if(p0!=null){
                        if(skipNum==0) carList!!.clear()
                        p0.map { CarIntro(it.name, it.cover, it.registerTime,
                                it.mileAge, it.price, it.newPrice,it.objectId)
                        }.forEach {
                            carList!!.add(it) }
                        if(carList!!.size!=0){
                            val layoutManager = DisScrollLinearLayoutManager(context)
                            layoutManager.setScrollEnabled(canScroll)
                            rv.layoutManager = layoutManager
                            rv.adapter = CarAdapter(carList,context)
                            rv.visibility = View.VISIBLE
                        }
                    }else{
                        rv.visibility = View.VISIBLE
                        val dialog = AlertDialog.Builder(context)
                        dialog.setMessage("没有车源！").setPositiveButton("确认",null).show()
                    }
                }else{
                    val dialog = AlertDialog.Builder(context)
                    dialog.setMessage("数据请求出错！"+e.errorCode+e.message).setPositiveButton("确认",null).show()
                }
            }
        })
    }
}