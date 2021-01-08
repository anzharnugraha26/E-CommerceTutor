package com.example.tokoonlineturorial

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tokoonlineturorial.activity.LoginActivity
import com.example.tokoonlineturorial.activity.MasukActivity

import com.example.tokoonlineturorial.fragment.AkunFragment
import com.example.tokoonlineturorial.fragment.HomeFragment
import com.example.tokoonlineturorial.fragment.KeranjangFragment
import com.example.tokoonlineturorial.helper.Selfpref
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

    private var statusLogin = false
    private lateinit var s: Selfpref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        s = Selfpref(this)

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

                    if (s.getStatusLogin()) {
                        callFragment(2, fragmenAkun)
                    } else{
                        startActivity(Intent(this, MasukActivity::class.java ))
                    }

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
