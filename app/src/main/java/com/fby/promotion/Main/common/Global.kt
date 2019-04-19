package com.fby.promotion.Main.common

import android.content.Context


/**
 * Created by fengfeng on 2018/8/27.
 */


class Global private constructor(){


    companion object {
        val instance : Global = Global()
    }

    //TODO
    //全局变量

    /**
     * dp转换成px
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}