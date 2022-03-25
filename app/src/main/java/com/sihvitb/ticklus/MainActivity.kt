@file:Suppress("DEPRECATION")

package com.sihvitb.ticklus

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Handler as Handler1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animDrawable = splash_Screen_layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()

        Handler1().postDelayed({ startActivity(Intent(this,Home_nav::class.java))
            finish()},2500)

//        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)?:return
//        val spEmail = sharedPref.getString("Email",1)
    }
}