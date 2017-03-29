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
    var cover: String = ""              //图片链接
    var maxSpeed: String = ""           //最大马力
    var mileAge: String = ""            //里程
    var newPrice: String = ""           //原价
    var price: String = ""              //价格
    var registerTime: String = ""       //上牌时间
    //var shelfTime: String = ""        //上架时间
    var size: String = ""               //长/宽/高
    var vendor: String = ""             //厂商
    var weight: String = ""             //整备重量
    var transmission: String = ""       //变速器
    var cylinder: String = ""           //气缸
    var link_detail:String = ""         //详情页链接
    var level:String = ""               //级别
    var introduction:String = ""        //卖家介绍
    var testResult:String = ""          //测试结果
    var structure:String = ""           //结构
    var city:String = ""                //所在城市
}