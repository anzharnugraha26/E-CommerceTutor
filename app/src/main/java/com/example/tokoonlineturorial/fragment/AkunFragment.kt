package com.example.tokoonlineturorial.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tokoonlineturorial.R
import com.example.tokoonlineturorial.activity.LoginActivity
import com.example.tokoonlineturorial.helper.Selfpref

class AkunFragment : Fragment() {

    lateinit var s: Selfpref
    lateinit var bLogout: TextView
    lateinit var tvNama: TextView
    lateinit var tvEmail: TextView
    lateinit var tvPhone: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)
        init(view)

        s = Selfpref(requireActivity())
        bLogout.setOnClickListener {
            s.setStatusLogin(false)
        }
        setData()
        return view
    }

    fun setData(){
        if (s.getUser() == null){
            val inten = Intent(activity, LoginActivity::class.java)
            inten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(inten)
            return
        }

        val user= s.getUser()!!
        tvNama.text = user.name
        tvEmail.text = user.email
       // tvPhone.text = user.phone
    }

    private fun init(view: View) {
        bLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.tv_nama)
        tvPhone = view.findViewById(R.id.phoneAkun)
        tvEmail = view.findViewById(R.id.tv_email)

    }


}