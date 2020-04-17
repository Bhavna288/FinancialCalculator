package com.mns.financialcalculator.fragments.loan

import android.content.Intent
import android.graphics.Color
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
import com.mns.financialcalculator.adapters.LoanViewAdapter
import com.mns.financialcalculator.model.LoanView
import com.mns.financialcalculator.utils.Utils
import com.mns.financialcalculator.utils.Utils.Companion.roundUpFunction
import com.mns.financialcalculator.utils.Utils.Companion.setupPermissions
import kotlinx.android.synthetic.main.activity_view_emi_calc.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*
import kotlinx.android.synthetic.main.loan_row_file.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ViewEmiCalc : AppCompatActivity(), StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
    StickyListHeadersListView.OnStickyHeaderChangedListener {

    var array: ArrayList<LoanView> ?=null
    private var fadeHeader:Boolean = true


    var mLoanAmount = 0.0
    var mEMI:Double = 0.0
    var mRate = 0.0
    var mTenure: Long = 0
    var current: Calendar = Calendar.getInstance()
    var calStr:String?=null

    /*formatting month*/
    val calformat = "MMM-yy"
    var cf = SimpleDateFormat(calformat, Locale.US)
    //calStr=cf.format(current.time)


    var mTotalBalance = 0.0
    var mTotalInterest = 0.0
    var mTotalPrincipal = 0.0
    var mTotalEMI = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_emi_calc)

        array = ArrayList()

        val actionBar = supportActionBar?.apply{hide()}
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent))


        // val textchanger = getResources().getString(R.string.vd_toolbar_name)

        vd_toolbar_name_change.text = "Loan Detail"

        back_button.setOnClickListener(){
            finish()
        }

        btn_share_plan_csv.setOnClickListener(){
            setupPermissions(this)
            MakeCSVData()
        }

        if (getIntent().hasExtra("emi")) {

            mLoanAmount = getIntent().getDoubleExtra("loanamt", 0.0)
            mEMI = getIntent().getDoubleExtra("loanemi",0.0)
            mRate = getIntent().getDoubleExtra("loanrate", 0.0)
            mTenure = getIntent().getLongExtra("loantenure", 0L)
            calStr = getIntent().getStringExtra("calendar")

        }


        else{
            if (getIntent().hasExtra("tenure")) {

                mLoanAmount = getIntent().getDoubleExtra("loanamt", 0.0)
                mRate = getIntent().getDoubleExtra("loanrate", 0.0)
                mEMI = getIntent().getDoubleExtra("loanemi", 0.0)
                mTenure = getIntent().getLongExtra("loantenure",0L)
                calStr = getIntent().getStringExtra("calendar")
            }


            else if (getIntent().hasExtra("amount")) {

                mRate = getIntent().getDoubleExtra("loanrate", 0.0)
                mTenure = getIntent().getLongExtra("loantenure", 0L)
                mEMI = getIntent().getDoubleExtra("loanemi", 0.0)
                mLoanAmount = getIntent().getDoubleExtra("loanamt",0.0)
                calStr = getIntent().getStringExtra("calendar")

            }

            else if (getIntent().hasExtra("rate")) {

                mLoanAmount = getIntent().getDoubleExtra("loanamt", 0.0)
                mTenure = getIntent().getLongExtra("loantenure", 0L)
                mRate = getIntent().getDoubleExtra("loanrate", 0.0)
                mEMI = getIntent().getDoubleExtra("loanemi", 0.0)
                calStr = getIntent().getStringExtra("calendar")

            }


            else{

                GoBack()
                Log.e("Back to","Main Activity")
                Toast.makeText(this,"app crash", Toast.LENGTH_SHORT).show()

            }


        }

        mTotalBalance = mLoanAmount
        mTotalInterest = 0.0
        mTotalPrincipal = 0.0
        mTotalEMI = 0.0

        for (i in 1..mTenure)
        {
            val interest = mTotalBalance * (mRate / 100 / 12)
            mTotalInterest += interest
            val principal = mEMI - interest
            mTotalPrincipal += principal
            mTotalBalance -= principal


            if (i!= 1.toLong() && current.get(Calendar.MONTH) == 3)
            {
                val bean = LoanView(
                    "Total",
                    roundUpFunction(mTotalEMI,2)!!,
                    roundUpFunction(mTotalInterest,2)!!,
                    roundUpFunction(mTotalPrincipal,2)!!,
                    "",
                    0
                )

                array!!.add(bean)
            }

            val bean = LoanView(
                cf.format(current.time).toString(),
                roundUpFunction(mEMI,2)!!,
                roundUpFunction(interest,2)!!,
                roundUpFunction(principal,2)!!,
                roundUpFunction(Math.abs(mTotalBalance),2).toString(),
                0
            )

            array?.add(bean)

            mTotalEMI +=mEMI
            current.add(Calendar.MONTH,1)
            //calStr=cf.toString()

            //cal month inc

        }

        val mAdapter = LoanViewAdapter( this,array!!)//(ArrayList<ViewSIPPlanitems>())
        loanview_stickyheader.adapter = mAdapter
        loanview_stickyheader.divider = null

        loanview_stickyheader.setDrawingListUnderStickyHeader(true)
        loanview_stickyheader.setAreHeadersSticky(true)

        AddFooter()

    }

    fun AddFooter(){
        val view: View = layoutInflater.inflate(R.layout.loan_row_file, null)

        view.loan_view_month.setText("Total")
        view.loan_view_bal.setText("")
        view.loan_view_int.setText(roundUpFunction(mTotalInterest,2).toString())
        view.loan_view_prin.setText(roundUpFunction(mTotalPrincipal,2).toString())
        view.loan_view_emi.setText(roundUpFunction(mTotalEMI,2).toString())

        view.loan_view_month.setTextColor(Color.WHITE)
        view.loan_view_bal.setTextColor(Color.WHITE)
        view.loan_view_int.setTextColor(Color.WHITE)
        view.loan_view_prin.setTextColor(Color.WHITE)
        view.loan_view_emi.setTextColor(Color.WHITE)

        loanview_stickyheader.addFooterView(view)
    }

    override fun onStickyHeaderOffsetChanged(
        l: StickyListHeadersListView?, header: View?, offset: Int) {

        if (fadeHeader && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            header!!.alpha = 1 - offset / header.measuredHeight.toFloat()
        }
    }

    override fun onStickyHeaderChanged(
        l: StickyListHeadersListView?, header: View?, itemPosition: Int, headerId: Long) {
        header!!.setAlpha(1f)
    }

    fun GoBack(){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun MakeCSVData(){

        val items = ArrayList<Array<String>>()


        // init
        val title = arrayOf("", "", "Loan Details")
        items.add(title)

        val black_line = arrayOf("")
        items.add(black_line)

        val field1 = arrayOf(
            "Loan Amount", mLoanAmount.toString(), "",
            "EMI", mEMI.toString())

        items.add(field1)

        val field2 = arrayOf(
            "Tenure",
            mTenure.toString() + "",
            "months",
            "Interest Rate",
            (mRate).toString() + " %"
        )
        items.add(field2)

        items.add(black_line)

        val column_title = arrayOf(
            "Month", "EMI", "Interest", "Principal",
            "Balance"
        )
        items.add(column_title)
        // ==//

        // ==//
        mTotalBalance = mLoanAmount
        mTotalInterest = 0.0
        mTotalPrincipal = 0.0
        mTotalEMI = 0.0

        for (i in 1..mTenure) {
            val interest = mTotalBalance * (mRate / 100 / 12) // 79
            mTotalInterest += interest
            val principal = mEMI - interest
            mTotalPrincipal += principal
            mTotalBalance -= principal
            if (i != 1L && current.get(Calendar.MONTH) == 3) {
                val column_data = arrayOf(
                    "Total",
                    mTotalEMI.toString(),
                    mTotalInterest.toString(),
                    mTotalPrincipal.toString(), ""
                )
                items.add(column_data)
            }
            val column_data = arrayOf<String>(
                //calStr!!.format("MMM-yyyy", current).toString(),
                cf.format(current.time).toString(),
                mEMI.toString(),
                interest.toString(),
                principal.toString(),
                mTotalBalance.toString()
            )
            items.add(column_data)
            mTotalEMI += mEMI
            current.add(Calendar.MONTH, 1)
        }

        val footer_data = arrayOf(
            "Total", Utils.FormatString(mTotalEMI),
            Utils.FormatString(mTotalInterest),
            Utils.FormatString(mTotalPrincipal), ""
        )
        items.add(footer_data)

        Utils.ExportToCSV(this, "Loan Details ", items)
    }
}
