package com.example.journel.app.extensions

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.disable() {
    this.isEnabled = false
}

fun View.enable() {
    this.isEnabled = true
}

fun Fragment.showToast(msg: String) {
    this.context?.let {
        Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
    }
}