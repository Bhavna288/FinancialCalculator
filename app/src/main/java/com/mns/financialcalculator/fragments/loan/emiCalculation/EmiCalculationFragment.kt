package com.mns.financialcalculator.fragments.loan.emiCalculation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.fragment_emi_calculation.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class EmiCalculationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emi_calculation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        val adapter = MyViewPagerAdapter(childFragmentManager)


        adapter.fragmentadd(EmiCalculationFragment(),"Emi")
        adapter.fragmentadd(EmiCalculationFragment(),"Tenure")
        adapter.fragmentadd(EmiCalculationFragment(),"Amount")
        adapter.fragmentadd(EmiCalculationFragment(),"Rate")



        frag_emi_viewpager.adapter = adapter
        frag_emi_tabs.setupWithViewPager(frag_emi_viewpager)


    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()


        override fun getItem(position: Int): Fragment {
            val emi= EmiCalculationContentFragment()
            val bundle=Bundle()
            when(position){
                0->{
                    bundle.putString("come_from","emi")
                    emi.arguments=bundle
                }
                1->{
                    bundle.putString("come_from","tenure")
                    emi.arguments=bundle
                }
                2->{
                    bundle.putString("come_from","amount")
                    emi.arguments=bundle
                }
                3->{
                    bundle.putString("come_from","rate")
                    emi.arguments=bundle
                }


            }
            return emi
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

        fun fragmentadd(fragment: Fragment, title: String) {

            fragmentList.add(fragment)
            titleList.add(title)
        }




    }



}
