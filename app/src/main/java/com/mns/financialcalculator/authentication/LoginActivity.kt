package com.mns.financialcalculator.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar?.apply{hide()}
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent))


        // val textchanger = getResources().getString(R.string.vd_toolbar_name)

        vd_toolbar_name_change.text = "Login"

        back_button.setOnClickListener(){
            finish()
        }

        btn_share_plan_csv.visibility= View.GONE


        tv_create_acc.setOnClickListener(){

            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }


    }
}
