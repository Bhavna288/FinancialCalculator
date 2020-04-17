package com.mns.financialcalculator.fragments.user

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.mns.financialcalculator.R
import com.mns.financialcalculator.authentication.LoginActivity
import com.mns.financialcalculator.authentication.RegisterActivity
import kotlinx.android.synthetic.main.fragment_user.*


class UserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setTitle(Html.fromHtml("<font color='#000000'>User</font>"))

        btn_login.setOnClickListener(){

            val intent = Intent(activity!!,LoginActivity::class.java)
            startActivity(intent)
        }


        btn_reg.setOnClickListener(){

            val intent = Intent(activity!!,RegisterActivity::class.java)
            startActivity(intent)
        }
    }






}
