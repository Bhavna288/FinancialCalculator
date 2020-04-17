package com.mns.financialcalculator.fragments.tax.gst


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mns.financialcalculator.R

/**
 * A simple [Fragment] subclass.
 */
class GstContentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gst_content, container, false)
    }


}
