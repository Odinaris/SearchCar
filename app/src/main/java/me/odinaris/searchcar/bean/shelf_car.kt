package me.odinaris.searchcar.bean

import cn.bmob.v3.BmobObject

/**
 * Created by Odinaris on 2017/3/24.
 */
class shelf_car: BmobObject() {
    var car_name: String = ""               //名称
    var car_wheelbase: String = ""          //扭矩
    var car_displacement: String = ""       //排量
    var car_driveMode: String = ""          //驱动方式
    var car_emissionStandard: String = ""   //排放标准
    var car_fuelType: String = ""           //燃油类型
    var car_img1: String = ""               //图片链接1
    var car_img2: String = ""               //图片链接2
    var car_img3: String = ""               //图片链接3
    var car_maxSpeed: String = ""           //最大马力
    var car_mileAge: String = ""            //里程
    var car_newPrice: String = ""           //原价
    var car_price: String = ""              //价格
    var car_registerTime: String = ""       //上牌时间
    var car_shelfTime: String = ""          //上架时间
    var car_size: String = ""               //长/宽/高
    var car_vendor: String = ""             //厂商
    var car_weight: String = ""             //整备重量
    var car_transmission: String = ""       //变速器
    var car_applyPhone: String = ""         //预约电话
}