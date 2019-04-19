package com.fby.promotion

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.fby.promotion.Main.WeexMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_btn.setOnClickListener {
            val intent = Intent(this, WeexMainActivity::class.java)
            intent.putExtra("url", "file://assets/dist/index.js?#/pcenter")
            intent.putExtra("title", "")
            startActivity(intent)
        }
    }
}
