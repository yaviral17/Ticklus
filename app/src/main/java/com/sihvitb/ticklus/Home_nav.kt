package com.sihvitb.ticklus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.sihvitb.ticklus.databinding.ActivityHomeNavBinding
import kotlinx.android.synthetic.main.content_home_nav.*
import kotlinx.android.synthetic.main.nav_header_home_nav.*

class Home_nav : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeNavBinding
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomeNav.toolbar)

        binding.appBarHomeNav.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home_nav)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


//        payment_btn.setOnClickListener{
//            startActivity(Intent(this,Payment_success_page::class.java))
//        }

        // Session management code starts from here

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?:return
        val islogin = sharedPref.getString("Email","1")



        if(islogin=="1"){
            val email = intent.getStringExtra("email")
            if(email != null){
                    setText(email)
                    with(sharedPref.edit()) {
                        putString("Email", email)
                        apply()
                    }
                }
            else{
                startActivity(Intent(this,Login_page::class.java))
                finish()
            }
        }
        else{
            setText(islogin)
        }
        // Session management code ends here

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_nav, menu)

        Logout_btn.setOnClickListener{
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            sharedPref.edit().remove("Email").apply()
            startActivity(Intent(this,Login_page::class.java))
            finish()
        }

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_nav)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setText(Email:String?){
        //Creating firestore instance
        db = FirebaseFirestore.getInstance()
        if (Email != null) {
            db.collection("USERS").document(Email).get().addOnSuccessListener { task ->
                user_name.text = task.get("Name").toString()
                user_email.text = task.get("email").toString()
            }
        }

    }
}