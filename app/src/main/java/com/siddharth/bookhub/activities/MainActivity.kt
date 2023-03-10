package com.siddharth.bookhub.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.siddharth.bookhub.*
import com.siddharth.bookhub.fragments.*

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem:MenuItem  ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.CoordinatorLayout)
        toolbar = findViewById(R.id.ToolBar)
        frameLayout = findViewById(R.id.FrameLayout)
        navigationView = findViewById(R.id.NavigationBar)
        setToolbar()
        openDashboard()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer

        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem!=null)
            {
                previousMenuItem?.isChecked=false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId)
            {
                R.id.dashboard -> {

                    openDashboard()
                    drawerLayout.closeDrawers()

                }

                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, FavouriteFragment())

                        .commit()
                    supportActionBar?.title="Favourite"
                    drawerLayout.closeDrawers()
                }


                R.id.audioBooks -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, AudioBookFragment())

                        .commit()
                    supportActionBar?.title="AudioBooks"
                    drawerLayout.closeDrawers()
                }

                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, ProfileFragment())

                        .commit()
                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()
                }


                R.id.aboutApp -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, AboutAppFragment())

                        .commit()
                    supportActionBar?.title="AboutApp"
                    drawerLayout.closeDrawers()
                }



            }

            return@setNavigationItemSelectedListener true

        }


    }


    fun openDashboard()
    {
        val fragment= DashboardFragment()
        val transition = supportFragmentManager.beginTransaction()

        transition.replace(R.id.FrameLayout,fragment)
        transition.commit()
        supportActionBar?.title="Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    fun setToolbar()
    {
       setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if(id==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)


    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.FrameLayout)
        when(frag)
        {
            !is DashboardFragment -> openDashboard()

            else -> super.onBackPressed()
        }
    }

}