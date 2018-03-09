package com.jati.simpleretrofit.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by jati on 10/03/18.
 */
fun Context.toast(msg: String, dur: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, dur).show()
}