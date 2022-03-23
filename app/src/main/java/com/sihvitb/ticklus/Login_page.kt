package com.sihvitb.ticklus

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registeration_page.*

class Login_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val animDrawable = login_page_layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()

        register_btn.setOnClickListener(){
            startActivity(Intent(this,Registeration_page::class.java))
            finish()
        }
        login_btn.setOnClickListener(){
            startActivity(Intent(this,Home_nav::class.java))
            finish()
        }
    }
}