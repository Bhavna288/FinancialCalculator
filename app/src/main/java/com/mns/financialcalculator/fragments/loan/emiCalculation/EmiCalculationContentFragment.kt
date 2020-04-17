package com.mns.financialcalculator.fragments.loan.emiCalculation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.fragment_emi_calculation_content.*


class EmiCalculationContentFragment : Fragment() {

    var comeFrom: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emi_calculation_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            comeFrom = arguments?.getString("come_from")
            when (comeFrom) {
                "emi" -> {
                    ll_loanCalc_emi.visibility = View.VISIBLE
                    ll_loanCalc_tenure.visibility = View.GONE
                    ll_loanCalc_amt.visibility = View.GONE
                    ll_loanCalc_rate.visibility = View.GONE


                }
                "tenure" -> {
                    ll_loanCalc_emi.visibility = View.GONE
                    ll_loanCalc_tenure.visibility = View.VISIBLE
                    ll_loanCalc_amt.visibility = View.GONE
                    ll_loanCalc_rate.visibility = View.GONE
                }

                "amount" -> {
                    ll_loanCalc_emi.visibility = View.GONE
                    ll_loanCalc_tenure.visibility = View.GONE
                    ll_loanCalc_amt.visibility = View.VISIBLE
                    ll_loanCalc_rate.visibility = View.GONE
                }

                "rate" -> {
                    ll_loanCalc_emi.visibility = View.GONE
                    ll_loanCalc_tenure.visibility = View.GONE
                    ll_loanCalc_amt.visibility = View.GONE
                    ll_loanCalc_rate.visibility = View.VISIBLE
                }


            }
        }




    }


}
