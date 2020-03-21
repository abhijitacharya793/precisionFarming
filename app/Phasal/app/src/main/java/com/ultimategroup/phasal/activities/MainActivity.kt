package com.ultimategroup.phasal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.ultimategroup.phasal.Fragments.cropDataFragment
import com.ultimategroup.phasal.Fragments.postFragment
import com.ultimategroup.phasal.Fragments.profileFragment
import com.ultimategroup.phasal.Fragments.searchFragment
import com.ultimategroup.phasal.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ultimategroup.phasal.R
import com.ultimategroup.phasal.activities.CropDiseaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {


    internal var selectedFragment : Fragment? = null


//    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener
//    private val onNavigationItemSelectedListener = BottomAppBar.{ item->
//        when(item.itemId){
//            R.id.navigation_post->{
//                moveToFragment(postFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_search->{
//                moveToFragment(searchFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_crop_data->{
//                moveToFragment(cropDataFragment())
//                //startActivity(Intent(this@MainActivity,AddPostActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//
//            R.id.navigation_profile->{
//                moveToFragment(profileFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//
//            R.id.navigation_about->{
//                moveToFragment(aboutFragment())
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//
//        false
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Splash Screen
        var splashScreen: RelativeLayout = findViewById(R.id.splash)
        var container: CoordinatorLayout = findViewById(R.id.container)
        splashScreen.visibility = View.VISIBLE
        container.visibility = View.GONE

        nav_view.replaceMenu(R.menu.bottom_nav_menu)

        var captureDisease: FloatingActionButton = findViewById(R.id.fabb)
        captureDisease.setOnClickListener{
            moveToActivity(it)
        }

        nav_view.setOnMenuItemClickListener{ menuItem ->
            when(menuItem.itemId){
                R.id.navigation_post->{
                    moveToFragment(postFragment())
//                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_search->{
                    moveToFragment(searchFragment())
//                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_crop_data->{
                    moveToFragment(cropDataFragment())
//                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile->{
                    moveToFragment(profileFragment())
//                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_about->{
                    moveToFragment(aboutFragment())
//                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        }

        Handler().postDelayed({
            splashScreen.visibility = View.GONE
            container.visibility = View.VISIBLE
        }, 1000)

//        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(cropDataFragment())

    }
    private fun moveToFragment(fragment: Fragment){
        val FragmentTrans = supportFragmentManager.beginTransaction()
        FragmentTrans.replace(R.id.fragment_container,fragment)
        FragmentTrans.commit()
    }

    fun moveToActivity(view: View) {
        val intent = Intent(this, CropDiseaseActivity::class.java).apply {}
        startActivity(intent)
    }


}
