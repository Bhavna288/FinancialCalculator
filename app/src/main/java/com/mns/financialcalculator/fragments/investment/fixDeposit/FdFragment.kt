package com.mns.financialcalculator.fragments.investment.fixDeposit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.mns.financialcalculator.R
import kotlinx.android.synthetic.main.fragment_fd.*
import java.util.ArrayList


class FdFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fd, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        val adapter = MyViewPagerAdapter(childFragmentManager)


        adapter.fragmentadd(FdContentFragment(),"Fixed Deposit")
        adapter.fragmentadd(FdContentFragment(),"Recurring Deposit")



        frag_fd_viewpager.adapter = adapter
        frag_fd_tabs.setupWithViewPager(frag_fd_viewpager)


    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()


        override fun getItem(position: Int): Fragment {
            val fd= FdContentFragment()
            val bundle=Bundle()
            when(position){
                0->{
                    bundle.putString("come_from","fixed_deposit")
                    fd.arguments=bundle
                }
                1->{
                    bundle.putString("come_from","rec_deposit")
                    fd.arguments=bundle
                }


            }
            return fd
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
