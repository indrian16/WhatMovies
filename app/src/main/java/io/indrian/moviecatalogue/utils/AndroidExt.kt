package io.indrian.moviecatalogue.utils

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String) = Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
fun AppCompatActivity.showToast(message: String) = Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()

fun View.toVisible(visible: Boolean = true) {

    if (visible) {

        this.visibility = View.VISIBLE
    } else {

        this.visibility = View.GONE
    }
}