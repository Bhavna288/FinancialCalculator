package com.mns.financialcalculator.fragments.investment

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mns.financialcalculator.R
import com.mns.financialcalculator.StickyHeaderListView.StickyListHeadersListView
import com.mns.financialcalculator.adapters.StpViewAdapter
import com.mns.financialcalculator.model.STPView
import com.mns.financialcalculator.utils.Utils.Companion.ExportToCSV
import com.mns.financialcalculator.utils.Utils.Companion.roundUpFunction
import com.mns.financialcalculator.utils.Utils.Companion.setupPermissions
import kotlinx.android.synthetic.main.activity_stpdetails.*
import kotlinx.android.synthetic.main.cardlayout_stp_details.view.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*
import kotlinx.android.synthetic.main.fragment_stpdetails.*

class STPDetails : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stpdetails)

        cnx = this

        val actionBar = supportActionBar?.apply{hide()}

        // val textchanger = getResources().getString(R.string.vd_toolbar_name)

        vd_toolbar_name_change.text = "STP Detail"

        back_button.setOnClickListener(){

            finish()
        }

        btn_share_plan_csv.setOnClickListener(){

            setupPermissions(this)
            MakeCSVData()
        }

        if (getIntent().hasExtra("stp_calc")) {

            mSTPAmount = getIntent().getIntExtra("stp_amount", 0);
            mInvestmentAmount_stp = getIntent().getLongExtra("init_investment", 0);
            mInstallments = getIntent().getIntExtra("installments", 0);
            mRateTransferor = getIntent().getFloatExtra("rate_transferor", 0f);
            mRateTransferee = getIntent().getFloatExtra("rate_transferee", 0f);

        } else {

            finish()
        }



        val adapter = MyViewPagerAdapter(supportFragmentManager)


        adapter.fragmentadd(FragFeror(),"Transferor Scheme")
        adapter.fragmentadd(FragFerre(),"Transferre Scheme")

        // for adapter.fragmentadd -> com.example.navigation.siptools.STPDetailsContentFragment()

        stpdetails_viewpager.adapter = adapter
        stpPlans_tabs.setupWithViewPager(stpdetails_viewpager)
    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            /*  val stp= com.example.navigation.siptools.STPDetailsContentFragment()
              val bundle=Bundle()*/

            //val stp =

            return when(position){
                0->{

                    /*bundle.putString("come_from","transferor_scheme")
                    stp.arguments=bundle*/


                    FragFeror()

                }
                1->{
                    /*bundle.putString("come_from","transferre_scheme")
                    stp.arguments=bundle*/

                    FragFerre()
                }

                else->{

                    FragFeror()

                }

            }

            //return stp



        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

        fun fragmentadd(fragment: Fragment, title: String) {

            fragmentList.add(fragment)
            titleList.add(title)
        }

    }

    //write your code here..!!

    companion object{

        var cnx: Context? = null

        var mSTPAmount:Int = 0
        var mInvestmentAmount_stp: Long = 0L
        var mInstallments:Int= 1
        var mRateTransferor:Float = 0f
        var mRateTransferee:Float = 0f

        var mTotalTransfered: Long = 0L
        var mTotalBalTransferor:Double = 0.0
        var mTotalBalTransferee:Double = 0.0
        var mTotalProfit_stp:Float = 0f

        class FragFeror: Fragment(), StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
            StickyListHeadersListView.OnStickyHeaderChangedListener {

            var array: ArrayList<STPView>?=null
            var fadeHeader:Boolean = true


            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
            ): View? {


                return inflater.inflate(R.layout.fragment_stpdetails,container,false)
                // var view:View = inflater.inflate(R.layout.fragment_stpdetails,container,false)
                //return super.onCreateView(inflater, container, savedInstanceState)
            }

            override fun onActivityCreated(savedInstanceState: Bundle?) {

                super.onActivityCreated(savedInstanceState)

                //code

                array = ArrayList() //always instantiate the array

                var year_counter = 1
                var month_counter = 1

                mTotalTransfered = mSTPAmount * mInstallments.toLong()
                mTotalBalTransferor = mInvestmentAmount_stp.toDouble()
                mTotalBalTransferee = 0.0
                mTotalProfit_stp = 0f


                // For Transferor
                for (i in 1..mInstallments) {

                    var subTotal: Double = mTotalBalTransferor - mSTPAmount

                    val interest: Float = (subTotal.toFloat() * (mRateTransferor / 100 / 12))
                    mTotalProfit_stp += interest




                    if (subTotal < 0) {
                        subTotal = mTotalBalTransferor
                    }


                    //mTotalBalTransferor = subTotal+interest


                    val bean = STPView(
                        year_counter.toLong(),
                        month_counter++.toLong(),
                        roundUpFunction(mTotalBalTransferor,2)!!,
                        mSTPAmount.toDouble(),
                        roundUpFunction(interest.toDouble(),2)!!,
                        roundUpFunction(subTotal+interest,2)!!
                    )




                    array!!.add(bean)
                    if (i % 12 == 0) {
                        year_counter++
                        month_counter = 1
                    }

                    mTotalBalTransferor = subTotal+interest
                }

                addHeaderView()

                val mAdapter = StpViewAdapter( cnx!!,array!!)//(ArrayList<ViewSIPPlanitems>())
                /*stpdetails_feror_rec_view.adapter = mAdapter
                stpdetails_feror_rec_view.divider = null

                stpdetails_feror_rec_view.setDrawingListUnderStickyHeader(true)
                stpdetails_feror_rec_view.setAreHeadersSticky(true)*/

                stp_stickyheader.adapter = mAdapter
                stp_stickyheader.divider = null

                stp_stickyheader.setDrawingListUnderStickyHeader(true)
                stp_stickyheader.setAreHeadersSticky(true)


                //tot_int_card_stp.setText("Total Interest:".plus(mTotalProfit_stp.toString()))
                stpcalc_tot_int_result.setText("Total Interest:".plus(roundUpFunction(mTotalProfit_stp.toDouble(),2)))


            }


            override fun onStickyHeaderOffsetChanged(l: StickyListHeadersListView?, header: View?, offset: Int) {

                if (fadeHeader && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    header!!.alpha = 1 - offset / header.measuredHeight.toFloat()
                }

            }

            override fun onStickyHeaderChanged(l: StickyListHeadersListView?, header: View?,
                                               itemPosition: Int, headerId: Long) {

                header!!.setAlpha(1F)

            }


            fun addHeaderView() {
                val view: View = activity!!.layoutInflater.inflate(R.layout.cardlayout_stp_details, null)
                //stpdetails_feror_rec_view.addHeaderView(view)
                stp_stickyheader.addHeaderView(view)
            }

        }



        class FragFerre: Fragment(), StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
            StickyListHeadersListView.OnStickyHeaderChangedListener{

            var array: ArrayList<STPView>?=null
            private var fadeHeader:Boolean = true

            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
            ): View? {


                return inflater.inflate(R.layout.fragment_stpdetails,container,false)

                /*var view:View = inflater.inflate(R.layout.fragment_stpdetails,container,false)
                return super.onCreateView(inflater, container, savedInstanceState)*/
            }

            override fun onActivityCreated(savedInstanceState: Bundle?) {
                super.onActivityCreated(savedInstanceState)

                array = ArrayList() //always instantiate the array

                var year_counter:Int = 1
                var month_counter:Int = 1

                mTotalTransfered = mSTPAmount * mInstallments.toLong()
                mTotalBalTransferor = mInvestmentAmount_stp.toDouble()
                mTotalBalTransferee = 0.0
                mTotalProfit_stp = 0f

                var totalProfitTransferor:Double = 0.0
                var totalProfitTransferee:Double = 0.0



                // For Transferrer
                for (i in 1..mInstallments)
                {

                    var subTotal = mTotalBalTransferor - mSTPAmount.toDouble()
                    var subTotal1 = mTotalBalTransferee + mSTPAmount.toDouble()
                    if (subTotal < 0)
                    {
                        subTotal = mTotalBalTransferor
                        subTotal1 = mTotalBalTransferee
                    }
                    else
                    {
                        totalProfitTransferor += mSTPAmount
                        totalProfitTransferee += mSTPAmount
                    }



                    // For Transferor
                    var interest = (subTotal.toFloat() * (mRateTransferor / 100 / 12))
                    mTotalBalTransferor = subTotal + interest


                    // For Transferee
                    interest = subTotal1.toFloat() * (mRateTransferee / 100 / 12)

                    mTotalProfit_stp += interest


                    //mTotalBalTransferee = subTotal1 + interest



                    val bean = STPView(
                        year_counter.toLong(),
                        month_counter++.toLong(),
                        roundUpFunction(mTotalBalTransferee,2)!!,
                        mSTPAmount.toDouble(),
                        roundUpFunction(interest.toDouble(),2)!!,
                        roundUpFunction(subTotal1+interest,2)!!
                    )

                    array!!.add(bean)

                    if (i % 12 == 0) {
                        year_counter++
                        month_counter = 1
                    }
                    mTotalBalTransferee = subTotal1 + interest
                }

                addHeaderView()

                val mAdapter = StpViewAdapter(cnx!!,array!!)//(ArrayList<ViewSIPPlanitems>())
                /*stpdetails_ferre_rec_view.adapter = mAdapter
                stpdetails_ferre_rec_view.divider = null
                stpdetails_ferre_rec_view.setDrawingListUnderStickyHeader(true)
                stpdetails_ferre_rec_view.setAreHeadersSticky(true)*/


                stp_stickyheader.adapter = mAdapter
                stp_stickyheader.divider = null
                stp_stickyheader.setDrawingListUnderStickyHeader(true)
                stp_stickyheader.setAreHeadersSticky(true)


                //tot_int_card_stp2.setText("Total Interest:".plus(mTotalProfit_stp.toString()))
                stpcalc_tot_int_result.setText("Total Interest:".plus(roundUpFunction(mTotalProfit_stp.toDouble(),2)))


            }

            fun addHeaderView() {
                val view: View = activity!!.layoutInflater.inflate(R.layout.cardlayout_stp_details, null)
                view.stp_view_trans_out.setText("Transf. In")
                //stpdetails_ferre_rec_view.addHeaderView(view)
                stp_stickyheader.addHeaderView(view)
            }

            override fun onStickyHeaderOffsetChanged(l: StickyListHeadersListView?, header: View?, offset: Int) {

                if (fadeHeader && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                ) {
                    header!!.alpha = 1 - offset / header
                        .measuredHeight.toFloat()
                }

            }

            override fun onStickyHeaderChanged(l: StickyListHeadersListView?, header: View?, itemPosition: Int, headerId: Long) {

                header!!.setAlpha(1f)

            }

        }

    }

    fun MakeCSVData() {
        val items = ArrayList<Array<String>>()
        // init
        val title = arrayOf("", "", "STP Details")
        items.add(title)
        val black_line = arrayOf("")
        items.add(black_line)
        val field1 = arrayOf(
            "STP Amount", mSTPAmount.toString(), "",
            "Init Investment", mInvestmentAmount_stp.toString())

        items.add(field1)
        val field2 =
            arrayOf("No of Installments", "$mInstallments months")
        items.add(field2)
        val field3 = arrayOf(
            "Rate of Return - Tranferor",
            (mRateTransferor).toString() + " %",
            "",
            "Rate of Return - Tranferee",
            (mRateTransferee).toString() + " %"
        )
        items.add(field3)
        items.add(black_line)
        val field4 = arrayOf(
            "", "", "", "Transferor Scheme", "", "", "", "",
            "", "", "", "Transferee Scheme", "", ""
        )
        items.add(field4)
        val column_title = arrayOf(
            "Year", "Month", "Balance Begin",
            "Transferred-Out", "Interest", "Balance End", "", "", "Year",
            "Month", "Balance Begin", "Transferree-In", "Interest",
            "Balance End"
        )
        items.add(column_title)
        // ==//
        var year_counter = 1
        var month_counter = 1
        mTotalTransfered = 0
        mTotalBalTransferor = mInvestmentAmount_stp.toDouble()
        mTotalBalTransferee = 0.0
        mTotalProfit_stp = 0f
        var totalProfitTransferor = 0.0
        var totalProfitTransferee = 0.0
        var totalInterestTransferor = 0.0
        var totalInterestTransferee = 0.0
        // For Transferrer
        for (i in 1..mInstallments) {
            val bal_pref_transferor = mTotalBalTransferor
            val bal_pref_transferee = mTotalBalTransferee
            var subTotal = mTotalBalTransferor - mSTPAmount.toDouble() // 696
            var subTotal1 = mTotalBalTransferee + mSTPAmount.toDouble()
            var mTransOut: Double = mSTPAmount.toDouble()
            if (subTotal < 0) {
                subTotal = mTotalBalTransferor
                subTotal1 = mTotalBalTransferee
                mTransOut = 0.0
            } else {
                totalProfitTransferor += mSTPAmount
                totalProfitTransferee += mSTPAmount
            }
            // For Transferoe
            val interest_tranfer = (subTotal.toFloat()
                    * (mRateTransferor / 100 / 12)) // 79
            totalInterestTransferor += interest_tranfer.toDouble()
            mTotalBalTransferor = subTotal + interest_tranfer // 9579
            // ==//
// For Transferee
            val interest_tranfee = (subTotal1.toFloat()
                    * (mRateTransferee / 100 / 12)) // 79
            mTotalProfit_stp += interest_tranfee
            totalInterestTransferee += interest_tranfee.toDouble()
            mTotalBalTransferee = subTotal1 + interest_tranfee // 9579
            // ==//
            val column_data = arrayOf(
                year_counter.toString() + "",
                month_counter.toString() + "",
                (bal_pref_transferor.toString()),
                (mTransOut.toString()),
                (interest_tranfer.toString()),
                (mTotalBalTransferor.toString()),
                "",
                "",
                year_counter.toString() + "",
                month_counter.toString() + "",
                (bal_pref_transferee.toString()),
                (mTransOut.toString()),
                (interest_tranfee.toString()),
                (mTotalBalTransferee.toString())
            )
            items.add(column_data)
            month_counter++
            if (i % 12 == 0) {
                year_counter++
                month_counter = 1
            }
        }
        val footer_data = arrayOf(
            "", "Total", "",
            (totalProfitTransferor.toString()),
            (totalInterestTransferor.toString()), "", "", "",
            "", "Total", "",
            (totalProfitTransferee.toString()),
            (totalInterestTransferee.toString())
        )
        items.add(footer_data)
        ExportToCSV(this, "STP Details ", items)
    }




}