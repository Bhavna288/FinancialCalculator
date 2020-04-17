package com.mns.financialcalculator

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.mns.financialcalculator.fragments.home.HomeFragment
import com.mns.financialcalculator.fragments.investment.InvestmentFragment
import com.mns.financialcalculator.fragments.loan.LoanFragment
import com.mns.financialcalculator.fragments.tax.TaxFragment
import com.mns.financialcalculator.fragments.user.UserFragment
import com.mns.financialcalculator.interfaces.OnClickInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),OnClickInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        setContentView(R.layout.activity_main)

        if (toolbar != null) {
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            toolbar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            toolbar!!.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
            //toolbar!!.titleMarginTop=30
        }

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent))

        //val actionBar = supportActionBar?.apply{hide()}
        //getSupportActionBar()?.setTitle(Html.fromHtml("<font color='#000000'>Money Planner</font>"))

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        /*val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_loan, R.id.navigation_investment
                , R.id.navigation_tax
                , R.id.navigation_user
            )
        )*/


        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)


        navView.setOnNavigationItemSelectedListener() { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_navigation_home -> {
                    toolbar?.title = resources.getString(R.string.app_name)
                    loadFragment(HomeFragment(),null)
                    //nav_view.selectedItemId = R.id.navigation_home
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.bottom_navigation_investment -> {
                    toolbar?.title = resources.getString(R.string.title_investment)
                    loadFragment(InvestmentFragment(),null)
                    //nav_view.selectedItemId = R.id.navigation_investment
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.bottom_navigation_loan -> {
                    toolbar?.title = resources.getString(R.string.title_loan)
                    loadFragment(LoanFragment(),null)
                    //nav_view.selectedItemId = R.id.navigation_loan
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.bottom_navigation_tax -> {
                    toolbar?.title = resources.getString(R.string.title_tax)
                    loadFragment(TaxFragment(),null)
                    //nav_view.selectedItemId = R.id.navigation_tax
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.bottom_navigation_user -> {
                    toolbar?.title = resources.getString(R.string.title_user)
                   loadFragment(UserFragment(),null)
                    //nav_view.selectedItemId = R.id.navigation_user
                    return@setOnNavigationItemSelectedListener true

                }
            }
            return@setOnNavigationItemSelectedListener false
        }



    }

    private fun init() {

         // initializing to home fragment
        val fragment = HomeFragment()
        loadFragment(fragment,"")



    }

    private fun loadFragment(fragment: Fragment, comeFrom: String?) {


            val transaction = supportFragmentManager.beginTransaction()
            val bundle = Bundle()
            bundle.putString("come_from", comeFrom)
            fragment.arguments = bundle
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }

    override fun onBackPressed() {


        // custom dialog developed.

        val dialog = Dialog(this)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(true)
        dialog .setContentView(R.layout.custom_dialogg)


        val dialogNoBtn = dialog .findViewById(R.id.dialogNoBtn) as MaterialButton
        val dialogYesBtn = dialog .findViewById(R.id.dialogYesBtn) as MaterialButton

        dialogNoBtn!!.setOnClickListener {
            dialog .dismiss()
        }

        dialogYesBtn!!.setOnClickListener {
            finish()
        }


        dialog.show()


    }

    override fun onClickInterface(name: String) {
        Log.e("Money Planner", "name::$name")

            when (name) {

                "eligibility" -> {

                    nav_view.selectedItemId = R.id.bottom_navigation_loan
                    loadFragment(LoanFragment(), "eligibility")


                }
                "emi_calculation" -> {

                    nav_view.selectedItemId = R.id.bottom_navigation_loan
                    loadFragment(LoanFragment(), "emi_calculation")


                }


                "fixed_deposit" -> {

                    nav_view.selectedItemId = R.id.bottom_navigation_investment
                    loadFragment(InvestmentFragment(), "fixed_deposit")


                }
                "mutual_fund" -> {

                    nav_view.selectedItemId = R.id.bottom_navigation_investment
                    loadFragment(InvestmentFragment(), "mutual_fund")


                }
                "goal" -> {

                    nav_view.selectedItemId = R.id.bottom_navigation_investment
                    loadFragment(InvestmentFragment(), "goal")


                }

                "gst"-> {

                    nav_view.selectedItemId = R.id.bottom_navigation_tax
                    loadFragment(TaxFragment(), "gst")

                }

                "it" -> {
                    nav_view.selectedItemId = R.id.bottom_navigation_tax
                    loadFragment(TaxFragment(), "it")

                }
            }


    }
}
