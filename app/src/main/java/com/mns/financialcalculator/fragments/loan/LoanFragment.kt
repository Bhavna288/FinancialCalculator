package com.mns.financialcalculator.fragments.loan

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.fragment_loan.*
import java.util.ArrayList

class LoanFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_loan, container, false)

        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setTitle(Html.fromHtml("<font color='#000000'>Loan</font>"))

        val adapter =
            MyViewPagerAdapter(childFragmentManager)


        adapter.fragmentadd(LoanContentFragment(), "Eligibility")
        adapter.fragmentadd(LoanContentFragment(), "EMI Calculation")


        frag_loan_viewpager.adapter = adapter
        frag_loan_tabs.setupWithViewPager(frag_loan_viewpager)

        //home fragment to this fragment
        frag_loan_viewpager.offscreenPageLimit = 0

        frag_loan_viewpager.addOnPageChangeListener(object : TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                frag_loan_viewpager.currentItem = tab?.position!!
            }
        })


        if (!arguments?.getString("come_from").isNullOrEmpty()) {
            when (arguments?.getString("come_from")) {
                "eligibility" -> {
                    frag_loan_viewpager.currentItem = 0
                }
                "emi_calculation" -> {
                    frag_loan_viewpager.currentItem = 1
                }

            }

        }


    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            val loan = LoanContentFragment()
            val bundle = Bundle()
            when (position) {
                0 -> {
                    bundle.putString("come_from", "eligibility")
                    loan.arguments = bundle
                }
                1 -> {
                    bundle.putString("come_from", "emi_calculation")
                    loan.arguments = bundle
                }


            }
            return loan
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