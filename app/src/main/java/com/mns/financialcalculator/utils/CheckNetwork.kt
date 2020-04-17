package com.mns.financialcalculator.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log


class CheckNetwork {

    companion object{

        private val TAG = CheckNetwork::class.java.simpleName

        fun isInternetAvailable(context:Context):Boolean{

            val info = (context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager)//.activeNetworkInfo as NetworkInfo

            val activeNetworkInfo:NetworkInfo? = info.activeNetworkInfo

                if(activeNetworkInfo!=null)
                {
                    Log.d(TAG," internet connection available...")
                    return true
                }
                else
                {
                    //Toast.makeText(context,"No internet connection",Toast.LENGTH_SHORT).show()
                    Log.d(TAG," No internet connection")
                    return false
                }

        }
    }
}