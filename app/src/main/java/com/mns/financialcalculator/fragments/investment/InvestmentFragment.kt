package com.mns.financialcalculator.fragments.investment


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
import kotlinx.android.synthetic.main.fragment_investment.*
import java.util.ArrayList

class InvestmentFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_investment, container, false)


        return root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setTitle(Html.fromHtml("<font color='#000000'>Investment</font>"))


        val adapter = MyViewPagerAdapter(childFragmentManager)


        adapter.fragmentadd(InvestmentContentFragment(),"Fixed Deposit")
        adapter.fragmentadd(InvestmentContentFragment(),"Mutual Fund")
        adapter.fragmentadd(InvestmentContentFragment(),"Goal")


        frag_investment_viewpager.adapter = adapter
        frag_investment_tabs.setupWithViewPager(frag_investment_viewpager)


        //home fragment to this fragment
        frag_investment_viewpager.offscreenPageLimit = 0

        frag_investment_viewpager.addOnPageChangeListener(object : TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
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
                frag_investment_viewpager.currentItem = tab?.position!!
            }
        })


        if (!arguments?.getString("come_from").isNullOrEmpty()) {
            when (arguments?.getString("come_from")) {
                "fixed_deposit" -> {
                    frag_investment_viewpager.currentItem = 0
                }
                "mutual_fund" -> {
                    frag_investment_viewpager.currentItem = 1
                }
                "goal" -> {
                    frag_investment_viewpager.currentItem = 2
                }
            }

        }




    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            val inv= InvestmentContentFragment()
            //val comeFrom =  arguments?.getString("come_from")
            val bundle=Bundle()
            when(position){
                0->{
                    bundle.putString("come_from","fixed_deposit")
                    inv.arguments=bundle
                }
                1->{
                    bundle.putString("come_from","mutual_fund")
                    inv.arguments=bundle
                }
                2->{
                    bundle.putString("come_from","goal")
                    inv.arguments=bundle
                }

            }
            return inv
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