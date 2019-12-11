package io.indrian.moviecatalogue.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String) = Toast.makeText(activity, message, Toast.LENGTH_LONG).show()

fun View.visible(indicator: Boolean) {

    if (indicator) {

        this.visibility = View.VISIBLE
    } else {

        this.visibility = View.GONE
    }
}