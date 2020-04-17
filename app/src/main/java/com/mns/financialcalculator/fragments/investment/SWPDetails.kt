package com.mns.financialcalculator.fragments.investment

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mns.financialcalculator.MainActivity
import com.mns.financialcalculator.R
import com.mns.financialcalculator.StickyHeaderListView.StickyListHeadersListView
import com.mns.financialcalculator.adapters.SwpViewAdapter
import com.mns.financialcalculator.model.SWPView
import com.mns.financialcalculator.utils.Utils
import com.mns.financialcalculator.utils.Utils.Companion.roundUpFunction
import com.mns.financialcalculator.utils.Utils.Companion.setupPermissions
import kotlinx.android.synthetic.main.activity_swpdetails.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*
import java.util.ArrayList

class SWPDetails : AppCompatActivity(), StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
    StickyListHeadersListView.OnStickyHeaderChangedListener {

    var array: ArrayList<SWPView>? = null

    private var fadeHeader:Boolean = true

    var mSWPAmount:Int = 0
    var mInvestmentAmount_swp:Long = 0L
    var mDuration:Int = 1
    var mRate_swp:Float = 0f

    var mTotalWithdrawal:Long = 0L
    var mTotalBalance:Double = 0.0
    var mTotalProfit:Float = 0f
    var mNoWithdrawal:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swpdetails)

        val actionBar = supportActionBar?.apply{hide()}

        // val textchanger = getResources().getString(R.string.vd_toolbar_name)

        vd_toolbar_name_change.text = "SWP Detail"

        array= ArrayList()

        back_button.setOnClickListener(){
            finish()
        }

        btn_share_plan_csv.setOnClickListener(){
            setupPermissions(this)
            MakeCSVData()
        }

        if (getIntent().hasExtra("swp_calc")) {

            mSWPAmount = getIntent().getIntExtra("swp_amount", 0)
            mInvestmentAmount_swp = getIntent().getLongExtra("init_investment", 0L)
            mDuration = getIntent().getIntExtra("duration", 0)
            mRate_swp = getIntent().getFloatExtra("rate", 0F)
            mTotalWithdrawal = getIntent().getLongExtra("total_withdrawals", 0L)
            mTotalBalance = getIntent().getDoubleExtra("total_balace", 0.0)
            mTotalProfit = getIntent().getFloatExtra("total_profit", 0F)
            mNoWithdrawal = getIntent().getIntExtra("total_no_of_withdrawals", 0)

        }


        else{
            GoBack()
            Log.e("Back to","Main Activity")
            Toast.makeText(this,"app crash", Toast.LENGTH_SHORT).show()

        }

        var year_counter = 1
        var month_counter = 1

        mTotalWithdrawal = 0L
        mTotalBalance = 0.0
        mTotalProfit = 0f
        mNoWithdrawal = 0

        var value1 = 0.0
        mTotalBalance = mInvestmentAmount_swp.toDouble()

        for (i in 1..mDuration) {

            mTotalWithdrawal += mSWPAmount.toLong()
            value1 = mTotalBalance - mSWPAmount.toDouble()
            val interest: Float = value1.toFloat() * (mRate_swp / 100 / 12)
            mTotalProfit += interest
            mTotalBalance = value1 + interest
            val bean = SWPView(
                month_counter++.toLong(),
                roundUpFunction(interest.toDouble(),2)!!,
                roundUpFunction(mTotalBalance,2)!!,
                year_counter.toLong(),
                mSWPAmount.toDouble()
            )

            array!!.add(bean)

            if (i % 12 == 0) {
                year_counter++
                month_counter = 1
            }

            tv_swp_cv_tot_with.setText("Total Withdrawal:".plus(roundUpFunction(mTotalWithdrawal.toDouble(),2)))

            if (mTotalBalance < mSWPAmount) {
                break
            }


            val mAdapter = SwpViewAdapter( this,array!!)//(ArrayList<ViewSIPPlanitems>())
            swp_stickyheader.adapter = mAdapter
            swp_stickyheader.divider = null

            swp_stickyheader.setDrawingListUnderStickyHeader(true)
            swp_stickyheader.setAreHeadersSticky(true)



        }


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

    override fun onStickyHeaderChanged(l: StickyListHeadersListView?, header: View?, itemPosition: Int, headerId: Long) {

        header!!.setAlpha(1F);
    }

    fun MakeCSVData() {
        val items = ArrayList<Array<String>>()
        // init
        val title = arrayOf<String>("", "", "SWP Details")
        items.add(title)
        val black_line = arrayOf<String>("")
        items.add(black_line)
        val field1 = arrayOf<String>("SWP Amount", mSWPAmount.toString(), "", "Init Investment", mInvestmentAmount_swp.toString())
        items.add(field1)
        val field2 = arrayOf<String>("Duration", mDuration.toString() + " months", "", "Rate of Returns", mRate_swp.toString() + " %")
        items.add(field2)
        items.add(black_line)
        val column_title = arrayOf<String>("Year", "Month", "Balance Begin", "Withdrawal", "Interest", "Balance End")
        items.add(column_title)
        // ==//
        var year_counter = 1
        var month_counter = 1
        mTotalWithdrawal = 0
        mTotalBalance = 0.0
        mTotalProfit = 0f
        mNoWithdrawal = 0
        var value1 = 0.0
        mTotalBalance = mInvestmentAmount_swp.toDouble()
        for (i in 1..mDuration)
        {
            val mBalBegin = mTotalBalance
            mTotalWithdrawal += mSWPAmount
            value1 = mTotalBalance - mSWPAmount.toDouble()
            val interest = value1.toFloat() * (mRate_swp / 100 / 12)
            mTotalProfit += interest
            mTotalBalance = value1 + interest // 9579
            val column_data = arrayOf<String>(year_counter.toString() + "", month_counter++.toString() + "", Utils.FormatString(mBalBegin), mSWPAmount.toString(),interest.toString(), mTotalBalance.toString())
            items.add(column_data)
            if (i % 12 == 0)
            {
                year_counter++
                month_counter = 1
            }

            if (mTotalBalance < mSWPAmount)
                break

        }
        val footer_data = arrayOf<String>("", "Total", "", mTotalWithdrawal.toString(), mTotalProfit.toString(), "")
        items.add(footer_data)
        Utils.ExportToCSV(this, "SWP Details ", items)
    }

}
