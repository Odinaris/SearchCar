package me.odinaris.searchcar.utils

import android.app.AlertDialog
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.view.View.GONE
import android.view.Window

import me.odinaris.searchcar.R

import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_car_detail.*
import me.odinaris.searchcar.bean.ShelfCar

class CarDetailActivity : AppCompatActivity() {
    private var objectId : String? = null
    private var imgUrl : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_car_detail)
        initData()
        initView()
    }
    private fun initView() {}
    private fun initData() {
        objectId = intent.getStringExtra("objectId")
        if(objectId!=""&&objectId!=null){
            val query = BmobQuery<ShelfCar>()
            query.getObject(objectId, object : QueryListener<ShelfCar>() {
                override fun done(car: ShelfCar, e: BmobException?) {
                    if (e == null) {
                        imgUrl = car.cover
                        Glide.with(baseContext).load(Uri.parse(imgUrl)).into(iv_cover)
                        tv_name.text = car.name
                        tv_price.text = car.price + "万"
                        tv_newPrice.text = "新车参考价:" + car.newPrice
                        tv_intro.text = car.introduction
                        tv_testResult.text = car.testResult
                        tv_city.text = car.city
                        tv_vendor.text = car.vendor
                        tv_wheelbase.text = car.wheelbase
                        tv_mileAge.text = car.mileAge
                        tv_maxSpeed.text = car.maxSpeed
                        tv_displacement.text = car.displacement
                        tv_fuelType.text = car.fuelType
                        tv_emission.text = car.emissionStandard
                        tv_registerTime.text = car.registerTime
                        tv_transmission.text = car.transmission
                        tv_cylinder.text = car.cylinder
                        tv_level.text = car.level
                        tv_structure.text = car.structure
                        abl_bar.visibility = VISIBLE
                        nsv_container.visibility = VISIBLE
                        ll_apply.visibility = VISIBLE
                        ll_loadingView.visibility = GONE
                        iv_cover.visibility = VISIBLE
                    } else {
                        val dialog = AlertDialog.Builder(applicationContext)
                        dialog.setMessage("数据请求出错！"+e.errorCode+e.message).show()
                    }
                }
            })
        }

    }
}
