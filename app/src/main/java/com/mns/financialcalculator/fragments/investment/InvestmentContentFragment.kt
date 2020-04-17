package com.mns.financialcalculator.fragments.investment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch

import com.mns.financialcalculator.R
import com.mns.financialcalculator.utils.FinancialUtils.Companion.FDMaturityValue
import com.mns.financialcalculator.utils.FinancialUtils.Companion.InvestmentValue
import com.mns.financialcalculator.utils.FinancialUtils.Companion.RDMaturityValueV1
import com.mns.financialcalculator.utils.FinancialUtils.Companion.pmt
import com.mns.financialcalculator.utils.Utils.Companion.roundUpFunction
import com.mns.financialcalculator.utils.Validation.Companion.checkEmptyValidation
import kotlinx.android.synthetic.main.fragment_fd_content.*
import kotlinx.android.synthetic.main.fragment_investment_content.*
import kotlinx.android.synthetic.main.fragment_mutual_fund_content.*
import java.sql.DriverManager


class InvestmentContentFragment : Fragment() {

    var comeFrom: String? = ""

    /*fd vars*/
    var mPrincipalAmount:Double = 0.0
    var mRate:Float = 0f
    var mTenure:Int = 0
    var mMaturityValue:Double = 0.0
    var mTotalInterest:Double = 0.0
    var iCompounding = intArrayOf(1, 3, 6, 12)
    //var mCompounding:Int = 0


    /*rd vars*/
    var mMonthlyInvestment:Double = 0.0
    /*var mRate:Float = 0f
    var mTenure:Int = 0*/
    var mTotalInvestment:Double = 0.0
    /*var mMaturityValue:Double = 0.0
    var mTotalInterest:Double = 0.0*/
    //var iCompounding = intArrayOf(1,3,6,12)
    var iCompounding_rd = intArrayOf(1,2,3,4,5,6,7,8,9,10,11,12)


    /*sip calc vars*/
    var monthly_investment:Double = 0.0
    var tenure:Float = 0f
    var rate:Float = 0f
    var rate_yearly:Float = 0f
    var init_investment:Float = 0f
    var TotalInvestment:Double = 0.0
    var TotalMaturity:Double = 0.0


    /*swp calc vars*/
    var mSWPAmount:Int = 0
    var mInvestmentAmount_swp:Long = 0L
    var mDuration:Int = 1
    var mRate_swp:Float = 0f
    var mTotalWithdrawal:Long = 0L
    var mTotalBalance:Double = 0.0
    var mTotalProfit:Float = 0f
    var mNoWithdrawal:Int = 0


    /*stp calc vars*/
    var mSTPAmount:Int = 0
    var mInvestmentAmount_stp:Long = 0L
    var mInstallments:Int = 1
    var mRateTransferor:Float = 0f
    var mRateTransferee:Float = 0f
    var mTotalTransfered:Long = 0L
    var mTotalBalTransferor:Double = 0.0
    var mTotalBalTransferee:Double = 0.0
    var mTotalProfit_stp:Float = 0f

