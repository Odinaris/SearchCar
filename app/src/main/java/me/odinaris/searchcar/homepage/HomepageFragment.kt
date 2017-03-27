package me.odinaris.searchcar.homepage


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
import android.widget.TextView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.zaaach.citypicker.CityPickerActivity
import me.odinaris.searchcar.R
import kotlinx.android.synthetic.main.frag_homepage.*
import me.odinaris.searchcar.adapter.CarAdapter
import me.odinaris.searchcar.bean.CarIntro
import me.odinaris.searchcar.bean.ShelfCar
import me.odinaris.searchcar.buy_car.BuyCarFragment
import me.odinaris.searchcar.rent_car.RentCarFragment
import me.odinaris.searchcar.sale_car_car.SaleCarFragment
import me.odinaris.searchcar.utils.Input


class HomepageFragment : Fragment() {
    private val REQUEST_CODE_PICK_CITY = 0
    private var carList: ArrayList<CarIntro>? = ArrayList()

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
        carIntroQuery.addWhereNotEqualTo("name","")
        carIntroQuery.setLimit(10)
        carIntroQuery.order("-objectId")
        carIntroQuery.findObjects(object : FindListener<ShelfCar>(){
            override fun done(p0: MutableList<ShelfCar>?, e: BmobException?) {
                if(e==null){
                    p0!!.map { CarIntro(it.name, it.cover, it.registerTime, it.mileAge, it.price, it.newPrice)
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
//        bbl_car_buy.setOnClickListener({
//            fragmentManager!!.beginTransaction()
//                    .addToBackStack(null)
//                    .replace(R.id.main_container, BuyCarFragment())
//                    .commit()
//        })
//        bbl_car_rent.setOnClickListener({
//            fragmentManager!!.beginTransaction()
//                    .addToBackStack(null)
//                    .replace(R.id.main_container, RentCarFragment())
//                    .commit()
//        })
//        bbl_car_sale.setOnClickListener({
//            fragmentManager!!.beginTransaction()
//                    .addToBackStack(null)
//                    .replace(R.id.main_container, SaleCarFragment())
//                    .commit()
//        })

        ll_location.setOnClickListener({
            startActivityForResult(Intent(context,CityPickerActivity::class.java),REQUEST_CODE_PICK_CITY)
        })

    }



    //重写onActivityResult方法
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            val city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY)
            tv_location.text = city
            //将用户当前选择城市存储在SharePreference中用于其他模块查询
            //当选择城市之后更新当前车源列表
        }
    }

    fun initView(){
        sv_searchCar.onActionViewExpanded()
        sv_searchCar.setIconifiedByDefault(false)
        sv_searchCar.isSubmitButtonEnabled = true
        val tv_searchCar: TextView = sv_searchCar.findViewById(R.id.search_src_text) as TextView
        tv_searchCar.text = "搜索您想要的车"
        tv_searchCar.textSize = 14.0F
        tv_searchCar.setHintTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
        Input.hideSoftInput(activity)
    }

}