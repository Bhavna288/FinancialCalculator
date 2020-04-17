package com.mns.financialcalculator.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mns.financialcalculator.R
import com.mns.financialcalculator.StickyHeaderListView.StickyListHeadersAdapter
import com.mns.financialcalculator.model.STPView
import kotlinx.android.synthetic.main.cardlayout_stp_details.view.*
import kotlinx.android.synthetic.main.stp_header.view.*



class StpViewAdapter (private val mContext: Context, private val stpviewdetails: ArrayList<STPView>) :
    ArrayAdapter<STPView>(mContext,0,0,stpviewdetails), StickyListHeadersAdapter {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        var view: View?=null

        if (view == null) {

            view = LayoutInflater.from(parent.context).inflate(R.layout.cardlayout_stp_details,parent,false)

        }
        else {
            Toast.makeText(context,"empty", Toast.LENGTH_SHORT).show()

        }


        val value: STPView = stpviewdetails.get(position)

        view?.stp_view_month?.setText((value.month.toString()))
        view?.stp_view_bal_begin?.setText((value.bal_begin.toString()))
        view?.stp_view_trans_out?.setText((value.STPAmt.toString()))
        view?.stp_view_int?.setText((value.interest.toString()))
        view?.stp_view_bal?.setText((value.bal.toString()))

        if (position % 2 != 0) {
            view?.ll_stpviewdetails?.setBackgroundColor(getContext().getResources().getColor(R.color.lt_gray_bg))
        }
        else{
            view?.ll_stpviewdetails?.setBackgroundColor(Color.WHITE)
        }

        view?.stp_view_month?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
        view?.stp_view_bal_begin?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
        view?.stp_view_trans_out?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
        view?.stp_view_int?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
        view?.stp_view_bal?.setTextColor(getContext().getResources().getColor(R.color.gray_text))

        return view!!

    }


    override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View?=null

        if (view == null) {

            view = LayoutInflater.from(parent!!.context).inflate(R.layout.stp_header,parent,false)

        }
        else {
            Toast.makeText(context,"empty", Toast.LENGTH_SHORT).show()

        }

        val value: STPView = stpviewdetails.get(position)

        view?.stp_header_tv_yr?.setText("Year:".plus(value.year.toString()))


        return view!!

    }

    override fun getItemId(position: Int):Long{
        return position.toLong()
    }


    override fun getHeaderId(position: Int): Long {
        return getItem(position)!!.year
    }


}
