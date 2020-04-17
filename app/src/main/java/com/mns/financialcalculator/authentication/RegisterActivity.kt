package com.mns.financialcalculator.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.common_toolbar_layout.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val actionBar = supportActionBar?.apply{hide()}
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent))


        // val textchanger = getResources().getString(R.string.vd_toolbar_name)

        vd_toolbar_name_change.text = "Register"

        back_button.setOnClickListener(){
            finish()
        }

        btn_share_plan_csv.visibility= View.GONE
    }
}
