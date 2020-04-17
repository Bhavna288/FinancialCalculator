package com.mns.financialcalculator.fragments.tax

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.fragment_tax.*
import java.util.ArrayList

class TaxFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tax, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setTitle(Html.fromHtml("<font color='#000000'>Tax</font>"))

        val adapter = MyViewPagerAdapter(childFragmentManager)


        adapter.fragmentadd(TaxContentFragment(),"GST")
        adapter.fragmentadd(TaxContentFragment(),"Income Tax")


        frag_tax_viewpager.adapter = adapter
        frag_tax_tabs.setupWithViewPager(frag_tax_viewpager)

        //home fragment to this fragment
        frag_tax_viewpager.offscreenPageLimit = 0

        frag_tax_viewpager.addOnPageChangeListener(object : TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
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
                frag_tax_viewpager.currentItem = tab?.position!!
            }
        })


        if (!arguments?.getString("come_from").isNullOrEmpty()) {
            when (arguments?.getString("come_from")) {
                "gst" -> {
                    frag_tax_viewpager.currentItem = 0
                }
                "it" -> {
                    frag_tax_viewpager.currentItem = 1
                }

            }

        }


    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            val tax= TaxContentFragment()
            val bundle=Bundle()
            when(position){
                0->{
                    bundle.putString("come_from","gst")
                    tax.arguments=bundle
                }
                1->{
                    bundle.putString("come_from","it")
                    tax.arguments=bundle
                }

            }
            return tax
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