    /*goal sip planner vars*/
    var mFinancialGoal:Double = 0.0
    //var mRate:Float = 0f
    var mPeriod:Int = 0
    var mSIPValue:Double = 0.0
    var mInvestmentAmount:Double = 0.0




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_investment_content, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            comeFrom = arguments?.getString("come_from")
            when (comeFrom) {
                "fixed_deposit" -> {
                    ll_fd.visibility = View.VISIBLE
                    ll_mut_fund.visibility = View.GONE
                    ll_goal.visibility = View.GONE

                }
                "mutual_fund" -> {
                    ll_fd.visibility = View.GONE
                    ll_mut_fund.visibility = View.VISIBLE
                    ll_goal.visibility = View.GONE

                }
                "goal" -> {
                    ll_fd.visibility = View.GONE
                    ll_mut_fund.visibility = View.GONE
                    ll_goal.visibility = View.VISIBLE
                }

            }
        }



        //fd view

        /*btn_inv_fd_fd.setOnClickListener(){

           ll_fd_fd.visibility=View.VISIBLE
           ll_fd_rd.visibility=View.GONE

       }

       btn_inv_fd_rd.setOnClickListener(){

           ll_fd_fd.visibility=View.GONE
           ll_fd_rd.visibility=View.VISIBLE

       }*/

        inv_fd_toggle_swt.setOnToggleSwitchChangeListener(object: BaseToggleSwitch.OnToggleSwitchChangeListener {
            override fun onToggleSwitchChangeListener(position:Int, isChecked:Boolean) {

                when(position){

                    0-> {

                        ll_fd_fd.visibility=View.VISIBLE
                        ll_fd_rd.visibility=View.GONE



                    }

                    1-> {

                        ll_fd_fd.visibility=View.GONE
                        ll_fd_rd.visibility=View.VISIBLE



                    }
                }



            }
        })




        //mutual fund view

        /*btn_inv_mf_sip.setOnClickListener(){

            ll_sipCalc.visibility=View.VISIBLE
            ll_swpCalc.visibility=View.GONE
            ll_stpCalc.visibility=View.GONE

        }

        btn_inv_mf_swp.setOnClickListener(){

            ll_sipCalc.visibility=View.GONE
            ll_swpCalc.visibility=View.VISIBLE
            ll_stpCalc.visibility=View.GONE

        }

        btn_inv_mf_stp.setOnClickListener(){

            ll_sipCalc.visibility=View.GONE
            ll_swpCalc.visibility=View.GONE
            ll_stpCalc.visibility=View.VISIBLE

        }*/

        inv_mf_toggle_swt.setOnToggleSwitchChangeListener(object: BaseToggleSwitch.OnToggleSwitchChangeListener {
            override fun onToggleSwitchChangeListener(position:Int, isChecked:Boolean) {

                when(position){

                    0-> {

                        ll_sipCalc.visibility=View.VISIBLE
                        ll_swpCalc.visibility=View.GONE
                        ll_stpCalc.visibility=View.GONE



                    }

                    1-> {

                        ll_sipCalc.visibility=View.GONE
                        ll_swpCalc.visibility=View.VISIBLE
                        ll_stpCalc.visibility=View.GONE


                    }

                    2-> {

                        ll_sipCalc.visibility=View.GONE
                        ll_swpCalc.visibility=View.GONE
                        ll_stpCalc.visibility=View.VISIBLE

                    }
                }



            }
        })




        //goal sip planner calculations

        btn_sipplanner_calc.setOnClickListener(){

            if(checkEmptyValidation(sipplanner_fin_goal,sipplanner_fin_goal_til) && checkEmptyValidation(sipplanner_ror,sipplanner_ror_til)
                && checkEmptyValidation(sipPlannerInvPeriod,til_sipPlannerInvPeriod)){

                mFinancialGoal = sipplanner_fin_goal.text.toString().toDouble()
                mRate = sipplanner_ror.text.toString().toFloat()
                mPeriod = sipPlannerInvPeriod.text.toString().toInt()

                if (swt_sipPlanner.isChecked) {
                    mPeriod *=  12
                }


                if(mRate > 100){
                    sipplanner_ror_til.setError("Rate of Return must be between 0 to 100")
                    sipplanner_ror_til.requestFocus()
                    return@setOnClickListener


                }else if(mPeriod > 1200){
                    til_sipPlannerInvPeriod.setError("Period value more than 1200 months not allowed")
                    til_sipPlannerInvPeriod.requestFocus()
                    return@setOnClickListener


                }

                mSIPValue  = pmt(mRate.toDouble() / 1200, mPeriod.toLong(), 0.0, -mFinancialGoal, 1)
                mInvestmentAmount = InvestmentValue(0f, mSIPValue, 0f, mPeriod, mRate / 12).toDouble()

                sipplanner_sipvalue.setText(roundUpFunction(mSIPValue,2).toString())
                sipplanner_inv_amt.setText(roundUpFunction(mInvestmentAmount,2).toString())


                sipPlanner_result.visibility = View.VISIBLE

                btn_share_sipPlanner.setOnClickListener(){

                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Sip Planner" + "\n\nFinancial Goal:\t" +sipplanner_fin_goal.text.toString() +  "\n\nRate of return:\t" +sipplanner_ror.text.toString()
                                + "\n\nInv period:\t" +sipPlannerInvPeriod.text.toString() + "\n\nInvestment Amount:\t" +sipplanner_inv_amt.text.toString()
                                + "\n\nSIP Value:\t" +sipplanner_sipvalue.text.toString()
                    )
                    intent.type = "text/plain"

                    startActivity(Intent.createChooser(intent, "Share Via"))
                }

                btn_viewdocs_sipPlanner.setOnClickListener(){
                    // Toast.makeText(activity,"Under development",Toast.LENGTH_SHORT).show()

                    /*passing tenure value to viewsipplan activity*/

                    /*val fin_goal = sipplanner_fin_goal.text.toString().toDouble()
                    val rate_o_ret = sipplanner_ror.text.toString().toFloat()
                    //val inv_per = sipPlannerInvPeriod.text.toString().toInt()
                    val inv_amt = sipplanner_inv_amt.text.toString().toDouble()
                    val sip_val = sipplanner_sipvalue.text.toString().toDouble()*/



                    val intent = Intent(activity!!, ViewSipPlanner::class.java)
                    intent.putExtra("sip_plan", comeFrom)
                    intent.putExtra("finance_goal",mFinancialGoal)
                    intent.putExtra("period", mPeriod)
                    intent.putExtra("rate",mRate)
                    intent.putExtra("inv_amount",mInvestmentAmount)
                    intent.putExtra("sip_value",mSIPValue)


                    startActivity(intent)

                }
            }
            else{
                btn_share_sipPlanner.isClickable=false
                btn_viewdocs_sipPlanner.isClickable=false
            }



        }



        //fixed deposit fragment

        //fd calculations

        if(fixeddeposit_spinner!=null){
            val adapter = ArrayAdapter.createFromResource(activity!!,
                R.array.fixeddeposit_arrays,android.R.layout.simple_spinner_dropdown_item)

            fixeddeposit_spinner.adapter = adapter

            fixeddeposit_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{


                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    Log.e("Monthly Planner",""+parent?.getItemAtPosition(position))

                    when(position){
                        0->{

                            DriverManager.println("monthly")


                        }

                        1->{
                            DriverManager.println("quaterly")

                        }

                        2->{
                            DriverManager.println("half-yearly")

                        }

                        3->{
                            DriverManager.println("yearly")

                        }

                    }

                    //mCompounding = fixeddeposit_spinner.parent.get

                    btn_fd_calc.setOnClickListener(){

                        if(checkEmptyValidation(fix_depo_prin_amt,fix_depo_prin_amt_til) && checkEmptyValidation(fdTenure,til_fdTenure)
                            && checkEmptyValidation(fix_depo_int_rate,fix_depo_int_rate_til)){



                            mPrincipalAmount = fix_depo_prin_amt.text.toString().toDouble()
                            mTenure = fdTenure.text.toString().toInt()
                            mRate = fix_depo_int_rate.text.toString().toFloat()

                            if (swt_fd.isChecked) {
                                mTenure *= 12;
                            }

                            if (mRate > 100) {
                                fix_depo_int_rate_til.setError("Interest Rate must be between 0 to 100");
                                fix_depo_int_rate_til.requestFocus()
                                return@setOnClickListener

                            } else if (mTenure > 1200) {
                                til_fdTenure.setError("Tenure value more than 1200 months not allowed");
                                til_fdTenure.requestFocus()
                                return@setOnClickListener

                            }

                            val mCompounding:Int = iCompounding[fixeddeposit_spinner.selectedItemPosition]//.get(position)

                            //mCompounding = fixeddeposit_spinner.getItemIdAtPosition(position).toInt()

                            mMaturityValue = FDMaturityValue(mPrincipalAmount, mTenure, mRate, mCompounding)
                            mTotalInterest = mMaturityValue - mPrincipalAmount

                            fix_depo_result.visibility = View.VISIBLE

                            fix_depo_mat_value.setText(roundUpFunction(mMaturityValue,2).toString())
                            fix_depo_tot_int.setText(roundUpFunction(mTotalInterest,2).toString())


                            btn_share_fd.setOnClickListener(){

                                val intent = Intent()
                                intent.action = Intent.ACTION_SEND
                                intent.putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Fixed Deposit" + "\nPrincipal amount:\t" +fix_depo_prin_amt.text.toString() +  "\nTenure:\t" +fdTenure.text.toString()
                                            + "\nInterest Rate:\t" +fix_depo_int_rate.text.toString()+ "\nPayment Method:\t" +fixeddeposit_spinner.getItemAtPosition(position)
                                            + "\nTotal Interest:\t" +fix_depo_tot_int.text.toString()+ "\nTotal Maturity:\t" +fix_depo_mat_value.text.toString()


                                )
                                intent.type = "text/plain"

                                startActivity(Intent.createChooser(intent, "Share Via"))

                            }

                            btn_viewdocs_fd.setOnClickListener(){

                                //Toast.makeText(context,"Under D",Toast.LENGTH_SHORT).show()
                                val intent = Intent(activity!!, ViewFixedDepositPlan::class.java)
                                intent.putExtra("principal_amt",mPrincipalAmount)
                                intent.putExtra("rate",mRate)
                                intent.putExtra("tenure",mTenure)
                                intent.putExtra("compounding",mCompounding)
                                startActivity(intent)
                            }
                        }

                        else{
                            btn_share_fd.isClickable=false
                            btn_viewdocs_fd.isClickable=false
                        }

                    }
                }

            }


        }




        //rd calculations

        recurringdeposit_spinner.setEnabled(false)
        recurringdeposit_spinner.setClickable(false)


        if(rec_depo_spinner!=null){

            val adapter = ArrayAdapter.createFromResource(activity!!,
                R.array.recurringdeposit_arrays,android.R.layout.simple_spinner_dropdown_item)

            rec_depo_spinner.adapter = adapter

            rec_depo_spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    Log.e("Monthly Planner",""+parent?.getItemAtPosition(position))

                    when(position){

                        0->{
                            DriverManager.println("6")
                        }

                        1->{
                            DriverManager.println("9")
                        }

                        2->{
                            DriverManager.println("12")
                        }

                        3->{
                            DriverManager.println("15")
                        }

                        4->{
                            DriverManager.println("18")
                        }

                        5->{
                            DriverManager.println("24")
                        }

                        6->{
                            DriverManager.println("30")
                        }

                        7->{
                            DriverManager.println("36")
                        }

                        8->{
                            DriverManager.println("48")
                        }

                        9->{
                            DriverManager.println("60")
                        }

                        10->{
                            DriverManager.println("84")
                        }

                        11->{
                            DriverManager.println("120")
                        }
                    }

                    btn_rec_depo_calculate.setOnClickListener(){

                        if(checkEmptyValidation(rec_depo_mon_inv,rec_depo_mon_inv_til)
                            && checkEmptyValidation(rec_depo_int_rate,rec_depo_int_rate_til)){

                            mMonthlyInvestment = rec_depo_mon_inv.text.toString().toDouble()
                            mRate = rec_depo_int_rate.text.toString().toFloat()
                            mTenure = rec_depo_spinner.getSelectedItem().toString().toInt()

                            if (mRate > 100) {
                                rec_depo_int_rate_til.setError("Interest Rate must be between 0 to 100");
                                rec_depo_int_rate_til.requestFocus()
                                return@setOnClickListener

                            }

                            //val mCompounding = iCompounding[rec_depo_spinner.getItemAtPosition(position)]
                            val mCompounding = iCompounding_rd.get(position)

                            mMaturityValue = RDMaturityValueV1(mMonthlyInvestment.toFloat(),
                                mTenure, mRate, mCompounding)

                            mTotalInvestment = mMonthlyInvestment * mTenure
                            mTotalInterest = mMaturityValue - mTotalInvestment


                            rec_depo_result.visibility=View.VISIBLE

                            rec_depo_mat_value.setText(roundUpFunction(mMaturityValue,2).toString())
                            rec_depo_tot_int.setText(roundUpFunction(mTotalInterest,2).toString())
                            rec_depo_inv_amt.setText(roundUpFunction(mTotalInvestment,2).toString())

                            btn_share_rd.setOnClickListener(){

                                val intent = Intent()
                                intent.action = Intent.ACTION_SEND
                                intent.putExtra(
                                    Intent.EXTRA_TEXT, /*tenure spinner to be fetched yet*/
                                    "Recurring Deposit" + "\n\nmonthly investment:\t" +rec_depo_mon_inv.text.toString() +  "\n\ntenure:\t" +rec_depo_spinner.getItemAtPosition(position)+" months"
                                            + "\n\ninterest rate:\t" +rec_depo_int_rate.text.toString() +  "\n\npayment mode:\t" +recurringdeposit_spinner.getItemAtPosition(0)
                                            + "\n\ntotal interest:\t" + rec_depo_tot_int.text.toString() + "\n\ntotal interest:\t" + rec_depo_tot_int.text.toString()
                                            + "\n\ntotal maturity:\t" + rec_depo_mat_value.text.toString()
                                )
                                intent.type = "text/plain"

                                startActivity(Intent.createChooser(intent, "Share Via"))
                            }

                        }

                        else{

                            btn_share_rd.isClickable=false
                        }
                    }
                }

            }
        }




        //mutual fund fragment

        //sip calc calculations

        btn_sipcalc_calc.setOnClickListener(){



            if(checkEmptyValidation(sipcalc_mon_inv,sipcalc_mon_inv_til) && checkEmptyValidation(sipcalc_tenure,til_sipCalcTenure)
                && checkEmptyValidation(sipcalc_rate_of_ret,sipcalc_rate_of_ret_til) ) {

                //assigning values of edit text
                monthly_investment = sipcalc_mon_inv.text.toString().toDouble()
                tenure = sipcalc_tenure.text.toString().toFloat()
                rate = sipcalc_rate_of_ret.text.toString().toFloat()
                rate_yearly = 0f
                init_investment = 0f


                if(swt_sipCalc.isChecked){
                    tenure *= 12
                }

                if (tenure > 1200) {
                    til_sipCalcTenure.setError("Tenure value more than 1200 month not allowed")
                    til_sipCalcTenure.requestFocus()
                    return@setOnClickListener

                }
                if (rate > 100) {
                    sipcalc_rate_of_ret_til.setError("Rate of Return must be between 0 to 100")
                    sipcalc_rate_of_ret_til.requestFocus()
                    return@setOnClickListener

                }

                if (sipcalc_yr_incr.length()!=0) {
                    rate_yearly = sipcalc_yr_incr.text.toString().toFloat()
                }

                if (sipcalc_lump.length() != 0) {
                    init_investment = sipcalc_lump.text.toString().toFloat()
                }

                var ans_maturity_value:Double = init_investment.toDouble()
                var ans_investment:Double = init_investment.toDouble()
                val yr_int = (monthly_investment*rate_yearly) / 100
                var local_monthly = monthly_investment

                for (i in 1..tenure.toInt()) {
                    val interest: Double =
                        (((ans_maturity_value + local_monthly) * rate) / 100) / 12

                    ans_maturity_value += local_monthly.toFloat() + interest.toFloat()
                    ans_investment += local_monthly.toFloat()

                    if (i % 12 == 0) {

                        local_monthly += yr_int


                    }
                }

                TotalInvestment = ans_investment
                TotalMaturity = ans_maturity_value



                sipcalc_result.visibility = View.VISIBLE

                sipcalc_inv_amt.setText(roundUpFunction(ans_investment,2).toString())
                sipcalc_mat_value.setText(roundUpFunction(ans_maturity_value,2).toString())



                btn_share_sipcalc.setOnClickListener(){

                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Sip Calculator" + "\n\nmonthly investment:\t" +sipcalc_mon_inv.text.toString() +  "\n\ntenure:\t" +sipcalc_tenure.text.toString()
                                + "\n\nrate of return:\t" +sipcalc_rate_of_ret.text.toString() +  "\n\nyearly inc in monthly inv:\t" +sipcalc_yr_incr.text.toString()
                                + "\n\nlump sum:\t" + sipcalc_lump.text.toString() + "\n\nInvestment Amount:\t" + sipcalc_inv_amt.text.toString()
                                + "\n\nmaturity Value:\t" + sipcalc_mat_value.text.toString()
                    )
                    intent.type = "text/plain"

                    startActivity(Intent.createChooser(intent, "Share Via"))
                }

                btn_viewdocs_sipcalc.setOnClickListener(){
                    /*passing tenure value to viewsipplan activity*/

                    /*val inv_mont = sipcalc_mon_inv.text.toString().toDouble()
                    //val tenure = sipcalc_tenure.text.toString().toFloat()
                    val rate_o_ret = sipcalc_rate_of_ret.text.toString().toFloat()
                    val inc_yr = sipcalc_yr_incr.text.toString()//.toFloat()
                    val lump = sipcalc_lump.text.toString()//.toFloat()*/
                    //val temp =12*tenure

                    val intent = Intent(activity!!, ViewSIPPlan::class.java)

                    intent.putExtra("sip_calc",comeFrom)
                    intent.putExtra("monthly_investment",monthly_investment)
                    intent.putExtra("tenure", tenure)
                    intent.putExtra("rate",rate)
                    intent.putExtra("rate_yearly",rate_yearly)
                    intent.putExtra("init_investment",init_investment)

                    startActivity(intent)
                    //Toast.makeText(activity,"Under development",Toast.LENGTH_SHORT).show()


                }
            }
            else{
                btn_share_sipcalc.isClickable=false
                btn_viewdocs_sipcalc.isClickable=false
            }



        }



        //swp calc calculations
        btn_swpcalc_calc.setOnClickListener(){

            if(checkEmptyValidation(swpcalc_amt,swpcalc_amt_til) && checkEmptyValidation(swpcalc_init_inv_amt,swpcalc_init_inv_amt_til)
                && checkEmptyValidation(swpCalcDuration,til_swpCalcDuration) && checkEmptyValidation(swpcalc_ror,swpcalc_ror_til)){


                mSWPAmount = swpcalc_amt.text.toString().toInt()
                mInvestmentAmount_swp = swpcalc_init_inv_amt.text.toString().toLong()
                mDuration = swpCalcDuration.text.toString().toInt()
                mRate_swp = swpcalc_ror.text.toString().toFloat()

                if (swt_swpCalc.isChecked) {
                    mDuration *= 12
                }

                if(mDuration > 1200){
                    til_swpCalcDuration.setError("Duration value more than 1200 months not allowed")
                    til_swpCalcDuration.requestFocus()
                    return@setOnClickListener


                }

                if(mRate_swp > 100){
                    swpcalc_ror_til.setError("Rate of Return must be between 0 to 100")
                    swpcalc_ror_til.requestFocus()
                    return@setOnClickListener


                }

                mTotalWithdrawal = 0
                mTotalBalance = 0.0
                mTotalProfit = 0f
                mNoWithdrawal = 0
                var value1:Double = 0.0
                mTotalBalance = mInvestmentAmount_swp.toDouble()



                for (i in 1..mDuration) {

                    if (mSWPAmount > mInvestmentAmount_swp) {
                        break
                    }
                    mTotalWithdrawal += mSWPAmount.toLong()
                    value1 = mTotalBalance - mSWPAmount.toDouble()
                    val interest = value1.toFloat() * (mRate_swp / 100 / 12)
                    mTotalProfit += interest
                    mTotalBalance = value1 + interest
                    mNoWithdrawal++

                    if (mTotalBalance < mSWPAmount) {
                        break
                    }
                }

                swpcalc_result.visibility = View.VISIBLE

                swpcalc_total_with.setText(roundUpFunction(mTotalWithdrawal.toDouble(),2).toString())
                swpcalc_bal.setText(roundUpFunction(mTotalBalance,2).toString())
                swpcalc_tot_profit.setText(roundUpFunction(mTotalProfit.toDouble(),2).toString())
                swpcalc_no_with.setText(roundUpFunction(mNoWithdrawal.toDouble(),2).toString())

                btn_share_swpCalc.setOnClickListener(){

                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Swp Calculator" + "\n\nSWP amt:\t" +swpcalc_amt.text.toString() +  "\n\nInit inv amt:\t" +swpcalc_init_inv_amt.text.toString()
                                + "\n\nDuration:\t" +swpCalcDuration.text.toString() + "\n\nRate of return:\t" +swpcalc_ror.text.toString()
                                + "\n\nTotal withdrawals:\t" +swpcalc_total_with.text.toString() + "\n\nBalance at the end:\t" +swpcalc_bal.text.toString()
                                + "\n\nTotal Profit:\t" +swpcalc_tot_profit.text.toString() + "\n\nNo. of withdrawals:\t" +swpcalc_no_with.text.toString()
                    )
                    intent.type = "text/plain"

                    startActivity(Intent.createChooser(intent, "Share Via"))
                }

                btn_viewdocs_swpCalc.setOnClickListener(){
                    //Toast.makeText(activity,"Under development",Toast.LENGTH_SHORT).show()
                    /*passing tenure value to viewsipplan activity*/

                    /*val amt = swpcalc_amt.text.toString().toInt()
                    val inv_amt = swpcalc_init_inv_amt.text.toString().toLong()
                    //val duration = swpCalcDuration.text.toString().toInt()
                    val rate_o_ret = swpcalc_ror.text.toString().toFloat()
                    val tot_with = swpcalc_total_with.text.toString().toInt()
                    val balance = swpcalc_bal.text.toString().toDouble()
                    val tot_profit = swpcalc_tot_profit.text.toString().toFloat()
                    val no_withdrawal = swpcalc_no_with.text.toString().toInt()*/



                    val intent = Intent(activity!!, SWPDetails::class.java)
                    intent.putExtra("swp_calc", comeFrom)
                    intent.putExtra("swp_amount",mSWPAmount)
                    intent.putExtra("init_investment", mInvestmentAmount_swp)
                    intent.putExtra("duration", mDuration)
                    intent.putExtra("rate", mRate_swp)
                    intent.putExtra("total_withdrawals", mTotalWithdrawal)
                    intent.putExtra("total_balace", mTotalBalance)
                    intent.putExtra("total_profit", mTotalWithdrawal)
                    intent.putExtra("total_no_of_withdrawals", mNoWithdrawal)


                    startActivity(intent)



                }
            }
            else{
                btn_share_swpCalc.isClickable=false
                btn_viewdocs_swpCalc.isClickable=false
            }



        }



        //stp calc calculations
        btn_stpcalc_calc.setOnClickListener(){

            if(checkEmptyValidation(stpcalc_stp_amt,stpcalc_stp_amt_til) && checkEmptyValidation(stpcalc_init_inv_amt,stpcalc_init_inv_amt_til)
                && checkEmptyValidation(stpcalc_no_inst,stpcalc_no_inst_til) && checkEmptyValidation(stpcalc_ror_transferor,stpcalc_ror_transferor_til)
                && checkEmptyValidation(stpcalc_ror_transferre,stpcalc_ror_transferre_til)){

                mSTPAmount = stpcalc_stp_amt.text.toString().toInt()
                mInvestmentAmount_stp = stpcalc_init_inv_amt.text.toString().toLong()
                mInstallments = stpcalc_no_inst.text.toString().toInt()
                mRateTransferor = stpcalc_ror_transferor.text.toString().toFloat()
                mRateTransferee = stpcalc_ror_transferre.text.toString().toFloat()

                if (mInstallments > 1200) {
                    stpcalc_no_inst_til.setError("Installments value more than 1200 months not allowed")
                    stpcalc_no_inst_til.requestFocus()
                    return@setOnClickListener

                } else if (mRateTransferor > 100) {
                    stpcalc_ror_transferor_til.setError("Rate of Return must be between 0 to 100")
                    stpcalc_ror_transferor_til.requestFocus()
                    return@setOnClickListener


                } else if (mRateTransferee > 100) {
                    stpcalc_ror_transferre_til.setError("Rate of Return must be between 0 to 100")
                    stpcalc_ror_transferre_til.requestFocus()
                    return@setOnClickListener


                }

                mTotalTransfered = 0
                mTotalBalTransferor = mInvestmentAmount_stp.toDouble()
                mTotalBalTransferee = 0.0
                mTotalProfit_stp = 0f
                var totalProfitTransferor:Double = 0.0
                var totalProfitTransferee:Double = 0.0


                for (i in 1..mInstallments) {

                    var subTotal: Double = mTotalBalTransferor - mSTPAmount.toDouble()
                    var subTotal1: Double = mTotalBalTransferee + mSTPAmount.toDouble()

                    if (subTotal < 0) {
                        subTotal = mTotalBalTransferor;
                        subTotal1 = mTotalBalTransferee;
                    } else {
                        totalProfitTransferor += mSTPAmount;
                        totalProfitTransferee += mSTPAmount;
                    }

                    var interest = (subTotal.toFloat() * (mRateTransferor / 100 / 12))

                    mTotalProfit_stp += interest
                    mTotalBalTransferor = subTotal + interest


                    interest = subTotal1.toFloat() * (mRateTransferee / 100 / 12)

                    mTotalProfit_stp += interest
                    mTotalBalTransferee = subTotal1 + interest


                }

                mTotalTransfered = totalProfitTransferor.toLong()


                stpcalc_result.visibility = View.VISIBLE

                stpcalc_tot_amt_transf.setText(roundUpFunction(mTotalTransfered.toDouble(),2).toString())
                stpcalc_bal_transferor.setText(roundUpFunction(mTotalBalTransferor,2).toString())
                stpcalc_bal_transferre.setText(roundUpFunction(mTotalBalTransferee,2).toString())
                stpcalc_tot_profit.setText(roundUpFunction(mTotalProfit_stp.toDouble(),2).toString())


                btn_viewdocs_stpCalc.setOnClickListener(){

                    //Toast.makeText(activity!!,"Signout is success",Toast.LENGTH_SHORT).show()

                    val intent = Intent(activity!!, STPDetails::class.java)
                    intent.putExtra("stp_calc",comeFrom)
                    intent.putExtra("stp_amount", mSTPAmount)
                    intent.putExtra("init_investment", mInvestmentAmount_stp)
                    intent.putExtra("installments", mInstallments)
                    intent.putExtra("rate_transferor", mRateTransferor)
                    intent.putExtra("rate_transferee", mRateTransferee)
                    startActivity(intent)
                }

                btn_share_stpCalc.setOnClickListener(){

                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Stp Calculator" + "\n\nSTP amt:\t" +stpcalc_stp_amt.text.toString() +  "\n\nInit inv amt:\t" +stpcalc_init_inv_amt.text.toString()
                                + "\n\nNo. of installments:\t" +stpcalc_no_inst.text.toString() + "\n\nRate of return- Transferor:\t" +stpcalc_ror_transferor.text.toString()
                                + "\n\nRate of return- Transferre:\t" +stpcalc_ror_transferre.text.toString() + "\n\nTotal Amount transferred:\t" +stpcalc_tot_amt_transf.text.toString()
                                + "\n\nBalance in transferor:\t" +stpcalc_bal_transferor.text.toString() + "\n\nBalance in transferre:\t" +stpcalc_bal_transferre.text.toString()
                                + "\n\nTotal Profit:\t" +stpcalc_tot_profit.text.toString()
                    )
                    intent.type = "text/plain"

                    startActivity(Intent.createChooser(intent, "Share Via"))
                }


            }
            else{
                btn_share_stpCalc.isClickable=false
                btn_viewdocs_stpCalc.isClickable=false
            }



        }



        // switches

        if (swt_sipCalc.isChecked) {

            til_sipCalcTenure.hint = "Tenure( in Years)"
        }

        else {
            til_sipCalcTenure.hint = "Tenure( in Months)"
        }

        swt_sipCalc.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                til_sipCalcTenure.hint = "Tenure( in Years)"

            }
            else {
                til_sipCalcTenure.hint = resources.getString(R.string.st_sipc_ten).plus("( in Months)")
            }
        }

        if (swt_sipPlanner.isChecked) {
            til_sipPlannerInvPeriod.hint = "Investment Period( in Years)"
        } else {
            til_sipPlannerInvPeriod.hint = "Investment Period( in Months)"
        }

        swt_sipPlanner.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                til_sipPlannerInvPeriod.hint = "Investment Period( in Years)"


            } else {
                til_sipPlannerInvPeriod.hint = resources.getString(R.string.st_sipp_ip).plus("( in Months)")
            }
        }

        if (swt_swpCalc.isChecked) {
            til_swpCalcDuration.hint = "Duration( in Years)"
        } else {
            til_swpCalcDuration.hint = "Duration( in Months)"
        }

        swt_swpCalc.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                til_swpCalcDuration.hint = "Duration( in Years)"

            } else {
                til_swpCalcDuration.hint = resources.getString(R.string.st_swpc_dur).plus("( in Months)")
            }
        }

        if (swt_fd.isChecked) {
            til_fdTenure.hint = "Tenure( in Years)"
        } else {
            til_fdTenure.hint = "Tenure( in Months)"
        }

        swt_fd.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                til_fdTenure.hint = "Tenure( in Years)"
            } else {
                til_fdTenure.hint = resources.getString(R.string.fd_ten).plus("( in Months)")
            }
        }


    }
}
