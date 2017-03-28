package me.odinaris.searchcar.utils

import android.app.AlertDialog
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

    private fun initView() {
    }

    private fun initData() {
        objectId = intent.getStringExtra("objectId")
        if(objectId!=""&&objectId!=null){
            val query = BmobQuery<ShelfCar>()
            query.getObject(objectId, object : QueryListener<ShelfCar>() {
                override fun done(`object`: ShelfCar, e: BmobException?) {
                    if (e == null) {
                        //获得playerName的信息
                        imgUrl = `object`.cover
                        Glide.with(baseContext).load(Uri.parse(imgUrl)).into(iv_cover)

                    } else {
                        val dialog = AlertDialog.Builder(applicationContext)
                        dialog.setMessage("数据请求出错！"+e.errorCode+e.message).show()
                    }
                }

            })
        }

    }
}
