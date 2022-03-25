package com.sihvitb.ticklus

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*

class Login_page : AppCompatActivity() {
    private  lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val animDrawable = login_page_layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()


         auth = FirebaseAuth.getInstance()
//        register_btn.setOnClickListener(){
//            startActivity(Intent(this,Registeration_page::class.java))
//            finish()
//        }
//        login_btn.setOnClickListener(){
//            startActivity(Intent(this,Home_nav::class.java))
//            finish()
        register_btn.setOnClickListener(){
            startActivity(Intent(this,Registeration_page::class.java))
        }


        login_btn.setOnClickListener() {
                val email = email_lodin_page.text.toString()
                val passwd = password_login_page.text.toString()
                if (checkUserDetails(email,passwd)) {
                    auth.signInWithEmailAndPassword(email, passwd)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,Home_nav::class.java).putExtra("email",email))
                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
//                                Log.w(TAG, "signInWithEmail:failure", task.exception)
//                                Toast.makeText(
//                                    baseContext, "Authentication failed.",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                                updateUI(null)
                                Toast.makeText(this, "Login Faild!", Toast.LENGTH_SHORT).show()

                            }
                        }
                }

        }
    }
    private fun checkUserDetails(email:String, passwd:String):Boolean {
        return if (email.trim { it <= ' ' }.isNotEmpty() && passwd.trim { it <= ' ' }.isNotEmpty()) {
            true
        } else {
            Toast.makeText(this, "Enter The Details !", Toast.LENGTH_SHORT).show()
            false
        }
    }
}