package com.fby.promotion.Main.api

import android.content.Context
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException


/**
 * Created by fengfeng on 2018/8/27.
 */


abstract class ObserverResponse<T>(private val context: Context) : Observer<T> {
    abstract fun success(data: T)
    abstract fun failure(statusCode: Int, message:String )

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        success(t)
    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {

        if (e is HttpException) { //连接服务器成功但服务器返回错误状态码

            failure(e.code(), "")
            return
        }

//        val apiErrorType: ApiErrorType = when (e) {  //发送网络问题或其它未知问题，请根据实际情况进行修改
//            is UnknownHostException -> ApiErrorType.NETWORK_NOT_CONNECT
//            is ConnectException -> ApiErrorType.NETWORK_NOT_CONNECT
//            is SocketTimeoutException -> ApiErrorType.CONNECTION_TIMEOUT
//            else -> ApiErrorType.UNEXPECTED_ERROR
//        }
        //TODO
        failure(-1, "")
    }


}
