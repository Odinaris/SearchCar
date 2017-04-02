package me.odinaris.searchcar.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Window
import com.jaredrummler.materialspinner.MaterialSpinner

import me.odinaris.searchcar.R
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.act_filter_car.*
import android.content.DialogInterface



class FilterCarActivity : AppCompatActivity() {
    private val sortList: List<String> = ArrayList(
            Arrays.asList("最新上架","价格最低","价格最高","里程最少"))
//    private val priceList: List<String> = ArrayList(
//            Arrays.asList("不限","3万以下","3-5万","5-7万","7-9万","9-12万","12-16万","16-20万","20万以上"))
//    private val mileAgeList: List<String> = ArrayList(
//            Arrays.asList("不限","0-3万公里","3-6公里","6-9万公里","9-12万公里","12-15万公里","15万公里以上"))
    private val emissionList: List<String> = ArrayList(
            Arrays.asList("不限","国二及以上","国三及以上","国四及以上","国五"))
    private var selectedSort: String = sortList[0]
//    private var selectedPrice: String = priceList[0]
//    private var selectedMileAge: String = mileAgeList[0]
    private var selectedEmission: String = emissionList[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.act_filter_car)
        init()
        initListener()
    }
    private fun initListener(){
        ms_sort.setOnItemSelectedListener(
                MaterialSpinner.OnItemSelectedListener<String>({ view, position, id, item -> selectedSort = item }))
//        ms_price.setOnItemSelectedListener(
//                MaterialSpinner.OnItemSelectedListener<String>({ view, position, id, item -> selectedPrice = item }))
//        ms_mileAge.setOnItemSelectedListener(
//                MaterialSpinner.OnItemSelectedListener<String>({ view, position, id, item -> selectedMileAge = item }))
        ms_emission.setOnItemSelectedListener(
                MaterialSpinner.OnItemSelectedListener<String>({ view, position, id, item -> selectedEmission = item }))
        iv_back.setOnClickListener({ this.finish() })
        btn_filter.setOnClickListener({
            val data = Intent()
            val bundle = Bundle()
            bundle.putString("selectedSort", selectedSort)
//            bundle.putString("selectedPrice", selectedPrice)
//            bundle.putString("selectedMileAge", selectedMileAge)
            bundle.putString("selectedEmission", selectedEmission)
            data.putExtras(bundle)
            this.setResult(RESULT_OK, data)
            this.finish()
        })
        tv_clearAllFilter.setOnClickListener({
            ms_sort.selectedIndex = 0
//            ms_price.selectedIndex = 0
//            ms_mileAge.selectedIndex = 0
            ms_emission.selectedIndex = 0
        })
    }
    private fun init() {
        ms_sort.setItems(sortList)
//        ms_price.setItems(priceList)
//        ms_mileAge.setItems(mileAgeList)
        ms_emission.setItems(emissionList)
    }
}
