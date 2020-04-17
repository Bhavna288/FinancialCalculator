package com.mns.financialcalculator.fragments.tax


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch
import com.mns.financialcalculator.R
import com.mns.financialcalculator.utils.Utils.Companion.roundUpFunction
import com.mns.financialcalculator.utils.Validation.Companion.checkEmptyValidation
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.fragment_gst_content.*
import kotlinx.android.synthetic.main.fragment_tax_content.*


class TaxContentFragment : Fragment() {

    var comeFrom: String? = ""

    //gst vars
    var initAmt:Double = 0.0
    var value:Int = 5



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tax_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            comeFrom = arguments?.getString("come_from")
            when (comeFrom) {
                "gst" -> {
                    ll_gst.visibility = View.VISIBLE
                    ll_income_tax.visibility = View.GONE


                }
                "it" -> {
                    ll_gst.visibility = View.GONE
                    ll_income_tax.visibility = View.VISIBLE


                }


            }
        }





        //gst caclulations
        val arr = arrayOf("5%","12%","15%","18%","28%")
        seekBar_gst.customTickTexts(arr)



        //seekbar value changes
        seekBar_gst.setOnSeekChangeListener(object : OnSeekChangeListener {



            override fun onSeeking(seekParams: SeekParams) {
                var a= seekParams.thumbPosition
                when (a){

                    0-> {value=5}
                    1-> {value=12}
                    2-> {value=15}
                    3-> {value=18}
                    4-> {value=28}
                }



            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {}

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {}

        })


        btn_gst_calc.setOnClickListener(){


            if(checkEmptyValidation(gst_init_amt,gst_init_amt_til)){


                gst_toggle_swt.setOnToggleSwitchChangeListener(object: BaseToggleSwitch.OnToggleSwitchChangeListener {
                    override fun onToggleSwitchChangeListener(position:Int, isChecked:Boolean) {

                        when(position){

                            0-> {

                                btn_gst_calc.setOnClickListener(){

                                    if(checkEmptyValidation(gst_init_amt,gst_init_amt_til)){

                                        initAmt = gst_init_amt.text.toString().toDouble()
                                        //value = seekBar_gst.progress


                                        val x = ((initAmt * 100)/(100 + value.toDouble()))
                                        val perc = (value.toDouble()/100)*x
                                        //val incl = initAmt + perc

                                        gst_inc_result.visibility=View.VISIBLE
                                        gst_exc_result.visibility=View.GONE

                                        gst_inc_net_amt.setText(roundUpFunction(initAmt, 2).toString())
                                        gst_inc_gst_value.setText(roundUpFunction(perc,2).toString())
                                        gst_inc_tot_amt.setText(roundUpFunction(x,2).toString())

                                    }


                                }


                            }

                            1-> {

                                btn_gst_calc.setOnClickListener(){

                                    if(checkEmptyValidation(gst_init_amt,gst_init_amt_til)){

                                        initAmt = gst_init_amt.text.toString().toDouble()
                                        //value = seekBar_gst.progress

                                        val perc = (value.toDouble()/100)*initAmt
                                        val excl = initAmt + perc

                                        gst_inc_result.visibility=View.GONE
                                        gst_exc_result.visibility=View.VISIBLE


                                        gst_exc_net_amt.setText(roundUpFunction(initAmt,2).toString())
                                        gst_exc_gst_value.setText(roundUpFunction(perc,2).toString())
                                        gst_exc_tot_amt.setText(roundUpFunction(excl,2).toString())
                                    }


                                }


                            }
                        }



                    }
                })


            }


        }

    }

}
