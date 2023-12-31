package com.example.instagram

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.fragments.HomeFragment
import com.example.instagram.fragments.NotificationsFragment
import com.example.instagram.fragments.ProfileFragment
import com.example.instagram.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    internal var selectedFragment:Fragment?=null

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    moveToFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_search -> {
                    moveToFragment(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_add_post -> {
                 ///   textView.setText("Add Post")
//                item.isChecked = false
//                startActivity(Intent(this@MainActivity, AddPostActivity::class.java))
                    moveToFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true

                }

                R.id.nav_notifications -> {
                    moveToFragment(NotificationsFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_profile -> {
                    moveToFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }


            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
     //   textView=findViewById<TextView>(R.id.message)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(HomeFragment())

    }
    private fun moveToFragment(fragment: Fragment){
        val fragmentTrans= supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container,fragment)
        fragmentTrans.commit()
    }
}