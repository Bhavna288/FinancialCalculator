package com.mns.financialcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat

class Splashscreen : AppCompatActivity() {

    private var mDelayHandler: Handler? =null
    private val SPLASH_DELAY:Long = 3000

    internal val mRunnable: Runnable = Runnable {
        if(!isFinishing){

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        // status bar color change
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
