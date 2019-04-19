package com.fby.promotion.Main.api

import io.reactivex.internal.operators.maybe.MaybeDoAfterSuccess


/**
 * Created by fengfeng on 2018/8/13.
 */


data class  Response (

        var code:String,
        var msg :String,
        var ok:Boolean
)

open class BaseResponse(response:Response){}


data class  ListResponse<T> (
        var respond:Response,
        var list:List<T>?,
        var page:Int?,
        var size:Int?,
        var total:Int?


)


data class  ObjectResponse <T> (
        val respond:Response,
        var data :T?,

        var list:T?,
        var page:Int?,
        var size:Int?,
        var total:Int?
)

data class  ObjectKlineResponse <T> (
        val success:Boolean,
        var data :T?
)