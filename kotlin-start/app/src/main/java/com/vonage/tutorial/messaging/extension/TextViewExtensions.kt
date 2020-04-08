package com.vonage.tutorial.messaging.extension

import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.setText(@StringRes res: Int, vararg formatArgs: Any?) {
    text = resources.getString(res, *formatArgs)
}