package com.mns.financialcalculator.fragments.loan


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch
import com.mns.financialcalculator.R
import com.mns.financialcalculator.utils.FinancialUtils.Companion.Loan_InterestRate
import com.mns.financialcalculator.utils.FinancialUtils.Companion.Loan_LoanAmount
import com.mns.financialcalculator.utils.FinancialUtils.Companion.Loan_NoOfPayments
import com.mns.financialcalculator.utils.FinancialUtils.Companion.pmt
import com.mns.financialcalculator.utils.Utils.Companion.roundUpFunction
import com.mns.financialcalculator.utils.Validation
import com.mns.financialcalculator.utils.Validation.Companion.checkEmptyValidation
import com.mns.financialcalculator.utils.Validation.Companion.getCurrentDate
import kotlinx.android.synthetic.main.fragment_emi_calculation_content.*
import kotlinx.android.synthetic.main.fragment_loan_content.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LoanContentFragment : Fragment() {

    var comeFrom: String? = ""

    private var calStr:String?=""

    val current = Calendar.getInstance()

    /*loan calc var*/
    var mLoanAmount: Double = 0.0
    var mEMI:Double = 0.0
    var mRate:Double = 0.0
    var mTenure: Long = 0L

    var mTotalPaid:Double = 0.0
    var mTotalInterest:Double = 0.0
    var mTotalEMI:Double = 0.0
    var mTotalTenure:Double = 0.0
    var mTotalModifiedEMI = 0.0
    var mTotalRate:Double = 0.0
    var mTotalLoanAmount:Double = 0.0


    /*emi eligibility vars*/
    var eIncome:Double = 0.0
    var exEmi:Double = 0.0
    var eTenure:Double = 0.0
    var eIntRate:Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loan_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            comeFrom = arguments?.getString("come_from")
            when (comeFrom) {
                "eligibility" -> {
                    ll_eligibility.visibility = View.VISIBLE
                    ll_emiCalc.visibility = View.GONE


                }
                "emi_calculation" -> {
                    ll_eligibility.visibility = View.GONE
                    ll_emiCalc.visibility = View.VISIBLE


                }

            }
        }

        //views
      /*  btn_loan_emi.setOnClickListener() {


            ll_loanCalc_emi.visibility = View.VISIBLE
            ll_loanCalc_tenure.visibility = View.GONE
            ll_loanCalc_amt.visibility = View.GONE
            ll_loanCalc_rate.visibility = View.GONE

        }

        btn_loan_tenure.setOnClickListener() {

            ll_loanCalc_emi.visibility = View.GONE
            ll_loanCalc_tenure.visibility = View.VISIBLE
            ll_loanCalc_amt.visibility = View.GONE
            ll_loanCalc_rate.visibility = View.GONE


        }

        btn_loan_amt.setOnClickListener() {

            ll_loanCalc_emi.visibility = View.GONE
            ll_loanCalc_tenure.visibility = View.GONE
            ll_loanCalc_amt.visibility = View.VISIBLE
            ll_loanCalc_rate.visibility = View.GONE

        }

        btn_loan_rate.setOnClickListener() {

            ll_loanCalc_emi.visibility = View.GONE
            ll_loanCalc_tenure.visibility = View.GONE
            ll_loanCalc_amt.visibility = View.GONE
            ll_loanCalc_rate.visibility = View.VISIBLE


        }*/



        val labels: ArrayList<String> = ArrayList()
        labels.add("Monthly Inst")
        labels.add("Tenure")
        labels.add("Loan Amount")
        labels.add("Interest Rate")
        loan_emicalc_toggle_swt.setLabels(labels)

        loan_emicalc_toggle_swt.setOnToggleSwitchChangeListener(object: BaseToggleSwitch.OnToggleSwitchChangeListener {
            override fun onToggleSwitchChangeListener(position:Int, isChecked:Boolean) {

                when(position){

                    0-> {

                        ll_loanCalc_emi.visibility = View.VISIBLE
                        ll_loanCalc_tenure.visibility = View.GONE
                        ll_loanCalc_amt.visibility = View.GONE
                        ll_loanCalc_rate.visibility = View.GONE



                    }

                    1-> {

                        ll_loanCalc_emi.visibility = View.GONE
                        ll_loanCalc_tenure.visibility = View.VISIBLE
                        ll_loanCalc_amt.visibility = View.GONE
                        ll_loanCalc_rate.visibility = View.GONE


                    }

                    2-> {

                        ll_loanCalc_emi.visibility = View.GONE
                        ll_loanCalc_tenure.visibility = View.GONE
                        ll_loanCalc_amt.visibility = View.VISIBLE
                        ll_loanCalc_rate.visibility = View.GONE

                    }

                    3-> {

                        ll_loanCalc_emi.visibility = View.GONE
                        ll_loanCalc_tenure.visibility = View.GONE
                        ll_loanCalc_amt.visibility = View.GONE
                        ll_loanCalc_rate.visibility = View.VISIBLE
                    }
                }



            }
        })



        //eligibility calculations

        btn_elig_calc.setOnClickListener(){

            if(checkEmptyValidation(elig_income,elig_income_til) && checkEmptyValidation(elig_tenure,elig_tenure_til)
                && checkEmptyValidation(elig_int_rate,elig_int_rate_til)){

                eIncome = elig_income.text.toString().toDouble()

                exEmi = 0.0

                if (elig_ex_emi.length()!=0) {
                    exEmi = elig_ex_emi.text.toString().toDouble()
                }

                eTenure = elig_tenure.text.toString().toDouble()
                eIntRate = elig_int_rate.text.toString().toDouble()



                if (eIntRate > 100) {
                    elig_int_rate_til.setError("Interest Rate must be between 0 to 100");
                    elig_int_rate_til.requestFocus()
                    return@setOnClickListener

                }

                val e0:Double = eIncome * 0.6
                val e1:Double = e0 - exEmi

                // per lac emi calculations
                val perLacEmi:Double = pmt(
                    eIntRate / 1200.0, eTenure.toLong(), -100000.0,
                    0.0, 0
                )


                val e2:Double = e1/perLacEmi
                val e3:Double = e2 * 100000
                val loanElgAmt:Double = e3
                val finalEmi:Double = e1


                if(exEmi>e0){

                    Toast.makeText(activity!!,"Existing EMI is higher check again!",Toast.LENGTH_SHORT).show()

                }
                else{

                    elig_result.visibility=View.VISIBLE


                    elig_loan_eli.setText(roundUpFunction(loanElgAmt,2).toString())
                    elig_emi.setText(roundUpFunction(finalEmi,2).toString())

                }




            }

        }

        // emi calculations fragment

        //emi calculations

        btn_loancalc_calc_emi.setOnClickListener(){

            if(checkEmptyValidation(loancalc_emi_loan_amt,loancalc_emi_loan_amt_til)
                && checkEmptyValidation(loancalc_emi_ten,til_loanCalcTenure_emi) &&
                checkEmptyValidation(loancalc_emi_int_rate,loancalc_emi_int_rate_til)){


                mRate = loancalc_emi_int_rate.text.toString().toDouble()
                mLoanAmount = loancalc_emi_loan_amt.text.toString().toDouble()
                mTenure = loancalc_emi_ten.text.toString().toLong()

                if (swt_loanCalc_emi.isChecked) {
                    mTenure *= 12
                }

                if (mTenure > 1200) {
                    til_loanCalcTenure_emi.setError("Tenure in month must be 0 to 1200")
                    til_loanCalcTenure_emi.requestFocus()
                    return@setOnClickListener

                }

                if (mRate > 100) {
                    loancalc_emi_int_rate_til.setError("Interest Rate must be between 0 to 100");
                    loancalc_emi_int_rate_til.requestFocus()
                    return@setOnClickListener

                }

                mEMI = pmt(
                    mRate / 1200.0, mTenure, -mLoanAmount,
                    0.0, 0
                )

                loancalc_emi_result.visibility=View.VISIBLE
                loancalc_tenure_result.visibility=View.GONE
                loancalc_amount_result.visibility=View.GONE
                loancalc_rate_result.visibility=View.GONE

                mTotalPaid = mEMI * mTenure
                mTotalInterest = mTotalPaid - mLoanAmount

                loancalc_es_tot_paid.setText(roundUpFunction(mTotalPaid,2).toString())
                loancalc_es_tot_int.setText(roundUpFunction(mTotalInterest,2).toString())
                loancalc_es_emi.setText(roundUpFunction(mEMI,2).toString())



                btn_viewdocs_lt_emi.setOnClickListener(){
                    //Toast.makeText(context,"under dev.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity!!, ViewEmiCalc::class.java)

                    mEMI = loancalc_es_emi.text.toString().toDouble()

                    val msg = "emi"
                    intent.putExtra("emi",msg)
                    intent.putExtra("loanamt",mLoanAmount)
                    intent.putExtra("loanemi",mEMI)
                    intent.putExtra("loanrate",mRate)
                    intent.putExtra("loantenure",mTenure)
                    intent.putExtra("calendar",calStr)
                    startActivity(intent)
                }

                btn_share_lt_emi.setOnClickListener(){
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Loan Calculator" + "\n\nLoan amount:\t" +loancalc_emi_loan_amt.text.toString() +  "\n\ntenure:\t" +loancalc_emi_ten.text.toString()
                                + "\n\nInterest rate:\t" +loancalc_emi_int_rate.text.toString() +  "\n\nTotal paid:\t" +loancalc_es_tot_paid.text.toString()
                                + "\n\nTotal interest:\t" + loancalc_es_tot_int.text.toString() + "\n\nEMI:\t" + loancalc_es_emi.text.toString()

                    )
                    intent.type = "text/plain"

                    startActivity(Intent.createChooser(intent, "Share Via"))
                }

            }

            else{

                btn_share_lt_emi.isClickable=false
                btn_viewdocs_lt_emi.isClickable=false

            }
        }


        //tenure calculations

        btn_loancalc_calc_ten.setOnClickListener(){

            if(checkEmptyValidation(loancalc_ten_loan_amt,loancalc_ten_loan_amt_til)
                && checkEmptyValidation(loancalc_ten_int_rate,loancalc_ten_int_rate_til) &&
                checkEmptyValidation(loancalc_ten_emi,loancalc_ten_emi_til)){

                mLoanAmount = loancalc_ten_loan_amt.text.toString().toDouble()
                mRate = loancalc_ten_int_rate.text.toString().toDouble()
                mEMI = loancalc_ten_emi.text.toString().toDouble()

                if (mRate > 100) {
                    loancalc_ten_int_rate_til.setError("Interest Rate must be between 0 to 100");
                    loancalc_ten_int_rate_til.requestFocus()
                    return@setOnClickListener

                }

                mTenure = Loan_NoOfPayments(mLoanAmount, mRate, mEMI)

                mTotalModifiedEMI = pmt(
                    mRate / 1200.0, mTenure,
                    -mLoanAmount, 0.0, 0
                )

                loancalc_emi_result.visibility=View.GONE
                loancalc_tenure_result.visibility=View.VISIBLE
                loancalc_amount_result.visibility=View.GONE
                loancalc_rate_result.visibility=View.GONE

                mTotalPaid = mEMI * mTenure
                mTotalInterest = mTotalPaid - mLoanAmount

                loancalc_ts_tot_paid.setText(roundUpFunction(mTotalPaid,2).toString())
                loancalc_ts_tot_int.setText(roundUpFunction(mTotalInterest,2).toString())
                loancalc_ts_ten.setText(mTenure.toString())
                loancalc_ts_modified_emi.setText(roundUpFunction(mTotalModifiedEMI,2).toString())



                btn_viewdocs_lt_tenure.setOnClickListener(){
                    //Toast.makeText(context,"under dev.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity!!, ViewEmiCalc::class.java)
                    val msg = "tenure"
                    intent.putExtra("tenure",msg)
                    intent.putExtra("loanamt",mLoanAmount)
                    intent.putExtra("loanemi",mEMI)
                    intent.putExtra("loanrate",mRate)
                    intent.putExtra("loantenure",mTenure)
                    intent.putExtra("calendar",calStr)
                    //intent.putExtra("loantenure",mTenure)
                    //intent.putExtra("calendar",current)
                    startActivity(intent)
                }

                btn_share_lt_tenure.setOnClickListener(){
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Loan Calculator" + "\n\nLoan amount:\t" +loancalc_ten_loan_amt.text.toString() +  "\n\ntenure:\t" +loancalc_emi_ten.text.toString()
                                + "\n\nInterest rate:\t" +loancalc_ten_int_rate.text.toString() +  "\n\nTotal paid:\t" +loancalc_es_tot_paid.text.toString()
                                + "\n\nTotal interest:\t" + loancalc_es_tot_int.text.toString() + "\n\nEMI:\t" + loancalc_es_emi.text.toString()

                    )
                    intent.type = "text/plain"

                    startActivity(Intent.createChooser(intent, "Share Via"))
                }

            }

            else{

                btn_share_lt_tenure.isClickable=false
                btn_viewdocs_lt_tenure.isClickable=false

            }



            }



        //amount calculations

        btn_loancalc_calc_amt.setOnClickListener(){

            if(checkEmptyValidation(loancalc_amt_ten,til_amt_loanCalcTenure)
                && checkEmptyValidation(loancalc_amt_int_rate,loancalc_amt_int_rate_til) &&
                checkEmptyValidation(loancalc_amt_emi,loancalc_amt_emi_til)){


                mEMI = loancalc_amt_emi.text.toString().toDouble()
                mRate = loancalc_amt_int_rate.text.toString().toDouble()
                mTenure = loancalc_amt_ten.text.toString().toLong()



                if (swt_loanCalc_amt.isChecked) {
                    mTenure *= 12
                }

                if (mTenure > 1200) {
                    til_amt_loanCalcTenure.setError("Tenure in month must be 0 to 1200")
                    til_amt_loanCalcTenure.requestFocus()
                    return@setOnClickListener

                }

                if (mRate > 100) {
                    loancalc_amt_int_rate_til.setError("Interest Rate must be between 0 to 100");
                    loancalc_amt_int_rate_til.requestFocus()
                    return@setOnClickListener

                }

                mLoanAmount = Loan_LoanAmount(mTenure, mRate, mEMI).toDouble()

                loancalc_emi_result.visibility = View.GONE
                loancalc_tenure_result.visibility = View.GONE
                loancalc_amount_result.visibility = View.VISIBLE
                loancalc_rate_result.visibility = View.GONE

                mTotalPaid = mEMI * mTenure
                mTotalInterest = mTotalPaid - mLoanAmount

                loancalc_as_tot_paid.setText(roundUpFunction(mTotalPaid,2).toString())
                loancalc_as_tot_int.setText(roundUpFunction(mTotalInterest,2).toString())
                loancalc_as_loan_amt.setText(roundUpFunction(mLoanAmount,2).toString())



                btn_viewdocs_lt_amt.setOnClickListener() {
                    //Toast.makeText(context,"under dev.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity!!, ViewEmiCalc::class.java)

                    //mLoanAmount = 0.0

                    /*if( fieldValidation(loancalc_loan_amt,loancalc_loan_amt_til)){

                        mLoanAmount = loancalc_loan_amt.text.toString().toDouble()
                    }*/

                    mLoanAmount = loancalc_as_loan_amt.text.toString().toDouble()

                    val msg = "amount"
                    intent.putExtra("amount", msg)
                    intent.putExtra("loanamt",mLoanAmount)
                    intent.putExtra("loanemi", mEMI)
                    intent.putExtra("loanrate", mRate)
                    intent.putExtra("loantenure", mTenure)
                    intent.putExtra("calendar", calStr)
                    startActivity(intent)
                }

                btn_share_lt_amt.setOnClickListener() {
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Loan Calculator" + "\n\nLoan amount:\t" + loancalc_emi_loan_amt.text.toString() + "\n\ntenure:\t" + loancalc_amt_ten.text.toString()
                                + "\n\nInterest rate:\t" + loancalc_amt_int_rate.text.toString() + "\n\nTotal paid:\t" + loancalc_es_tot_paid.text.toString()
                                + "\n\nTotal interest:\t" + loancalc_es_tot_int.text.toString() + "\n\nEMI:\t" + loancalc_es_emi.text.toString()

                    )
                    intent.type = "text/plain"

                    startActivity(Intent.createChooser(intent, "Share Via"))
                }

            } else {

                btn_share_lt_amt.isClickable = false
                btn_viewdocs_lt_amt.isClickable = false

            }

            }


        //rate calculations

        btn_loancalc_calc_rate.setOnClickListener(){

            if(checkEmptyValidation(loancalc_rate_loan_amt,loancalc_rate_loan_amt_til)
                && checkEmptyValidation(loancalc_rate_ten,til_rate_loanCalcTenure) &&
                checkEmptyValidation(loancalc_rate_emi,loancalc_rate_emi_til)){

                mEMI = loancalc_rate_emi.text.toString().toDouble()
                mLoanAmount = loancalc_rate_loan_amt.text.toString().toDouble()
                mTenure = loancalc_rate_ten.text.toString().toLong()

                if (swt_loanCalc_rate.isChecked) {
                    mTenure *= 12
                }

                if (mTenure > 1200) {
                    til_rate_loanCalcTenure.setError("Tenure in month must be 0 to 1200")
                    til_rate_loanCalcTenure.requestFocus()
                    return@setOnClickListener

                }

                mRate = Loan_InterestRate(mLoanAmount, mTenure, mEMI)

                loancalc_emi_result.visibility=View.GONE
                loancalc_tenure_result.visibility=View.GONE
                loancalc_amount_result.visibility=View.GONE
                loancalc_rate_result.visibility=View.VISIBLE

                mTotalPaid = mEMI * mTenure
                mTotalInterest = mTotalPaid - mLoanAmount

                loancalc_rs_tot_paid.setText(roundUpFunction(mTotalPaid,2).toString())
                loancalc_rs_tot_int.setText(roundUpFunction(mTotalInterest,2).toString())
                loancalc_rs_int_rate.setText(roundUpFunction(mRate,2).toString())

                btn_viewdocs_lt_rate.setOnClickListener(){
                    // Toast.makeText(context,"under dev.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity!!, ViewEmiCalc::class.java)

                    mRate = loancalc_rs_int_rate.text.toString().toDouble()


                    val msg = "rate"
                    intent.putExtra("rate",msg)
                    intent.putExtra("loanamt",mLoanAmount)
                    intent.putExtra("loanemi",mEMI)
                    intent.putExtra("loanrate",mRate)
                    intent.putExtra("loantenure",mTenure)
                    intent.putExtra("calendar",calStr)
                    //intent.putExtra("calendar",current)
                    startActivity(intent)
                }

                btn_share_lt_rate.setOnClickListener(){
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Loan Calculator" + "\n\nLoan amount:\t" +loancalc_rate_loan_amt.text.toString() +  "\n\ntenure:\t" +loancalc_rate_ten.text.toString()
                                + "\n\nInterest rate:\t" +loancalc_rate_int_rate.text.toString() +  "\n\nTotal paid:\t" +loancalc_es_tot_paid.text.toString()
                                + "\n\nTotal interest:\t" + loancalc_es_tot_int.text.toString() + "\n\nEMI:\t" + loancalc_es_emi.text.toString()

                    )
                    intent.type = "text/plain"

                    startActivity(Intent.createChooser(intent, "Share Via"))
                }

            }

            else{

                btn_share_lt_rate.isClickable=false
                btn_viewdocs_lt_rate.isClickable=false

            }

            }



        //emi date month
        btn_loantools_month_emi.setText(getCurrentDate("MMM-yyyy"))
        calStr= Validation.getCurrentDate("MMM-yy")


        val datePicker =  DatePickerDialog.OnDateSetListener()
        { view, year, month, dayOfMonth ->

            current.set(Calendar.YEAR, year)
            current.set(Calendar.MONTH, month)
            current.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()

        }

        btn_loantools_month_emi.setOnClickListener() {

            DatePickerDialog(activity!!, datePicker, current.get(Calendar.YEAR), current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH)).show()
        }


        if (swt_loanCalc_emi.isChecked) {
            til_loanCalcTenure_emi.hint = "Tenure( in Years)"
        } else {
            til_loanCalcTenure_emi.hint = "Tenure( in Months)"
        }

        swt_loanCalc_emi.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                til_loanCalcTenure_emi.hint = "Tenure( in Years)"
            } else {
                til_loanCalcTenure_emi.hint = resources.getString(R.string.lt_lcalc_ten).plus("( in Months)")
            }
        }



        //tenure date month
        btn_loantools_month_ten.setText(getCurrentDate("MMM-yyyy"))
        calStr= Validation.getCurrentDate("MMM-yy")


        val datePicker2 =  DatePickerDialog.OnDateSetListener()
        { view, year, month, dayOfMonth ->

            current.set(Calendar.YEAR, year)
            current.set(Calendar.MONTH, month)
            current.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()

        }

        btn_loantools_month_ten.setOnClickListener() {

            DatePickerDialog(activity!!, datePicker2, current.get(Calendar.YEAR), current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH)).show()
        }


        //amount date month
        btn_loantools_month_amt.setText(getCurrentDate("MMM-yyyy"))
        calStr= Validation.getCurrentDate("MMM-yy")


        val datePicker3 =  DatePickerDialog.OnDateSetListener()
        { view, year, month, dayOfMonth ->

            current.set(Calendar.YEAR, year)
            current.set(Calendar.MONTH, month)
            current.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()

        }

        btn_loantools_month_amt.setOnClickListener() {

            DatePickerDialog(activity!!, datePicker3, current.get(Calendar.YEAR), current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH)).show()
        }


        if (swt_loanCalc_amt.isChecked) {
            til_amt_loanCalcTenure.hint = "Tenure( in Years)"
        } else {
            til_amt_loanCalcTenure.hint = "Tenure( in Months)"
        }

        swt_loanCalc_amt.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                til_amt_loanCalcTenure.hint = "Tenure( in Years)"
            } else {
                til_amt_loanCalcTenure.hint = resources.getString(R.string.lt_lcalc_ten).plus("( in Months)")
            }
        }


        //rate date month
        btn_loantools_month_rate.setText(getCurrentDate("MMM-yyyy"))
        calStr= Validation.getCurrentDate("MMM-yy")


        val datePicker4 =  DatePickerDialog.OnDateSetListener()
        { view, year, month, dayOfMonth ->

            current.set(Calendar.YEAR, year)
            current.set(Calendar.MONTH, month)
            current.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()

        }

        btn_loantools_month_rate.setOnClickListener() {

            DatePickerDialog(activity!!, datePicker4, current.get(Calendar.YEAR), current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH)).show()
        }


        if (swt_loanCalc_rate.isChecked) {
            til_rate_loanCalcTenure.hint = "Tenure( in Years)"
        } else {
            til_rate_loanCalcTenure.hint = "Tenure( in Months)"
        }

        swt_loanCalc_rate.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                til_rate_loanCalcTenure.hint = "Tenure( in Years)"
            } else {
                til_rate_loanCalcTenure.hint = resources.getString(R.string.lt_lcalc_ten).plus("( in Months)")
            }
        }



    }

    fun updateLabel(){
        val dateformat = "MMM-yyyy"
        val calformat = "MMM-yy"
        val cf = SimpleDateFormat(calformat, Locale.US)
        val sdf = SimpleDateFormat(dateformat,Locale.US)
        btn_loantools_month_emi.setText(sdf.format(current.time))
        btn_loantools_month_ten.setText(sdf.format(current.time))
        btn_loantools_month_amt.setText(sdf.format(current.time))
        btn_loantools_month_rate.setText(sdf.format(current.time))
        calStr=cf.format(current.time)
    }

    }
