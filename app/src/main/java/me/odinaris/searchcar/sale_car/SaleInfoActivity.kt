package me.odinaris.searchcar.sale_car

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.zaaach.citypicker.CityPickerActivity
import kotlinx.android.synthetic.main.act_sale_info.*

import me.odinaris.searchcar.R
import java.util.*
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.content.DialogInterface
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import me.odinaris.searchcar.bean.SaleInfo
import me.odinaris.searchcar.main.MainActivity


class SaleInfoActivity : AppCompatActivity() {
    private val REQUEST_CODE_PICK_CITY = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_sale_info)
        initClickListener()//监听器绑定操作
    }

    private fun initClickListener() {
        tv_submit.setOnClickListener{
            val phone = intent.getStringExtra("phone")
            val vendor = et_vendor.text.toString()
            val mileAge = et_mileAge.text.toString()
            val city = tv_city.text.toString()
            val registerTime = tv_registerTime.text.toString()
            val price = et_price.text.toString()
            if(allInfoFilled(vendor,mileAge,registerTime,price)){
                val saleInfo = SaleInfo()
                saleInfo.phone = phone
                saleInfo.vendor = vendor
                saleInfo.mileAge = mileAge.toFloat()
                saleInfo.city = city
                saleInfo.registerTime = registerTime
                saleInfo.price = price.toFloat()
                saleInfo.username = BmobUser.getCurrentUser().username
                val dialog = AlertDialog.Builder(this)
                saleInfo.save(object:SaveListener<String>(){
                    override fun done(p0: String?, e: BmobException?) {
                        if(e == null) {
                            dialog.setMessage("信息提交成功,请等待工作人员与您联系!")
                                    .setPositiveButton("知道了", DialogInterface.OnClickListener { dialog, which ->
                                        startActivity(Intent(this@SaleInfoActivity,MainActivity::class.java))
                                    }).show()
                        }else{
                            dialog.setMessage("提交数据失败！"+e.message+e.errorCode)
                                    .setPositiveButton("知道了",null).show()
                        }
                    }
                })
            }else{
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage("请检查信息是否填写完整!").setPositiveButton("知道了",null).show()
            }
        }
        ll_city.setOnClickListener{
            startActivityForResult(Intent(this, CityPickerActivity::class.java),REQUEST_CODE_PICK_CITY) }
        ll_registerTime.setOnClickListener{
            showDataPick(tv_registerTime)
        }
    }

    private fun  allInfoFilled(vendor: String, mileAge: String, registerTime: String, price: String): Boolean {
        return (vendor!="" && mileAge != "" && registerTime != "" && price != "")
    }
    //重写onActivityResult方法
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == Activity.RESULT_OK && data!=null){
            val city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY)
            tv_city.text = city
        }
    }
    private fun showDataPick(dateText : TextView){
        //获取Calendar对象，用于获取当前时间
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dateSet =  DatePickerDialog.OnDateSetListener {
            datePicker: DatePicker, year: Int, month: Int, day: Int ->
            dateText.text = "" + year + "-" + (month+1).toString()
        }
        //实例化DatePickerDialog对象
        val datePickerDialog = DatePickerDialog(this,dateSet,year,month,day)
        //弹出选择日期对话框
        datePickerDialog.show()
    }
}
