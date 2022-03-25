package com.sihvitb.ticklus

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registeration_page.*


class Registeration_page : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private  lateinit var  db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration_page)

        val animDrawable = registeration_page_layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

//        val user = hashMapOf(
//            "Name" to "Brine Walker",
//            "Phone" to "0123456789",
//            "Email" to "demo@demo.com",
//        )
//
//        db.collection("USERS")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

        registern_btn_register.setOnClickListener {
            val email = email_register.text.toString()
            val passwd = password_login_page.text.toString()
            val name = name_register.text.toString()
            val phone = Phone_register.text.toString()
            val user = hashMapOf(
                "Name" to name,
                "Phone" to phone,
                "email" to email
            )
            val users = db.collection("USERS")
            if (!(errorToast())) {
                users.whereEqualTo("email",email).get().addOnSuccessListener { tasks ->
                        if (tasks.isEmpty) {

                                auth.createUserWithEmailAndPassword(email , passwd).addOnCompleteListener(this){ task ->
                                    if(task.isSuccessful){
                                        users.document(email).set(user, SetOptions.merge())
                                        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this,Home_nav::class.java).putExtra("email",email))
                                        finish()


//                                        startActivity(Intent(this,Login_page::class.java))
//                                        finish()
                                    }
                                    else{
                                        Toast.makeText(this,"Registration Failed !",Toast.LENGTH_SHORT).show()


                                    }
                                }

                        } else {
                            Toast.makeText(this, "User Already Registered", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, Login_page::class.java))
                            finish()
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