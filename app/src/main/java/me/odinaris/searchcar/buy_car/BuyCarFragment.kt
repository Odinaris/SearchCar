package me.odinaris.searchcar.buy_car

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.odinaris.searchcar.R
import kotlinx.android.synthetic.main.frag_car_buy.*
import android.widget.TextView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import me.odinaris.searchcar.adapter.CarAdapter
import me.odinaris.searchcar.bean.CarIntro
import me.odinaris.searchcar.bean.ShelfCar
import me.odinaris.searchcar.utils.Input

class BuyCarFragment : Fragment() {
    private var carList: ArrayList<CarIntro>? = ArrayList()

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
    }

    private fun initView() {
        sv_search.onActionViewExpanded()
        sv_search.setIconifiedByDefault(false)
        sv_search.isSubmitButtonEnabled = true
        val tv_searchCar:TextView = sv_search.findViewById(R.id.search_src_text) as TextView
        tv_searchCar.textSize = 14.0F
        tv_searchCar.setHint(R.string.tips_searchCar)
        tv_searchCar.setHintTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
        tv_searchCar.clearFocus()
        Input.hideSoftInput(activity)
    }

    private fun initData() {
        val carIntroQuery = BmobQuery<ShelfCar>()
        carIntroQuery.addWhereNotEqualTo("objectId","")
        carIntroQuery.setLimit(20)
        carIntroQuery.order("-objectId")
        carIntroQuery.findObjects(object : FindListener<ShelfCar>(){
            override fun done(p0: MutableList<ShelfCar>?, e: BmobException?) {
                if(e==null){
                    p0!!.map { CarIntro(it.name, it.cover, it.registerTime, it.mileAge, it.price, it.newPrice)
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
}