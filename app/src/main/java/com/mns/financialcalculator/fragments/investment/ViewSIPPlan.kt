package com.mns.financialcalculator.fragments.investment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mns.financialcalculator.MainActivity
import com.mns.financialcalculator.R
import com.mns.financialcalculator.StickyHeaderListView.StickyListHeadersListView
import com.mns.financialcalculator.adapters.SipViewAdapter
import com.mns.financialcalculator.model.ViewSIPPlanitems
import com.mns.financialcalculator.utils.Utils.Companion.ExportToCSV
import com.mns.financialcalculator.utils.Utils.Companion.roundUpFunction
import com.mns.financialcalculator.utils.Utils.Companion.setupPermissions
import kotlinx.android.synthetic.main.activity_view_sipplan.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*
import java.util.*

class ViewSIPPlan:AppCompatActivity(), StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
    StickyListHeadersListView.OnStickyHeaderChangedListener {

    //private var mListView: StickyListHeadersListView? = null
    //var mAdapter: SipViewAdapter?= null
    var array: ArrayList<ViewSIPPlanitems> ?=null

    private var fadeHeader:Boolean = true
    //var mOriginalViewHeightPool: WeakHashMap<View, Int> = WeakHashMap<View, Int>()

    var monthly_investment:Double = 0.0
    var tenure:Float = 0f
    var rate:Float = 0f
    var rate_yearly:Float = 0f
    var init_investment:Float = 0f





    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_sipplan)

        val actionBar = supportActionBar?.apply{hide()}
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent))


        // val textchanger = getResources().getString(R.string.vd_toolbar_name)

        vd_toolbar_name_change.text = "SIP Detail"


        array= ArrayList()


        back_button.setOnClickListener(){
            finish()
        }

        btn_share_plan_csv.setOnClickListener(){
            setupPermissions(this)
            MakeCSVData()
        }



        if (getIntent().hasExtra("sip_calc")) {

            monthly_investment = getIntent().getDoubleExtra("monthly_investment", 0.0)
            tenure = getIntent().getFloatExtra("tenure", 0f)
            rate = getIntent().getFloatExtra("rate", 0f)
            rate_yearly = getIntent().getFloatExtra("rate_yearly", 0f)
            init_investment = getIntent().getFloatExtra("init_investment", 0f)

        }


        else{
            GoBack()
            Log.e("Back to","Main Activity")
            Toast.makeText(this,"app crash", Toast.LENGTH_SHORT).show()

        }
        /*sip calc calculation*/
        var ans_maturity_value = init_investment.toDouble()
        var ans_investment = init_investment.toDouble()
        var year_counter:Int = 1
        var month_counter:Int = 1
        val yealy_interst = (monthly_investment * rate_yearly) / 100


        for(i:Int in 1..tenure.toInt()){


            val interest =  (((ans_maturity_value + monthly_investment) * rate )/ 100 )/ 12
            ans_maturity_value += monthly_investment + interest
            ans_investment += monthly_investment


            if (i % 12 == 0) {
                monthly_investment += yealy_interst
            }

            val bean = ViewSIPPlanitems(
                month_counter++.toLong(),
                roundUpFunction(interest,2)!!,
                roundUpFunction(ans_maturity_value,2)!!,
                year_counter.toLong(),
                roundUpFunction(monthly_investment,2)!!
            )


            array?.add(bean)

            if (i % 12 == 0) {
                year_counter++
                month_counter = 1
            }
        }

        view_sip_totinv.setText("Total Investment:".plus(roundUpFunction(ans_investment,2)))


        val mAdapter = SipViewAdapter( this,array!!)//(ArrayList<ViewSIPPlanitems>())
        sip_view_sticky.adapter = mAdapter
        sip_view_sticky.divider = null

        sip_view_sticky.setDrawingListUnderStickyHeader(true)
        sip_view_sticky.setAreHeadersSticky(true)





    }

    fun GoBack(){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onStickyHeaderOffsetChanged(l: StickyListHeadersListView?, header: View?, offset: Int) {
        if (fadeHeader && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            header!!.alpha = 1 - offset / header.measuredHeight.toFloat()
        }
    }

    override fun onStickyHeaderChanged(l: StickyListHeadersListView?, header: View?,
                                       itemPosition: Int, headerId: Long) {

        header!!.setAlpha(1F);
    }


    fun MakeCSVData() {
        val items = ArrayList<Array<String>>()
        //initialize



        val title = arrayOf<String>("", "", "SIP Details")
        items.add(title)
        val black_line = arrayOf<String>("")
        items.add(black_line)
        val field1 = arrayOf<String>("Monthly Investment", (monthly_investment.toString()), "", "Init Investment", (init_investment.toString()))
        items.add(field1)
        val field2 = arrayOf<String>("Tenure", tenure.toString() + " months", "", "Rate of Returns", (rate.toString()) + " %")
        items.add(field2)
        val field3 = arrayOf<String>("Yearly increased Rate", (rate_yearly.toString()) + " %")
        items.add(field3)
        items.add(black_line)
        val column_title = arrayOf<String>("Year", "Month", "Balance Begin", "Investment", "Interest", "Balance End")
        items.add(column_title)

        //items = ArrayList() //instantiate

        var ans_maturity_value = init_investment
        var ans_investment = init_investment
        var year_counter = 1
        var month_counter = 1
        val yealy_interst = (monthly_investment * rate_yearly) / 100
        var mTotalInterest = 0.0
        for (i in 1..tenure.toInt())
        {
            val bal_begin = ans_maturity_value
            val interest = (((ans_maturity_value + monthly_investment) * rate) / 100) / 12
            ans_maturity_value += (monthly_investment + interest).toFloat()
            ans_investment += monthly_investment.toFloat()
            if (i % 12 == 0)
            {
                monthly_investment += yealy_interst
            }
            val column_data = arrayOf<String>(year_counter.toString() + "", month_counter++.toString() + "", (bal_begin.toString()), (monthly_investment.toString()), (interest.toString()), (ans_maturity_value.toString()))
            items.add(column_data)
            if (i % 12 == 0)
            {
                year_counter++
                month_counter = 1
            }
            mTotalInterest += interest
        }
        val footer_data = arrayOf<String>("", "Total", "", (ans_investment.toString()), (mTotalInterest.toString()), "")
        items.add(footer_data)
        ExportToCSV(this, "SIP Details ", items)

    }




}

