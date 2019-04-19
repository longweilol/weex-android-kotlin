package com.fby.promotion.Main.utils

import com.raizlabs.android.dbflow.annotation.Database

/**
 * Created by fby on 2019/2/26 15:20
 */
@Database(name = MyDatabase.NAME,version = MyDatabase.VERSION)
class MyDatabase {
    companion object {
        //数据库名称
        const  val NAME = "MyDatabase"
        //数据库版本号
        const  val VERSION = 1
    }
}