package me.odinaris.searchcar.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import me.odinaris.searchcar.bean.CarIntro
import android.view.View
import android.widget.ImageView
import me.odinaris.searchcar.R
import android.view.LayoutInflater
import com.bumptech.glide.Glide


/**
 * Created by Odinaris on 2017/3/26.
 */
class CarAdapter(val carList: ArrayList<CarIntro>, val context: Context) :
        RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carInfo = carList[position]
        holder.name.text = carInfo.name
        holder.registerTime.text = carInfo.registerTime
        holder.mileAge.text = carInfo.mileAge
        holder.price.text = carInfo.price
        holder.newPrice.text = carInfo.newPrice
        Glide.with(context).load(Uri.parse(carInfo.imageUrl)).into(holder.img)
        holder.itemView.setOnClickListener({
            //跳转汽车详情页
        })


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int { return carList.size }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.tv_car_name) as TextView
        var registerTime : TextView = itemView.findViewById(R.id.tv_car_registerTime) as TextView
        var mileAge : TextView = itemView.findViewById(R.id.tv_car_mileAge) as TextView
        var price : TextView = itemView.findViewById(R.id.tv_car_price) as TextView
        var newPrice : TextView = itemView.findViewById(R.id.tv_car_newPrice) as TextView
        var img : ImageView = itemView.findViewById(R.id.iv_car_cover) as ImageView

    }
}