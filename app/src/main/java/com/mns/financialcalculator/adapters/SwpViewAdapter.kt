package com.mns.financialcalculator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mns.financialcalculator.R
import com.mns.financialcalculator.StickyHeaderListView.StickyListHeadersAdapter
import com.mns.financialcalculator.model.SWPView
import kotlinx.android.synthetic.main.cardlayout_details_swp.view.*
import kotlinx.android.synthetic.main.swp_header.view.*


class SwpViewAdapter (private val mContext: Context, private val swpviewdetails: ArrayList<SWPView>) :
    ArrayAdapter<SWPView>(mContext,0,0,swpviewdetails), StickyListHeadersAdapter {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        var view:View?=null
        if (view == null) {

            view = LayoutInflater.from(parent.context).inflate(R.layout.cardlayout_details_swp,parent,false)



        }
        else {
            Toast.makeText(context,"empty", Toast.LENGTH_SHORT).show()

        }



        val value: SWPView = swpviewdetails.get(position)

        view?.swp_month_card?.setText((value.month.toString()))
        view?.swp_int_card?.setText((value.interest.toString()))
        view?.swp_bal_card?.setText((value.balance.toString()))

        return view!!

    }


    override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {


        var view:View?=null

        if (view == null) {

            view = LayoutInflater.from(parent!!.context).inflate(R.layout.swp_header,parent,false)

        }
        else {
            Toast.makeText(context,"empty", Toast.LENGTH_SHORT).show()

        }

        val value: SWPView = swpviewdetails.get(position)

        view?.swp_header_tv_swpamt?.setText("SWP Amount:".plus(value.swpamt.toString()))
        view?.swp_header_tv_yr?.setText("Year:".plus(value.year.toString()))


        return view!!
    }

    override fun getItemId(position: Int):Long{
        return position.toLong()
    }

    override fun getHeaderId(position: Int): Long {
        return swpviewdetails.get(position).year//get(position)
    }

}
