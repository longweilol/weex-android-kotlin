package com.fby.promotion.Home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *    author : fby
 *    date   : 2019/3/1111:24
 *    desc   :
 *    version: 1.0
 */
@Parcelize
data class ShareMedia(
    var title:String?=null,
    var summary:String?=null,
    var targetUrl:String?=null,
    var bitmapUrl: String?=null
):Parcelable