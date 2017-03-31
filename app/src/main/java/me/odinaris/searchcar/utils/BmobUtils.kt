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
    fun searchCar(priceLow : Int = 0, priceUp : Int = 999,
                  loadingNum : Int = 10, skipNum : Int = 0,
                  mileAgeLow : Int = 0, mileAgeUp : Int = 0,
                  city : String = "", vendor : String = "",
                  sortRule : String = "", emission : String = "",
                  rv : RecyclerView, pb : ProgressBar,
                  context : Context, canScroll : Boolean = false){
        val carIntroQuery = BmobQuery<ShelfCar>()
        val carList: ArrayList<CarIntro>? = ArrayList()
        carIntroQuery.addWhereNotEqualTo("name","")
        carIntroQuery.setLimit(loadingNum)
        if(sortRule=="") carIntroQuery.order("-objectId")
        else { }
        carIntroQuery.findObjects(object : FindListener<ShelfCar>(){
            override fun done(p0: MutableList<ShelfCar>?, e: BmobException?) {
                if(e==null){
                    p0!!.map { CarIntro(it.name, it.cover, it.registerTime,
                                it.mileAge, it.price, it.newPrice,it.objectId)
                    }.forEach { carList!!.add(it) }
                    val layoutManager = DisScrollLinearLayoutManager(context)
                    layoutManager.setScrollEnabled(false)
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