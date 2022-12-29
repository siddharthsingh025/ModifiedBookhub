package com.siddharth.bookhub.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo

class ConnectionManager {

    fun checkConnectivity(context: Context):Boolean{

        //its check device connection is enable or not and work perfectly or not
        val conectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        //its check is our connection has internet or not (asses)
        val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo

        if(activeNetwork?.isConnected != null)
        {
            return activeNetwork.isConnected
        }
        else
        {
            return false
        }


    }
}