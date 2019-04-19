package com.fby.promotion.Main.wxmodule;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import com.fby.promotion.Main.WeexActivity;
import com.fby.promotion.Main.WeexMainActivity;
import com.fby.promotion.Main.utils.SharedUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * Created by pc on 2018/9/5.
 */

public class Module extends WXModule {
    //run ui thread
    @JSMethod(uiThread = true)
    public void printLog(String msg) {
        Toast.makeText(mWXSDKInstance.getContext(),msg, Toast.LENGTH_SHORT).show();
    }
    @JSMethod(uiThread = true)
    public void openURL(String url) {
        Intent intent = new Intent(mWXSDKInstance.getContext(), WeexMainActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("type","0");
        mWXSDKInstance.getContext().startActivity(intent);
    }
    @JSMethod(uiThread = true)
    public void openURL(String url,String title) {
        Intent intent = new Intent(mWXSDKInstance.getContext(), WeexActivity.class);
        intent.putExtra("url",url);
        mWXSDKInstance.getContext().startActivity(intent);
    }
    @JSMethod(uiThread = true)
    public void dismissViewController(String data) {
        Activity activity=(Activity)mWXSDKInstance.getContext();
        activity.finish();
    }
    @JSMethod(uiThread = true)
    public void isLoginGlobalEvent(String data,boolean isLogin) {
        if (isLogin){
            SharedUtil.putShared(mWXSDKInstance.getContext(),data,"0");
        }else{
            SharedUtil.putShared(mWXSDKInstance.getContext(),data,"1");
        }
    }

    //run JS thread
    @JSMethod (uiThread = false)
    public void fireEventSyncCall(){
        //implement your module logic here
    }

}
