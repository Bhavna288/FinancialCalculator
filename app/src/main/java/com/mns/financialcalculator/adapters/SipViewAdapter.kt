package com.mns.financialcalculator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mns.financialcalculator.R
import com.mns.financialcalculator.StickyHeaderListView.StickyListHeadersAdapter
import com.mns.financialcalculator.model.ViewSIPPlanitems
import kotlinx.android.synthetic.main.cardlayout_sip_view.view.*
import kotlinx.android.synthetic.main.sip_header.view.*
import java.util.*


class SipViewAdapter(private val mContext: Context, private val viewsipplanitems: ArrayList<ViewSIPPlanitems>) :
    ArrayAdapter<ViewSIPPlanitems>(mContext,0,0,viewsipplanitems), StickyListHeadersAdapter {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        var view:View?=null
        if (view == null) {

             view = LayoutInflater.from(parent.context).inflate(R.layout.cardlayout_sip_view,parent,false)



        }
        else {
            Toast.makeText(context,"empty",Toast.LENGTH_SHORT).show()

        }



        val value: ViewSIPPlanitems = viewsipplanitems.get(position)

        view?.sip_view_month?.setText((value.month.toString()))
        view?.sip_view_int?.setText((value.interest.toString()))
        view?.sip_view_bal?.setText((value.balance.toString()))

        return view!!

    }


    override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {


        var view:View?=null

        if (view == null) {

            view = LayoutInflater.from(parent!!.context).inflate(R.layout.sip_header,parent,false)

        }
        else {
            Toast.makeText(context,"empty",Toast.LENGTH_SHORT).show()

        }

        val value: ViewSIPPlanitems = viewsipplanitems.get(position)

        view?.sip_header_tv_inv?.setText("Investment:".plus(value.investment.toString()))
        view?.sip_header_tv_yr?.setText("Year:".plus(value.year.toString()))


        return view!!
    }

    override fun getItemId(position: Int):Long{
        return position.toLong()
    }

    override fun getHeaderId(position: Int): Long {
        return viewsipplanitems.get(position).year//get(position)
    }


}


