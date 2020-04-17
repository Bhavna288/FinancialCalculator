package com.mns.financialcalculator.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mns.financialcalculator.R
import java.text.SimpleDateFormat
import java.util.*

class Validation {

    companion object{

        var status=false

        fun nameValidation(a: TextInputEditText,til: TextInputLayout):Boolean{

            val name = a.text.toString()
            //var status = false
            if(name.isEmpty()){
                til.error = "Required"
                a.requestFocus()
                status = false
            }
            if(name.isNotEmpty()){
                til.error = null
                status = true
            }

            return status

        }

        fun fieldValidation(a: TextInputEditText,til: TextInputLayout):Boolean{

            val name = a.text.toString()
            //var status = false
            if(name.isEmpty()){

                status = false
            }
            if(name.isNotEmpty()){

                status = true
            }

            return status

        }




        fun emailValidation(b: TextInputEditText,til:TextInputLayout):Boolean{

            val email = b.text.toString()
            val emailPattern =
                    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            //var stat = false
            if(email.isEmpty()){
                til.error="Required"
                b.requestFocus()
                status = false
            }
            if(email.isNotEmpty()){

                if(email.matches(emailPattern.toRegex())){
                    til.error = null
                    status = true

                }
                else{
                    til.error = "invalid email format"
                    b.requestFocus()
                    status =false
                }

            }
            return status
        }

        fun phoneValidation(c: TextInputEditText,til: TextInputLayout):Boolean{

            val number = c.text.toString()
            // var statuss = false
            if(number.isEmpty()){
                til.error = "Required"
                c.requestFocus()
                status = false
            }

            if(number.isNotEmpty()){

                if(number.length!=10){
                    til.error = "10 digits required"
                    c.requestFocus()
                    status = false
                }

                if(number.length==10){
                    til.error=null
                    status = true
                }

            }


            return status
        }

        fun loginpwdValidation(f:TextInputEditText,til:TextInputLayout):Boolean{
            val pwd_login = f.text.toString()
            if(pwd_login.isEmpty()){
                til.error="required"
                f.requestFocus()
                status=false
            }
            if(pwd_login.isNotEmpty()){
                til.error=null
                status=true
            }

            return status


        }

        fun pwdValidation(d: TextInputEditText, e: TextInputEditText, til: TextInputLayout,til2: TextInputLayout, mcontext: Context, mmcontext: Context, view: View):Boolean{ //mlayout: Layout,

            val pwd = d.text.toString()
            val cnfpwd = e.text.toString()


            if(cnfpwd.isEmpty()){
                til2.error="confirm pwd required"
                e.requestFocus()
                status=false
            }

            if(pwd.isEmpty()){
                til.error="pwd required"
                d.requestFocus()
                status=false
            }



            if(pwd.isEmpty() && cnfpwd.isNotEmpty()){
                til.error="pwd required"
                d.requestFocus()
                e.error=null
                til2.error=null
                status=false


            }

            if(pwd.isNotEmpty() && cnfpwd.isEmpty()){

                d.error=null
                til.error=null
                til2.error="confirm pwd required"
                e.requestFocus()
                status=false

            }

            if(pwd.isNotEmpty()&& cnfpwd.isNotEmpty()){

                if(pwd!=cnfpwd){
                    d.error= null
                    e.error=null
                    til.error=null
                    til2.error=null
                    d.requestFocus()
                    //Toast.makeText(mcontext,"pwd not matching",Toast.LENGTH_SHORT).show()

                    val snackregister = Snackbar.make(view,"Password not matching", Snackbar.LENGTH_SHORT)

                    snackregister.setAction("dismiss", View.OnClickListener {

                        snackregister.dismiss()
                    })

                    snackregister.setActionTextColor(ContextCompat.getColor(mmcontext, R.color.btn_signout_btint))


                    snackregister.show()


                    status=false

                }

                else{
                    d.error= null
                    e.error=null
                    til.error=null
                    til2.error=null
                    //Toast.makeText(mcontext,"registering...",Toast.LENGTH_SHORT).show()

                    /*val snackregister = Snackbar.make(view,"Registering...", Snackbar.LENGTH_SHORT)

                    snackregister.setAction("dismiss", View.OnClickListener {

                        snackregister.dismiss()
                    })

                    snackregister.setActionTextColor(ContextCompat.getColor(mmcontext, R.color.colorAccent))


                    snackregister.show()*/

                    status=true
                }

            }

            return status

        }


        /*sip tools*/

        fun checkEmptyValidation(et:TextInputEditText,til: TextInputLayout):Boolean{
            val checking = et.text.toString()
            if(checking.isEmpty()){
                til.error="Required"
                et.requestFocus()
                status=false
            }

            else{
                til.error=null
                status=true
            }

            return status

        }


        fun getCurrentDate(format: String?): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat(format, Locale.ENGLISH)
            return sdf.format(cal.time)
        }

        fun convertStringToCalendar(formate: String, dateStr: String): Calendar {
            val df = SimpleDateFormat(formate, Locale.ENGLISH)
            val cal = Calendar.getInstance()
            cal.time = df.parse(dateStr)
            return cal
        }






    }
}