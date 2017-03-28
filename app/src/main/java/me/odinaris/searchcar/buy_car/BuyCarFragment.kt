package me.odinaris.searchcar.buy_car

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.odinaris.searchcar.R
import kotlinx.android.synthetic.main.frag_car_buy.*
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import me.odinaris.searchcar.adapter.CarAdapter
import me.odinaris.searchcar.bean.CarIntro
import me.odinaris.searchcar.bean.ShelfCar
import me.odinaris.searchcar.utils.FilterCarActivity
import java.util.*

class BuyCarFragment : Fragment() {
    private val REQUEST_CODE_FILTER = 0
    private var carList: ArrayList<CarIntro>? = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_car_buy,container,false)
        return view
    }
    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initView()//适配器绑定等操作
        initClickListener()//监听器绑定操作
        initData()//网络加载、数据请求操作
    }
    private fun initClickListener() {
        ll_filterBox.setOnClickListener({ startActivityForResult(Intent(context,FilterCarActivity::class.java),REQUEST_CODE_FILTER) })
    }
    private fun initView() {
        et_search.clearFocus()
        val drawable = ContextCompat.getDrawable(context,R.drawable.ic_search)
        drawable.setBounds(0,0,40,40)
        et_search.setCompoundDrawables(drawable,null,null,null)
    }
    private fun initData() {
        val carIntroQuery = BmobQuery<ShelfCar>()
        carIntroQuery.addWhereNotEqualTo("objectId","")
        carIntroQuery.setLimit(20)
        carIntroQuery.order("-objectId")
        carIntroQuery.findObjects(object : FindListener<ShelfCar>(){
            override fun done(p0: MutableList<ShelfCar>?, e: BmobException?) {
                if(e==null){
                    p0!!.map { CarIntro(it.name, it.cover, it.registerTime,
                            it.mileAge, it.price, it.newPrice,it.objectId)
                    }.forEach { carList!!.add(it) }
                    rv_carList.layoutManager = LinearLayoutManager(context)
                    rv_carList.adapter = CarAdapter(carList!!,context)
                }else{
                    val dialog = AlertDialog.Builder(context)
                    dialog.setMessage("数据请求出错！"+e.errorCode+e.message).show()
                }
            }
        })
    }
    //重写onActivityResult方法
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent) {
        if (requestCode == REQUEST_CODE_FILTER && resultCode == RESULT_OK){
            val bundle = data.extras
            val selectedSort = bundle.getString("selectedSort")
            val selectedPrice = if(bundle.getString("selectedPrice")==("不限")) "" else ",价格"+bundle.getString("selectedPrice")
            val selectedMileAge = if(bundle.getString("selectedMileAge")==("不限")) "" else ",里程"+bundle.getString("selectedMileAge")
            val selectedEmission = if(bundle.getString("selectedEmission")==("不限")) "" else ",排放标准"+bundle.getString("selectedEmission")
            tv_filterResult.text = "$selectedSort$selectedPrice$selectedMileAge$selectedEmission"
        }
    }
}