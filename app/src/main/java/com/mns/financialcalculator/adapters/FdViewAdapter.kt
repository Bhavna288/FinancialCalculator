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
import com.mns.financialcalculator.model.FdView
import kotlinx.android.synthetic.main.fd_row_file.view.*

class FdViewAdapter (private val mContext: Context, private val fddetails: ArrayList<FdView>):
    ArrayAdapter<FdView>(mContext,0,0,fddetails) , StickyListHeadersAdapter {

    var counter:Int = 1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View?=null

        if (view == null) {

            view = LayoutInflater.from(parent.context).inflate(R.layout.fd_row_file,parent,false)

        }
        else {
            Toast.makeText(context,"empty", Toast.LENGTH_SHORT).show()

        }

        val value: FdView = fddetails.get(position)

        view?.fdview_month?.setText(value.month.toString())
        view?.fdview_int?.setText(value.interest.toString())
        view?.fdview_fd_bal?.setText(value.balance.toString())

        if(position % 2 != 0){
            view?.ll_fddetailsview?.setBackgroundColor(getContext().getResources().getColor(R.color.lt_gray_bg))
        }

        else{

            view?.ll_fddetailsview?.setBackgroundColor(Color.WHITE)
        }

        view?.fdview_month?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
        view?.fdview_int?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
        view?.fdview_fd_bal?.setTextColor(getContext().getResources().getColor(R.color.gray_text))


        return view!!
    }

    override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View?=null

        if (view == null) {

            view = LayoutInflater.from(parent!!.context).inflate(R.layout.fd_row_file,parent,false)

        }
        else {
            Toast.makeText(context,"empty", Toast.LENGTH_SHORT).show()

        }

        view?.fdview_month?.setTextColor(Color.WHITE)
        view?.fdview_int?.setTextColor(Color.WHITE)
        view?.fdview_fd_bal?.setTextColor(Color.WHITE)


        return view!!
    }

    override fun getHeaderId(position: Int): Long {
        return getItem(position)!!.year.toLong()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}