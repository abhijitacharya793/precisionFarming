package com.ultimategroup.phasal.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ultimategroup.phasal.MainActivity

import com.ultimategroup.phasal.R
import kotlinx.android.synthetic.main.fragment_weather.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class weatherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        skip_buttonw.setOnClickListener {
//            skipActivity()
//        }
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_weather, container, false)
    }
    fun skipActivity(){
        val goToMain = Intent(activity, MainActivity::class.java)
        goToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(goToMain)
    }
}// Required empty public constructor