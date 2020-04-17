package com.mns.financialcalculator.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mns.financialcalculator.R
import com.mns.financialcalculator.interfaces.OnClickInterface
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.ClassCastException

class HomeFragment : Fragment() {

    var onClickInterface:OnClickInterface?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try{
            onClickInterface=activity as OnClickInterface
        }
        catch (e:ClassCastException){
            Log.e("Money Planner","ClassCastException!")
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar?.setTitle(Html.fromHtml("<font color='#000000'>Money Planner</font>"))

            // passing data from one fragment to another fragment

        btn_eligibility.setOnClickListener(){

            onClickInterface?.onClickInterface("eligibility")

            /*val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, LoanFragment(),arguments?.getString("come_from")) //.arguments kri ne bundle pass kro
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()*/

            //activity!!.nav_view.selectedItemId = R.id.bottom_navigation_loan

        }

        btn_emi_calc.setOnClickListener(){

            onClickInterface?.onClickInterface("emi_calculation")

            /*val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()


            fragmentTransaction.replace(R.id.nav_host_fragment, LoanFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()*/

            //activity!!.nav_view.selectedItemId = R.id.bottom_navigation_loan

        }

        btn_fd.setOnClickListener(){

            onClickInterface?.onClickInterface("fixed_deposit")


            /*val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val comeFrom = "fixed_deposit"
            val fragment= InvestmentFragment()
            val bundle = Bundle()
            bundle.putString("come_from", comeFrom)
            fragment.arguments = bundle
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
*/
           // activity!!.nav_view.selectedItemId = R.id.bottom_navigation_investment

        }

        btn_mut_fund.setOnClickListener(){
            onClickInterface?.onClickInterface("mutual_fund")




            /*val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val comeFrom = "mutual_fund"
            val fragment= InvestmentFragment()
            val bundle = Bundle()
            bundle.putString("come_from", comeFrom)
            fragment.arguments = bundle
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()*/

           // activity!!.nav_view.selectedItemId = R.id.bottom_navigation_investment


        }

        btn_goal.setOnClickListener(){

            onClickInterface?.onClickInterface("goal")



            /* val fragmentManager = activity!!.supportFragmentManager
             val fragmentTransaction = fragmentManager.beginTransaction()
             val comeFrom= "goal"
             val fragment= InvestmentFragment()
             val bundle = Bundle()
             bundle.putString("come_from", comeFrom)
             fragment.arguments = bundle
             fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
             fragmentTransaction.addToBackStack(null)
             fragmentTransaction.commit()*/

           // activity!!.nav_view.selectedItemId = R.id.bottom_navigation_investment


        }

        btn_gst.setOnClickListener(){

            onClickInterface?.onClickInterface("gst")

            /*val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, TaxFragment(),arguments?.getString("come_from"))
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()*/

            //activity!!.nav_view.selectedItemId = R.id.bottom_navigation_tax

        }

        btn_it.setOnClickListener(){

            onClickInterface?.onClickInterface("it")

            /* val fragmentManager = activity!!.supportFragmentManager
             val fragmentTransaction = fragmentManager.beginTransaction()
             fragmentTransaction.replace(R.id.nav_host_fragment, TaxFragment(),arguments?.getString("come_from"))
             fragmentTransaction.addToBackStack(null)
             fragmentTransaction.commit()*/

           // activity!!.nav_view.selectedItemId = R.id.bottom_navigation_tax
        }

    }
}