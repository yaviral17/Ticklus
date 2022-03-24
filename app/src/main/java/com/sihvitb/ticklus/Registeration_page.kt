package com.sihvitb.ticklus

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registeration_page.*


class Registeration_page : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration_page)

        val animDrawable = registeration_page_layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()

        auth = FirebaseAuth.getInstance()

        registern_btn_register.setOnClickListener(){
            val email = email_register.text.toString()
            val passwd = password_login_page.text.toString()
            val name = name_register.text.toString()
            val phoen = Phone_register.text.toString()
            val cpasswd = confirm_password_login_page.text.toString()

            if(!(errorToast())){

                auth.createUserWithEmailAndPassword(email , passwd).addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        startActivity(Intent(this,Login_page::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Registration Failed !",Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }



    }

    private fun errorToast():Boolean{
            if(email_register.text.toString().trim{it<=' '}.isNotEmpty() &&
                name_register.text.toString().trim{it<=' '}.isNotEmpty() &&
                Phone_register.text.toString().trim{it<=' '}.isNotEmpty() &&
                password_login_page.text.toString().trim{it<=' '}.isNotEmpty()){
                    return if (password_login_page.text.toString() != confirm_password_login_page.text.toString()){
                    Toast.makeText(this,"Confirm Password Mismatch !",Toast.LENGTH_SHORT).show()
                    true
                } else{
                    false
                }

        }
        else{
                Toast.makeText(this,"Enter The Details !",Toast.LENGTH_SHORT).show()
                return true
            }

    }
}