package com.mns.financialcalculator.fragments.investment.fixDeposit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.fragment_fd_content.*


class FdContentFragment : Fragment() {

    var comeFrom: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fd_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recurringdeposit_spinner.setEnabled(false)
        recurringdeposit_spinner.setClickable(false)

        if (arguments != null) {
            comeFrom = arguments?.getString("come_from")
            when (comeFrom) {
                "fixed_deposit" -> {
                    ll_fd_fd.visibility = View.VISIBLE
                    ll_fd_rd.visibility = View.GONE


                }
                "rec_deposit" -> {
                    ll_fd_fd.visibility = View.GONE
                    ll_fd_rd.visibility = View.VISIBLE


                }


            }
        }



    }


}
