package workshop.akbolatss.xchangesrates.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: Akbolat Sadvakassov
 * Date: 22.11.2017
 */

object UtilityMethods {

    val todayDate: String
        get() {
            val date = Date()
            return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(date)
        }

    fun getTimestamp(bpiDate: String): Long {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = format.parse(bpiDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date!!.time
    }

    fun getCalculatedBackDays(daysAgo: Int): String {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo)
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(calendar.time)
    }

    /**
     * Check if WiFi is Connected.
     */
    fun isWiFiConnected(context: Context): Boolean {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo

        return wifiInfo.networkId != -1
    }

    /**
     * Check network availability.
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}
