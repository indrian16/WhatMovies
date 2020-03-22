package io.indrian.whatmovies.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun Fragment.showToast(message: String) = Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
fun AppCompatActivity.showToast(message: String) = Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()

fun View.setVisibility(visible: Boolean = true) {

    if (visible) {

        this.visibility = View.VISIBLE
    } else {

        this.visibility = View.GONE
    }
}

@SuppressLint
fun Context.isNetworkConnected(): Boolean {

    var result = false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val networkCapabilities = cm.activeNetwork ?: return false
        val activeCapabilities = cm.getNetworkCapabilities(networkCapabilities) ?: return false

        result = when {

            activeCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {

        cm.run {

            this.activeNetworkInfo?.run {

                result = when (type) {

                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true

                    else -> false
                }
            }
        }
    }

    return result
}