package me.odinaris.searchcar.homepage


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.zaaach.citypicker.CityPickerActivity
import me.odinaris.searchcar.R
import kotlinx.android.synthetic.main.frag_homepage.*
import me.odinaris.searchcar.bean.CarIntro
import me.odinaris.searchcar.buy_car.BuyCarFragment
import me.odinaris.searchcar.sale_car.SaleCarFragment
import me.odinaris.searchcar.utils.Input
import java.util.*
import android.content.Context
import me.odinaris.searchcar.utils.BmobUtils


class HomepageFragment : Fragment(),View.OnClickListener{

    private val REQUEST_CODE_PICK_CITY = 0
    private var carList: ArrayList<CarIntro>? = ArrayList()
    private var viewList: ArrayList<View> = ArrayList()
    private val loadingNum = 10
    private val skipNum = 0
    private var carMap:HashMap<String,String> = HashMap()
    private var  bnb: BottomNavigationBar? = null


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
        carMap["city"] = activity.getSharedPreferences("cityInfo",Context.MODE_PRIVATE).getString("city","合肥")
        BmobUtils.searchCar(carMap,carList,loadingNum,skipNum,rv_hotCar,ll_loading,context,false)
    }
    private fun initClickListener() {
        ll_location.setOnClickListener(this)
        bbl_car_buy.setOnClickListener(this)
        btn_allCar.setOnClickListener(this)
        bbl_car_sale.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v){
            ll_location -> {
                startActivityForResult(Intent(context,CityPickerActivity::class.java),REQUEST_CODE_PICK_CITY) }
            bbl_car_buy -> {
                bnb!!.selectTab(1)
                val transaction = fragmentManager!!.beginTransaction()!!
                transaction.show(BuyCarFragment())
                transaction.hide(this)
                transaction.commit()
            }
            btn_allCar -> {
                bnb!!.selectTab(1)
                val transaction = fragmentManager!!.beginTransaction()!!
                transaction.show(BuyCarFragment())
                transaction.hide(this)
                transaction.commit()
            }
            bbl_car_sale -> {
                val transaction = fragmentManager!!.beginTransaction()!!
                bnb!!.selectTab(2)
                transaction.show(SaleCarFragment())
                transaction.hide(this)
                transaction.commit()
            }
        }
    }
    //重写onActivityResult方法
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK && data!=null){
            val city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY)
            tv_location.text = city
            val cityInfo = activity.getSharedPreferences("cityInfo",Context.MODE_PRIVATE)
            cityInfo.edit().putString("city", city).apply()//将用户当前选择城市存储在SharePreference中用于其他模块查询
            BmobUtils.searchCar(carMap,carList,loadingNum,skipNum,rv_hotCar,ll_loading,context,false)

        }
    }
    fun initView(){
        tv_location.text = activity.getSharedPreferences("cityInfo",Context.MODE_PRIVATE).getString("city","合肥")
        bnb = activity.findViewById(R.id.main_navigator) as BottomNavigationBar
        et_search.onActionViewExpanded()
        et_search.setIconifiedByDefault(false)
        et_search.isSubmitButtonEnabled = true
        val tv_searchCar: TextView = et_search.findViewById(R.id.search_src_text) as TextView
        tv_searchCar.textSize = 14.0F
        tv_searchCar.setHint(R.string.tips_searchCar)
        tv_searchCar.setHintTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
        tv_searchCar.clearFocus()//隐藏自动弹出的软键盘
        viewList.add(et_search)
        viewList.add(tv_searchCar)
        Input.hideSoftInput(context,viewList)
    }

}