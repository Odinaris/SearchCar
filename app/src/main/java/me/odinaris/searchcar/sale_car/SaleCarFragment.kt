package me.odinaris.searchcar.sale_car


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.odinaris.searchcar.R
import kotlinx.android.synthetic.main.frag_car_sale.*

class SaleCarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater!!.inflate(R.layout.frag_car_sale,container,false)
        return view
    }
    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initView()//适配器绑定等操作
        initClickListener()//监听器绑定操作
    }

    private fun initClickListener() {
        btn_apply.setOnClickListener{
            val phone = et_phone.text.toString()
            if(isPhone(phone)){
                val intent = Intent(context,SaleInfoActivity::class.java)
                intent.putExtra("phone",phone)
                startActivity(intent)
            }else{
                val builder = AlertDialog.Builder(context)
                builder.setMessage("手机号码填写不正确!").setPositiveButton("知道了", null).show()
            }
        }
    }

    private fun initView() {
        ll_saleBox.setBackgroundResource(R.drawable.wallpaper_sj2)
    }

    private fun readBitMap(resId: Int): Bitmap {
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.RGB_565
        val `is` = resources.openRawResource(resId)
        return BitmapFactory.decodeStream(`is`, null, opt)
    }
    //检测手机号码是否符合规范
    private fun isPhone(mobiles: String): Boolean {
        val telRegex = "[1][34578]\\d{9}"
        if (mobiles.isEmpty()) return false
        else return mobiles.matches(telRegex.toRegex())
    }
}