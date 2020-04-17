package com.mns.financialcalculator.fragments.investment

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
import com.mns.financialcalculator.adapters.SipPlannerViewAdapter
import com.mns.financialcalculator.model.ViewSIPPlanitems
import com.mns.financialcalculator.utils.Utils.Companion.ExportToCSV
import com.mns.financialcalculator.utils.Utils.Companion.roundUpFunction
import com.mns.financialcalculator.utils.Utils.Companion.setupPermissions
import kotlinx.android.synthetic.main.activity_view_sipplan.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*

class ViewSipPlanner : AppCompatActivity(), StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
    StickyListHeadersListView.OnStickyHeaderChangedListener {

    var array_planner:ArrayList<ViewSIPPlanitems> ?=null
    private var fadeHeader:Boolean = true


    var mFinancialGoal:Double = 0.0
    var mRate:Float = 0f
    var mPeriod:Int = 0

    var mSIPValue:Double = 0.0
    var mInvestmentAmount:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_sipplan)

        Log.e("detail","coming")

        val actionBar = supportActionBar?.apply{hide()}
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent))


        // val textchanger = getResources().getString(R.string.vd_toolbar_name)

        vd_toolbar_name_change.text = "SIP Detail"


        array_planner= ArrayList()

        back_button.setOnClickListener(){
            finish()
        }

        btn_share_plan_csv.setOnClickListener(){
            setupPermissions(this)
            MakeCSVData()
            //Toast.makeText(this,"UD",Toast.LENGTH_SHORT).show()
        }



        if (getIntent().hasExtra("sip_plan")) {

            mFinancialGoal = getIntent().getDoubleExtra("finance_goal",0.0)
            mPeriod = getIntent().getIntExtra("period", 0)
            mRate = getIntent().getFloatExtra("rate",0F)

            mSIPValue = getIntent().getDoubleExtra("sip_value", 0.0)
            mInvestmentAmount = getIntent().getDoubleExtra("inv_amount", 0.0)


        }


        else{
            GoBack()
            Log.e("Back to","Main Activity")
            Toast.makeText(this,"app crash", Toast.LENGTH_SHORT).show()

        }

        /*sip planner calculation*/

        var year_counter:Int = 1
        var month_counter:Int = 1
        var d1:Float = 0f
        //val d2:Float = 0f
        val d5 = mSIPValue.toFloat()
        val f3 = (mRate / 100) / 12

        for (i in 1..mPeriod) {
            val d8 = d1
            val d9 = ((d8 + d5) * f3)
            d1 = d9 + (d8 + d5)

            val bean2 = ViewSIPPlanitems(
                month_counter++.toLong(),
                roundUpFunction(d9.toDouble(),2)!!,
                roundUpFunction(d1.toDouble(),2)!!,
                year_counter.toLong(),
                roundUpFunction(mSIPValue,2)!!
            )
            /*bean.Bal = d1
            bean.IntAmt = mSIPValue
            bean.Interest = d9
            bean.Month = month_counter++
            bean.Year = year_counter*/
            array_planner!!.add(bean2)
            if (i % 12 == 0) {
                year_counter++
                month_counter = 1
            }
        }

        val mAdapter2 = SipPlannerViewAdapter( this,array_planner!!)//(ArrayList<ViewSIPPlanitems>())
        sip_view_sticky.adapter = mAdapter2
        sip_view_sticky.divider = null

        sip_view_sticky.setDrawingListUnderStickyHeader(true)
        sip_view_sticky.setAreHeadersSticky(true)


        view_sip_totinv.setText("Total Investment:".plus(roundUpFunction(mInvestmentAmount,2)))


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
        //init
        val title = arrayOf<String>("", "", "SIP Planner Details")
        items.add(title)
        val black_line = arrayOf<String>("")
        items.add(black_line)
        val field1 = arrayOf<String>("Financial Goal",mFinancialGoal.toString())
        items.add(field1)
        val field2 = arrayOf<String>("Investment Period", mPeriod.toString() + " months", "", "Rate of Returns", (mRate.toString()) + " %")
        items.add(field2)
        items.add(black_line)
        val column_title = arrayOf<String>("Year", "Month", "Balance Begin", "Investment", "Interest", "Balance End")
        items.add(column_title)
        //==//

        var year_counter = 1
        var month_counter = 1
        var d1 = 0f
        val d2 = 0f
        val d5 = mSIPValue.toFloat()
        val f3 = mRate / 100 / 12
        var mTotalInvestment = 0.0
        var mTotalInterest = 0.0
        for (i in 1..mPeriod)
        { // 120
            val d8 = d1
            val d9 = ((d8 + d5) * f3)
            d1 = d9 + (d8 + d5)
            val column_data = arrayOf<String>(year_counter.toString(), month_counter++.toString(),
                d8.toString(), mSIPValue.toString(), d9.toString(), d1.toString())

            items.add(column_data)
            if (i % 12 == 0)
            {
                year_counter++
                month_counter = 1
            }
            mTotalInvestment += mSIPValue
            mTotalInterest += d9.toDouble()
        }
        val footer_data = arrayOf<String>("", "Total", "", mTotalInvestment.toString(), mTotalInterest.toString(), "")
        items.add(footer_data)
        ExportToCSV(this, "SIP Planner Details ", items)
    }
}
