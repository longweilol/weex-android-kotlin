package com.fby.promotion.Main.widget


import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.fby.promotion.Home.model.ShareMedia
import com.fby.promotion.Main.utils.GlobalUtil
import com.fby.promotion.Main.widget.BaseBottomDialog
import com.fby.promotion.R
import kotlinx.android.synthetic.main.layout_bottom_share.*
import me.shaohui.shareutil.ShareUtil
import me.shaohui.shareutil.share.ShareListener
import me.shaohui.shareutil.share.SharePlatform
import org.jetbrains.anko.toast


/**
 *    author : fby
 *    date   : 2019/3/615:21
 *    desc   :
 *    version: 1.0
 */
class ShareBottomDialog: BaseBottomDialog {
    constructor() : super()

    private var bitmap:Bitmap?=null
    private var shareType = 0
    private var shareMedia: ShareMedia? = null

    private var mShareListener: ShareListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mShareListener = object : ShareListener() {
            override fun shareSuccess() {
                activity!!.toast("分享成功")
                dismiss()
            }
            override fun shareFailure(e: Exception) {
                activity!!.toast("分享失败")
                dismiss()
            }
            override fun shareCancel() {
                activity!!.toast("取消分享")

            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.layout_bottom_share
    }

    override fun bindView(v: View?) {

    }

    override fun onStart() {
        super.onStart()
        share_cancel.setOnClickListener{
            dismiss()
        }
        if (this.shareType == 0){
            sv_share_dialog.visibility = View.VISIBLE
            img_share.setImageBitmap(this.bitmap)
            onShareBitmapAll()
        }else if (this.shareType == 2){
            sv_share_dialog.visibility = View.VISIBLE
            img_share_header.visibility = View.GONE
            img_share_quan.visibility = View.GONE
            img_share.setImageBitmap(this.bitmap)
            onShareBitmap(this.bitmap!!)
        } else{
            ll_share_all.visibility = View.GONE
            onShareMedia(this.shareMedia!!)
        }
    }
    fun onShareBitmapAll(){
        mRlQQ.setOnClickListener {
            var bitmap:Bitmap?=null
            try {
                bitmap = GlobalUtil.createViewBitmap(ll_share_all)
            }catch (e:Exception){
                e.printStackTrace()
            }
            ShareUtil.shareImage(activity, SharePlatform.QQ, bitmap, mShareListener)
        }
        mRlQzone.setOnClickListener {
            var bitmap:Bitmap?=null
            try {
                bitmap = GlobalUtil.createViewBitmap(ll_share_all)
            }catch (e:Exception){
                e.printStackTrace()
            }
            ShareUtil.shareImage(activity, SharePlatform.QZONE, bitmap, mShareListener)
        }
        mRlWeibo.setOnClickListener {
            var bitmap:Bitmap?=null
            try {
                bitmap = GlobalUtil.createViewBitmap(ll_share_all)
            }catch (e:Exception){
                e.printStackTrace()
            }
            ShareUtil.shareImage(activity, SharePlatform.WEIBO, bitmap, mShareListener)
        }
        mRlWeixinCircle.setOnClickListener {
            var bitmap:Bitmap?=null
            try {
                bitmap = GlobalUtil.createViewBitmap(ll_share_all)
            }catch (e:Exception){
                e.printStackTrace()
            }
            ShareUtil.shareImage(activity, SharePlatform.WX_TIMELINE, bitmap, mShareListener)
        }
        mRlWechat.setOnClickListener {

            var bitmap:Bitmap?=null
            try {
                bitmap = GlobalUtil.createViewBitmap(ll_share_all)
            }catch (e:Exception){
                e.printStackTrace()
            }
            ShareUtil.shareImage(activity, SharePlatform.WX, bitmap, mShareListener)
        }
    }
    fun onShareBitmap(bitmap: Bitmap){
        mRlQQ.setOnClickListener {
            ShareUtil.shareImage(activity, SharePlatform.QQ, bitmap, mShareListener)
        }
        mRlQzone.setOnClickListener {
            ShareUtil.shareImage(activity, SharePlatform.QZONE, bitmap, mShareListener)
        }
        mRlWeibo.setOnClickListener {
            ShareUtil.shareImage(activity, SharePlatform.WEIBO, bitmap, mShareListener)
        }
        mRlWeixinCircle.setOnClickListener {
            ShareUtil.shareImage(activity, SharePlatform.WX_TIMELINE, bitmap, mShareListener)
        }
        mRlWechat.setOnClickListener {
            ShareUtil.shareImage(activity, SharePlatform.WX, bitmap, mShareListener)
        }
    }
    fun onShareMedia(shareMedia: ShareMedia){
        mRlQQ.setOnClickListener {
            ShareUtil.shareMedia(activity,SharePlatform.QQ,shareMedia.title,
                    shareMedia.summary,shareMedia.targetUrl,shareMedia.bitmapUrl,mShareListener)
        }
        mRlQzone.setOnClickListener {
            ShareUtil.shareMedia(activity,SharePlatform.QZONE,shareMedia.title,
                    shareMedia.summary,shareMedia.targetUrl,shareMedia.bitmapUrl,mShareListener)
        }
        mRlWeibo.setOnClickListener {
            ShareUtil.shareMedia(activity,SharePlatform.WEIBO,shareMedia.title,
                    shareMedia.summary,shareMedia.targetUrl,shareMedia.bitmapUrl,mShareListener)
        }
        mRlWeixinCircle.setOnClickListener {
            ShareUtil.shareMedia(activity,SharePlatform.WX_TIMELINE,shareMedia.title,
                    shareMedia.summary,shareMedia.targetUrl,shareMedia.bitmapUrl,mShareListener)
        }
        mRlWechat.setOnClickListener {
//            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher, null)
            ShareUtil.shareMedia(activity,SharePlatform.WX,shareMedia.title,
                    shareMedia.summary,shareMedia.targetUrl,shareMedia.bitmapUrl,mShareListener)
        }
    }
    fun setShareBitmap(bitmap: Bitmap){
        this.bitmap =  bitmap
    }
    fun setShareMedia(type:Int,shareMedia: ShareMedia){
        this.shareType = type
        this.shareMedia = shareMedia
    }
    fun setShareType(type:Int){
        this.shareType = type
    }
}