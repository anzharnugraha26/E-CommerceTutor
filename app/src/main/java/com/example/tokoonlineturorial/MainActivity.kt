package com.example.tokoonlineturorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.example.tokoonlineturorial.fragment.AkunFragment
import com.example.tokoonlineturorial.fragment.HomeFragment
import com.example.tokoonlineturorial.fragment.KeranjangFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val fragmentHome: Fragment = HomeFragment()
    private val fragmenKeranjang: Fragment = KeranjangFragment()
    private var fragmenAkun: Fragment = AkunFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentHome

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var buttonNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpNavBottom()

    }

    fun setUpNavBottom() {

        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmenKeranjang).hide(fragmenKeranjang).commit()
        fm.beginTransaction().add(R.id.container, fragmenAkun).hide(fragmenAkun).commit()

        buttonNavigationView = findViewById(R.id.nav_view)
        menu = buttonNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        buttonNavigationView.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_home -> {
                    callFragment(0, fragmentHome)
                }

                R.id.navigation_keranjang -> {
                    callFragment(1, fragmenKeranjang)
                }

                R.id.navigation_akun -> {
                    callFragment(2, fragmenAkun)
                }

            }

            false

        }

    }

    fun callFragment(int: Int, fragment: Fragment) {
//        Log.d("Response", "keranjang")
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

}
