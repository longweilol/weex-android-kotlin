package com.fby.promotion.Main

import android.content.Intent
import android.support.v4.app.Fragment

/**
 *    author : fby
 *    date   : 2019/4/16
 *    desc   :
 *    version: 1.0
 */
open class BaseFragmentActivity : BaseActivity() {
    protected var currentFragment: Fragment? = null

    protected fun setDefaultFragment(id: Int,fragment: Fragment) {
        // 开启Fragment事务
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 使用fragment的布局替代frame_layout的控件并提交事务
        fragmentTransaction.replace(id, fragment).commit()
        currentFragment = fragment
    }

    protected fun switchFragment(id:Int,fragment: Fragment) {
        if (fragment !== currentFragment) {
            if (!fragment.isAdded) {
                supportFragmentManager.beginTransaction().hide(currentFragment!!)
                        .add(id, fragment).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(currentFragment!!)
                        .show(fragment).commit()
            }
            currentFragment = fragment
        }
    }

    override fun onResume() {
        super.onResume()
        currentFragment?.userVisibleHint = true
    }

    override fun onPause() {
        super.onPause()
        currentFragment?.userVisibleHint = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        currentFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
