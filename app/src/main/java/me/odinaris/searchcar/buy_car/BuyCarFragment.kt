package me.odinaris.searchcar.buy_car

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
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
import com.zaaach.citypicker.CityPickerActivity
import me.odinaris.searchcar.adapter.CarAdapter
import me.odinaris.searchcar.bean.CarIntro
import me.odinaris.searchcar.bean.ShelfCar
import me.odinaris.searchcar.utils.BmobUtils
import me.odinaris.searchcar.utils.FilterCarActivity
import java.util.*

class BuyCarFragment : Fragment() {
    private val REQUEST_CODE_FILTER = 1
    private val REQUEST_CODE_PICK_CITY = 0
    private var carList: ArrayList<CarIntro>? = ArrayList()
    private var city = ""
    private var loadingNum = 20
    private var skipNum = 0
    private var carMap = HashMap<String,String>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_car_buy,container,false)
        return view
    }
    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initData()//网络加载、数据请求操作
        initView()//适配器绑定等操作
        initClickListener()//监听器绑定操作
    }
    private fun initClickListener() {
        ll_filterBox.setOnClickListener({ startActivityForResult(Intent(context,FilterCarActivity::class.java),REQUEST_CODE_FILTER) })
        tv_location.setOnClickListener{
            startActivityForResult(Intent(context, CityPickerActivity::class.java),REQUEST_CODE_PICK_CITY) }
    }
    private fun initView() {
        tv_location.text = activity.getSharedPreferences("cityInfo", Context.MODE_PRIVATE).getString("city","合肥")
        et_search.clearFocus()
        val drawable = ContextCompat.getDrawable(context,R.drawable.ic_search)
        drawable.setBounds(0,0,40,40)
        et_search.setCompoundDrawables(drawable,null,null,null)
    }
    private fun initData() {
        city = activity.getSharedPreferences("cityInfo",Context.MODE_PRIVATE).getString("city","合肥")
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
                    ll_loading.visibility = View.GONE
                    rv_carList.visibility = View.VISIBLE
                }else{
                    val dialog = AlertDialog.Builder(context)
                    dialog.setMessage("数据请求出错！"+e.errorCode+e.message).show()
                }
            }
        })
    }
    //重写onActivityResult方法
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        if (requestCode == REQUEST_CODE_FILTER && resultCode == RESULT_OK && data!=null){
            val bundle = data.extras
            if(bundle.getString("selectedSort")!=""){
                val selectedSort = bundle.getString("selectedSort")
//                val selectedPrice = if(bundle.getString("selectedPrice")==("不限")) "" else ",价格"+bundle.getString("selectedPrice")
//                val selectedMileAge = if(bundle.getString("selectedMileAge")==("不限")) "" else ",里程"+bundle.getString("selectedMileAge")
                val selectedEmission = ",排放标准"+bundle.getString("selectedEmission")
                tv_filterResult.text = "$selectedSort$selectedEmission"
                carMap["city"] = city
                carMap["emission"] = bundle.getString("selectedEmission")
                carMap["sortRule"] = bundle.getString("selectedSort")
                BmobUtils.searchCar(carMap,carList,loadingNum,skipNum,rv_carList,ll_loading,context,true)
            }

        }else if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == Activity.RESULT_OK && data!=null){
            val city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY)
            tv_location.text = city
            val cityInfo = activity.getSharedPreferences("cityInfo",Context.MODE_PRIVATE)
            cityInfo.edit().putString("city", city).apply()//将用户当前选择城市存储在SharePreference中用于其他模块查询
            carMap["city"] = activity.getSharedPreferences("cityInfo",Context.MODE_PRIVATE).getString("city","合肥")
            BmobUtils.searchCar(carMap,carList,loadingNum,skipNum,rv_carList,ll_loading,context,true)
        }

    }

}