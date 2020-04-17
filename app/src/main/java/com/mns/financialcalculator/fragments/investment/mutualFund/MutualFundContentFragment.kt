package com.mns.financialcalculator.fragments.investment.mutualFund

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.fragment_mutual_fund_content.*


class MutualFundContentFragment : Fragment() {

    var comeFrom: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mutual_fund_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            comeFrom = arguments?.getString("come_from")
            when (comeFrom) {
                "sip_calc" -> {
                    ll_sipCalc.visibility = View.VISIBLE
                    ll_swpCalc.visibility = View.GONE
                    ll_stpCalc.visibility = View.GONE


                }
                "swp_calc" -> {
                    ll_sipCalc.visibility = View.GONE
                    ll_swpCalc.visibility = View.VISIBLE
                    ll_stpCalc.visibility = View.GONE


                }
                "stp_calc" -> {
                    ll_sipCalc.visibility = View.GONE
                    ll_swpCalc.visibility = View.GONE
                    ll_stpCalc.visibility = View.VISIBLE


                }


            }
        }
    }


}
