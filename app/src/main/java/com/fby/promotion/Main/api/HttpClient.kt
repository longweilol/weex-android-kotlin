package com.fby.promotion.Main.api

import android.util.Log
import android.webkit.CookieManager
import com.fby.promotion.Main.common.Constant
import com.fby.promotion.Main.utils.L
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by fengfeng on 2018/8/13.
 */


class HttpClient private constructor() {
    private val SEFAILT_TIMEOUT: Long = 20
    private var retrofit: Retrofit
    private var apiServers: ApiServers


    companion object {
        val instance : HttpClient= HttpClient()
    }

    init {
        retrofit = Retrofit.Builder()
                .client(genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BaseUrl)
                .build()
        apiServers = retrofit.create(ApiServers::class.java)
    }

    fun getAPIServers(): ApiServers {
        return  apiServers
    }

    fun <T>request(observable: Observable<ObjectResponse<T>>,observer: Observer<ObjectResponse<T>>){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    fun <T>request(observable: Observable<ObjectResponse<T>>): Observable<T>{
        return observable.toSchedulers().map(object : Function<ObjectResponse<T>, T> {
            override fun apply(t: ObjectResponse<T>): T {

                //
                L.d(t.toString())



                return t.data!!
            }
        }).doOnEach(object : Observer<T> {
            override fun onNext(t: T) {
                L.d(t.toString())
            }

            override fun onError(e: Throwable) {
                // 处理请求过程中错误
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onComplete() {

            }
        })
    }

    fun genericClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()

//        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
//        }else {
//            logging.level = HttpLoggingInterceptor.Level.NONE
//        }
        return OkHttpClient.Builder()
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        var request = chain.request()
                        val requestBuilder = request.newBuilder()
                        request = requestBuilder
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .addHeader("cookie",getCookie())
//                                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
//                                        bodyToString(request.body())))//关键部分，设置requestBody的编码格式为json
                                .build()
                        return chain.proceed(request)
                    }
                })
                .addInterceptor(logging)
                .connectTimeout(SEFAILT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(SEFAILT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(SEFAILT_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }

    private fun getCookie(): String {
        val cookieManager = CookieManager.getInstance()
        val cookie = cookieManager.getCookie("cookie")
        Log.i("cookie",cookie ?: "")
        return cookie ?: ""
    }

    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            Log.e("request",request.toString())
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }

    }

}