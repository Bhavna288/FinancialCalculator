package com.mns.financialcalculator.utils

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import au.com.bytecode.opencsv.CSVWriter
import com.mns.financialcalculator.R
import com.mns.financialcalculator.utils.Validation.Companion.getCurrentDate
import java.io.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Utils {

    companion object {

        var pd : ProgressDialog?= null
        val STORAGE_REQUEST_CODE = 101
        var read_permission:Int?=null
        var write_permission:Int?=null

        fun roundUpFunction(value: Double, places: Int): Double? {
            if (places < 0) throw IllegalArgumentException()

            var bd = BigDecimal(value)
            bd = bd.setScale(places, RoundingMode.HALF_UP)
            return bd.toDouble()
        }


        fun FormatString(value: Double): String {
            val df = DecimalFormat("##,##,##,###")
            val dfs = DecimalFormatSymbols()
            dfs.setGroupingSeparator(',')
            df.setDecimalFormatSymbols(dfs)
            return df.format(value)
        }

        fun FormatStringwithpoint(value: Double, useComma: Boolean): String {
            val df = DecimalFormat("#########.##")
            val dfs = DecimalFormatSymbols()
            df.setDecimalFormatSymbols(dfs)
            return df.format(value)
        }

        fun FormatFloatString(value: Double): String {
            val df = DecimalFormat("##,##,##,###.##")
            val dfs = DecimalFormatSymbols()
            dfs.setGroupingSeparator(',')
            //		df.setMinimumFractionDigits(2);
            df.setDecimalFormatSymbols(dfs)
            return df.format(value)
        }

        fun SetDate(date: String, format_date: String): Calendar {
            var dt = Date()
            try {
                val sdf = SimpleDateFormat(format_date)
                dt = sdf.parse(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val cal = Calendar.getInstance()
            cal.setTime(dt)
            return cal
        }
        /*fun ConvertDateFormat(date:String, format_date:String,
                          format_return:String):String {
        var dt = Date()
        try
        {
            val sdf = SimpleDateFormat(format_date)
            dt = sdf.parse(date)
        }
        catch (e:Exception) {
            e.printStackTrace()
        }
        return DateFormat.format(format_return, dt).toString()
    }*/
        /*Run time Permissions*/


         fun setupPermissions(cnx:Context) {
             read_permission = ContextCompat.checkSelfPermission(
                cnx, Manifest.permission.READ_EXTERNAL_STORAGE)

              write_permission = ContextCompat.checkSelfPermission(
                 cnx, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (write_permission != PackageManager.PERMISSION_GRANTED||read_permission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission disallowed")
                //Toast.makeText(cnx,"Permission denied",Toast.LENGTH_SHORT).show()
                Toast.makeText(cnx,"Permission is required to share this file",Toast.LENGTH_LONG).show()

                makeRequest(cnx as Activity)
            }

             else{
                Log.i(TAG, "Permission allowed")
                Toast.makeText(cnx,"Permission granted",Toast.LENGTH_SHORT).show()

            }
        }

         fun makeRequest(act: Activity) {
            ActivityCompat.requestPermissions(act,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),
                STORAGE_REQUEST_CODE)

        }

        fun ExportToCSV(cnx: Context, file_title: String, lines: ArrayList<Array<String>>) {

              pd = ProgressDialog(cnx)

            try {

                pd!!.setMessage("Generating CSV File...")
                pd!!.setTitle(cnx.getResources().getString(R.string.app_name))
                // pd.setIcon(R.drawable.ic_launcher_foreground)
                pd!!.setCancelable(true)
                pd!!.setCanceledOnTouchOutside(false)
                pd!!.show()

                MyTask(cnx,file_title,lines).execute()
                Log.e("intent","passed")
            }
            catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(cnx, "Error Generating Data...", Toast.LENGTH_SHORT).show()
                return
            }

        }


           class MyTask(internal var cnx: Context, internal var f:String, internal var l:ArrayList<Array<String>>) : AsyncTask<Void, Void, String>()
                {
                   // val pd = ProgressDialog(cnx)


                    override fun doInBackground(vararg params: Void): String {

                        return CreateCSVFile(cnx, f, l)

                    }

                    override fun onPostExecute(file: String) {
                        super.onPostExecute(file)
                        pd!!.dismiss()


                            if (!file.isEmpty()) {
                                val ff = File(file)

                                if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N){

                                    if(write_permission == PackageManager.PERMISSION_GRANTED&&
                                        read_permission == PackageManager.PERMISSION_GRANTED){

                                        val file_uri:Uri? = FileProvider.getUriForFile(cnx, "com.mns.financialcalculator.fileprovider", ff)

                                        val i = Intent(Intent.ACTION_SEND)
                                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        i.setType("text/csv")
                                        i.putExtra(Intent.EXTRA_STREAM, file_uri)

                                        val start = Intent.createChooser(i, "Share CSV File...")

                                        if (start.resolveActivity(cnx.getPackageManager()) != null) {
                                            cnx.startActivity(start)
                                        }

                                        else {
                                            Toast.makeText(cnx, "Fail to share CSV file", Toast.LENGTH_SHORT).show()
                                        }

                                    }

                                    else{

                                        setupPermissions(cnx)
                                    }

                                }

                                else{

                                    val file_uri = Uri.fromFile(ff) //this is not used after targetsdk >24

                                    val i = Intent(Intent.ACTION_SEND)
                                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    i.setType("text/csv")
                                    i.putExtra(Intent.EXTRA_STREAM, file_uri)

                                    val start = Intent.createChooser(i, "Share CSV File...")

                                    if (start.resolveActivity(cnx.getPackageManager()) != null) {
                                        cnx.startActivity(start)
                                    }

                                    else {
                                        Toast.makeText(cnx, "Fail to share CSV file", Toast.LENGTH_SHORT).show()
                                    }

                                }


                            }

                            else {
                                Toast.makeText(cnx, "Fail to share csv file", Toast.LENGTH_SHORT).show()
                            }






                    }
                }


    fun CreateCSVFile(cnx: Context, file_title: String, lines: ArrayList<Array<String>>): String {
        try {
            val dir = File(
                ""+Environment.getExternalStorageDirectory() + File.separator
                        + cnx.getResources().getString(R.string.app_name)
                        + File.separator + AppConstant.FOLDER_CSV)
            dir.mkdirs()
            val file_name = (file_title
                    + getCurrentDate(
                "hh.mm aa dd.MMM.yyyy").toString() + ".csv")

            val file_write = File(dir, file_name)
            if (file_write.exists()) {
                return file_write.getAbsolutePath()
            }

            else{

                file_write.createNewFile()

                val fw = FileWriter(file_write)
                val writer = CSVWriter(fw, ',')
                writer.writeAll(lines)
                writer.close()
                return file_write.getAbsolutePath()

            }

        }

        catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
       /* return null.toString()*/
    }

    }
}



   /* fun ReadFromfile(fileName:String, context:Context):String {
        val returnString = StringBuilder()
        var fIn: InputStream? = null
        var isr: InputStreamReader? = null
        var input: BufferedReader? = null
        try
        {
            fIn = context.getResources().getAssets()
                .open(fileName, Context.MODE_WORLD_READABLE)
            isr = InputStreamReader(fIn)
            input = BufferedReader(isr)
            val line = ""
            while ((line = input.readLine()) != null)
            {
                returnString.append(line)
            }
        }
        catch (e:Exception) {
            e.message
        }
        finally
        {
            try
            {
                if (isr != null)
                    isr.close()
                if (fIn != null)
                    fIn.close()
                if (input != null)
                    input.close()
            }
            catch (e2:Exception) {
                e2.message
            }
        }
        return returnString.toString()
    }*/