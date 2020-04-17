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
import com.mns.financialcalculator.model.LoanView
import kotlinx.android.synthetic.main.loan_row_file.view.*



class LoanViewAdapter(private val mContext: Context, private val loandetails: ArrayList<LoanView>):
    ArrayAdapter<LoanView>(mContext,0,0,loandetails) , StickyListHeadersAdapter {


    var counter = 1

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


            var view: View?=null

            if (view == null) {

                view = LayoutInflater.from(parent.context).inflate(R.layout.loan_row_file,parent,false)

            }
            else {
                Toast.makeText(context,"empty", Toast.LENGTH_SHORT).show()

            }



            val value: LoanView = loandetails.get(position)

            view?.loan_view_month?.setText((value.month.toString()))
            view?.loan_view_emi?.setText((value.emi.toString()))
            view?.loan_view_int?.setText((value.interest.toString()))
            view?.loan_view_prin?.setText((value.principal.toString()))
            view?.loan_view_bal?.setText((value.balance.toString()))

            if(!value.month.equals("Total")){

                if (position % 2 != 0) {
                    view?.ll_loandetailsview?.setBackgroundColor(getContext().getResources().getColor(R.color.lt_gray_bg))
                }
                else{
                    view?.ll_loandetailsview?.setBackgroundColor(Color.WHITE)
                }

                view?.loan_view_month?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
                view?.loan_view_emi?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
                view?.loan_view_int?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
                view?.loan_view_prin?.setTextColor(getContext().getResources().getColor(R.color.gray_text))
                view?.loan_view_bal?.setTextColor(getContext().getResources().getColor(R.color.gray_text))

            }

            else{

                view?.ll_loandetailsview?.setBackgroundColor(getContext().getResources().getColor(R.color.gray_bg_total))

                view?.loan_view_month?.setTextColor(Color.WHITE)
                view?.loan_view_emi?.setTextColor(Color.WHITE)
                view?.loan_view_int?.setTextColor(Color.WHITE)
                view?.loan_view_prin?.setTextColor(Color.WHITE)
                view?.loan_view_bal?.setTextColor(Color.WHITE)
                counter = 0

            }

            counter++
            return view!!

        }


        override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var view: View?=null

            if (view == null) {

                view = LayoutInflater.from(parent!!.context).inflate(R.layout.loan_row_file,parent,false)

            }
            else {
                Toast.makeText(context,"empty", Toast.LENGTH_SHORT).show()

            }

            val value: LoanView = loandetails.get(position)

            view?.loan_view_month?.setTextColor(Color.WHITE)
            view?.loan_view_emi?.setTextColor(Color.WHITE)
            view?.loan_view_int?.setTextColor(Color.WHITE)
            view?.loan_view_prin?.setTextColor(Color.WHITE)
            view?.loan_view_bal?.setTextColor(Color.WHITE)

            return view!!

        }

        override fun getItemId(position: Int):Long{
            return position.toLong()
        }


        override fun getHeaderId(position: Int): Long {
            return getItem(position)!!.year.toLong()
        }


    }
