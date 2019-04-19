package com.fby.promotion.Main.utils

import java.text.DecimalFormat


/**
 * Created by fengfeng on 2018/9/3.
 */


class MyUtils {

    companion object Factory {
        val instance = MyUtils()
    }


    fun getVolUnit(num: Float): String {

        val e = Math.floor(Math.log10(num.toDouble())).toInt()

        return if (e >= 8) {
            "亿手"
        } else if (e >= 4) {
            "万手"
        } else {
            "手"
        }
    }

    fun getVolUnitNum(num: Float): Int {

        val e = Math.floor(Math.log10(num.toDouble())).toInt()

        return if (e >= 8) {
            8
        } else if (e >= 4) {
            4
        } else {
            1
        }
    }

    fun getVolUnitText(unit: Int, num: Float): String {
        var num = num
        val mFormat: DecimalFormat
        if (unit == 1) {
            mFormat = DecimalFormat("#0")
        } else {
            mFormat = DecimalFormat("#0.00")
        }
        num = num / unit
        return if (num == 0f) {
            "0"
        } else mFormat.format(num)
    }

    fun getDecimalFormatVol(vol: Float): String {
        val decimalFormat = DecimalFormat("#0.00")//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(vol)//format 返回的是字符串
    }


}