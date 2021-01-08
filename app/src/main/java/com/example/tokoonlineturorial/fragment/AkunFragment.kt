package com.example.tokoonlineturorial.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.helper.Selfpref

class AkunFragment : Fragment() {

    lateinit var s: Selfpref
    lateinit var bLogout:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)

        bLogout = view.findViewById(R.id.btn_logout)

        s = Selfpref(activity!!)
        bLogout.setOnClickListener {
            s.setStatusLogin(false)
        }
        return view
    }


}