package com.mns.financialcalculator.fragments.investment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mns.financialcalculator.R
import com.mns.financialcalculator.StickyHeaderListView.StickyListHeadersListView
import com.mns.financialcalculator.adapters.FdViewAdapter
import com.mns.financialcalculator.model.FdView
import com.mns.financialcalculator.utils.Utils.Companion.ExportToCSV
import com.mns.financialcalculator.utils.Utils.Companion.roundUpFunction
import com.mns.financialcalculator.utils.Utils.Companion.setupPermissions
import kotlinx.android.synthetic.main.activity_view_fixed_deposit_plan.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*
import kotlinx.android.synthetic.main.fd_row_file.view.*
import java.util.*
import kotlin.collections.ArrayList


class ViewFixedDepositPlan : AppCompatActivity(), StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
    StickyListHeadersListView.OnStickyHeaderChangedListener {

    var array:ArrayList<FdView>?=null
    private var fadeHeader:Boolean = true

    var mPrincipalAmount:Double = 0.0
    var mRate:Float = 0f
    var mTenure:Int = 0

    var mMaturityValue:Double = 0.0
    var mTotalInterest:Double = 0.0

    var iCompounding = intArrayOf(1, 3, 6, 12)
    var mCompounding:Int = 0//iCompounding.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_fixed_deposit_plan)

        array = ArrayList()

        val actionBar = supportActionBar?.apply{hide()}
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent))


        // val textchanger = getResources().getString(R.string.vd_toolbar_name)

        vd_toolbar_name_change.text = "Fixed Deposit Detail"

        back_button.setOnClickListener(){
            finish()
        }

        btn_share_plan_csv.setOnClickListener(){
            setupPermissions(this)
            MakeCSVData()
        }

        if (getIntent().hasExtra("principal_amt")) {

            mPrincipalAmount = getIntent().getDoubleExtra("principal_amt", 0.0)
            mRate = getIntent().getFloatExtra("rate", 0f)
            mTenure = getIntent().getIntExtra("tenure", 0)
            mCompounding = getIntent().getIntExtra("compounding", 0)

        }
        else{
            Log.e("Back to","Main Activity")
            Toast.makeText(this,"app crash", Toast.LENGTH_SHORT).show()
        }

        mTotalInterest = 0.0
        mMaturityValue = 0.0

        var d1:Double = mPrincipalAmount
        val f:Float = mRate / 100.0f / 12.0f
        var i:Int = 0
        var d2:Double = 0.0

        for (j in 1..mTenure) {

            val interest:Double = d1 * f
            d2 += interest
            mTotalInterest += interest

            i++

            if (i == mCompounding) {
                i = 0
                d1 += d2
                d2 = 0.0
            }

            val bean = FdView(j, 0, roundUpFunction(interest,2)!!, roundUpFunction(d1,2)!!)

            array!!.add(bean)
        }

             mMaturityValue = d1 + d2

        val mAdapter = FdViewAdapter( this,array!!)//(ArrayList<ViewSIPPlanitems>())
        fdview_stickyheader.adapter = mAdapter
        fdview_stickyheader.divider = null

        fdview_stickyheader.setDrawingListUnderStickyHeader(true)
        fdview_stickyheader.setAreHeadersSticky(true)

        AddFooter()


    }

    fun AddFooter(){
        val view: View = layoutInflater.inflate(R.layout.fd_row_file, null)

        view?.fdview_month.setText("Total")
        view?.fdview_fd_bal.setText("")
        view?.fdview_int.setText(roundUpFunction(mTotalInterest.toDouble(),2).toString())


        view?.fdview_month.setTextColor(Color.WHITE)
        //fdview_fd_bal.setTextColor(Color.WHITE)
        view?.fdview_int.setTextColor(Color.WHITE)


        fdview_stickyheader.addFooterView(view)
    }

    override fun onStickyHeaderOffsetChanged(
        l: StickyListHeadersListView?, header: View?, offset: Int) {

        if (fadeHeader
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
        ) {
            header!!.alpha = 1 - offset / header.measuredHeight.toFloat()
        }
    }

    override fun onStickyHeaderChanged(
        l: StickyListHeadersListView?,
        header: View?, itemPosition: Int, headerId: Long) {

        header!!.setAlpha(1f)
    }

    fun MakeCSVData() {
        val items = ArrayList<Array<String>>()
        // init
        val title =
            arrayOf("", "", "Fixed Deposite Details")
        items.add(title)
        val black_line = arrayOf("")
        items.add(black_line)
        val field1 = arrayOf(
            "Principal Amount",
            mPrincipalAmount.toString(),
            ""
            ,
            "PaymentMode",resources.getStringArray(R.array.fixeddeposit_arrays)
                    [Arrays.binarySearch(iCompounding,mCompounding)]
            //iCompounding.toUIntArray().toString()
        )
        items.add(field1)
        val field2 = arrayOf(
            "Tenure",
            mTenure.toString() + "",
            "months",
            "Interest Rate",
            mRate.toString() + " %"
        )
        items.add(field2)
        items.add(black_line)
        val column_title =
            arrayOf("Month", "Interest", "FD Balance")
        items.add(column_title)
        // ==//
        mTotalInterest = 0.0
        mMaturityValue = 0.0
        var d1 = mPrincipalAmount
        val f = mRate / 100.0f / 12.0f
        var i = 0
        var d2 = 0.0
        for (j in 1..mTenure) {
            val interest = d1 * f
            d2 += interest
            mTotalInterest += interest
            i++
            if (i == mCompounding) {
                i = 0
                d1 += d2
                d2 = 0.0
            }
            val column_data = arrayOf<String>(
                j.toString(), interest.toString(),
                d1.toString()
            )
            items.add(column_data)
        }
        mMaturityValue = d1 + d2
        val footer_data =
            arrayOf("Total", mTotalInterest.toString())
        items.add(footer_data)
        ExportToCSV(this, "Fixed Deposite Details ", items)
    }
}
