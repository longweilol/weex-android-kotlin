package com.fby.promotion.Main.api

import com.fby.promotion.Home.model.Home
import io.reactivex.Observable
import retrofit2.http.*

/**
 *    author : fby
 *    date   : 2019/4/1617:45
 *    desc   :
 *    version: 1.0
 */

interface  ApiServers {

    @POST("api/home/info")
    fun getHomeInfo( @Body params: HashMap<String, Any>): Observable<ObjectResponse<Home>>

    @POST("api/home/list")
    fun getHomeList( @Body params: HashMap<String, Any>): Observable<ObjectResponse<List<Home>>>

    @GET("api/push/getByKey")
    fun getByKey(@Query("key") code : String): Observable<ObjectResponse<Home>>

}
