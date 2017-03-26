package me.odinaris.searchcar.bean

import cn.bmob.v3.BmobObject

/**
 * Created by Odinaris on 2017/3/24.
 */
class ShelfCar : BmobObject() {
    var name: String = ""               //名称
    var wheelbase: String = ""          //扭矩
    var displacement: String = ""       //排量
    var driveMode: String = ""          //驱动方式
    var emissionStandard: String = ""   //排放标准
    var fuelType: String = ""           //燃油类型
    var imgUrl1: String = ""               //图片链接1
    var imgUrl2: String = ""               //图片链接2
    var imgUrl3: String = ""               //图片链接3
    var maxSpeed: String = ""           //最大马力
    var mileAge: String = ""            //里程
    var newPrice: String = ""           //原价
    var price: String = ""              //价格
    var registerTime: String = ""       //上牌时间
    //var shelfTime: String = ""          //上架时间
    var size: String = ""               //长/宽/高
    var vendor: String = ""             //厂商
    var weight: String = ""             //整备重量
    var transmission: String = ""       //变速器
    var applyPhone: String = ""         //预约电话
}