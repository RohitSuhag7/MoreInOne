package org.example.moreinone.common

import android.content.Context
import android.widget.Toast

fun toastMessage(
    context: Context,
    msg: String,
) {
    Toast.makeText(
        context,
        msg,
        Toast.LENGTH_SHORT
    ).show()
}
