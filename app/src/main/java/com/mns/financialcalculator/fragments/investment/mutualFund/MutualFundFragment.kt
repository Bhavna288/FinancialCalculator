package com.mns.financialcalculator.fragments.investment.mutualFund

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.fragment_mutual_fund.*
import java.util.ArrayList


class MutualFundFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mutual_fund, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = MyViewPagerAdapter(childFragmentManager)


        adapter.fragmentadd(MutualFundContentFragment(),"SIP Calc")
        adapter.fragmentadd(MutualFundContentFragment(),"SWP Calc")
        adapter.fragmentadd(MutualFundContentFragment(),"STP Calc")




        frag_mut_fund_viewpager.adapter = adapter
        frag_mut_fund_tabs.setupWithViewPager(frag_mut_fund_viewpager)


    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            val mf= MutualFundContentFragment()
            val bundle=Bundle()
            when(position){
                0->{
                    bundle.putString("come_from","sip_calc")
                    mf.arguments=bundle
                }
                1->{
                    bundle.putString("come_from","swp_calc")
                    mf.arguments=bundle
                }
                2->{
                    bundle.putString("come_from","stp_calc")
                    mf.arguments=bundle
                }


            }
            return mf
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
