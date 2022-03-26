package com.sihvitb.ticklus

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_registeration_page.*


class Registeration_page : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private  lateinit var  db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration_page)

        val animDrawable = registeration_page_layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

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




        user_image.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
//            Log.i(TAG,"Open up image piker on device")
//            val imagePickerintent  = Intent(Intent.ACTION_GET_CONTENT)
//            imagePickerintent.type = "image/*"
//            if(imagePickerintent.resolveActivity(packageManager) != null){
//                startActivityForResult(imagePickerintent,PICK_PHOTO_CODE)
//            }

        }

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

                                        //Uplaod user image
                                        if(imageUri == null){
                                            imageUri = Uri.parse("android.resource://com.sihvitb.ticklus/drawable/default_user")
                                        }
                                        val riversRef = storageReference.child("images/$email")
                                        val uploadTask = riversRef.putFile(imageUri!!)
                                        uploadTask.addOnSuccessListener { Log.i(TAG,"Image Added to firebase") }
                                        uploadTask.addOnFailureListener{ Log.i(TAG,"Image not Added to firebase")}
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            user_image.setImageURI(imageUri)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode == PICK_PHOTO_CODE){
//            if (resultCode == Activity.RESULT_OK){
//                photoUri = data?.data
//                Log.i(TAG,"PhotoUri $photoUri")
//                user_image.setImageURI(photoUri)
//            }
//            }
//            else{
//                Toast.makeText(this,"Image picker action canceled !",Toast.LENGTH_SHORT).show()
////                Log.i(TAG,"DefualtImageURI $photoUri")
////                photoUri = Uri.parse("android.resource://com.sihvitb.ticklus/drawable/default_user")
////                user_image.setImageURI(photoUri)
//            }
//        }


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