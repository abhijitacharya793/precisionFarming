package com.example.phasal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.phasal.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    internal var selectedFragment : Fragment? = null



    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item->
        when(item.itemId){
            R.id.navigation_home->{
            moveToFragment(homeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search->{
                moveToFragment(searchFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_crop_data->{
                moveToFragment(cropDataFragment())
                //startActivity(Intent(this@MainActivity,AddPostActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_profile->{
            moveToFragment(profileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }

        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView:BottomNavigationView= findViewById(R.id.nav_view)



        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(cropDataFragment())

    }
    private fun moveToFragment(fragment: Fragment){
        val FragmentTrans = supportFragmentManager.beginTransaction()
        FragmentTrans.replace(R.id.fragment_container,fragment)
        FragmentTrans.commit()
    }

}
