package com.manuelcarvalho.speccygraphs.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat

private const val TAG = "utils"  //context: Context, uri: Uri
fun sendEmail(context: Context, uri: Uri) {

    val to = "tom@gmail.com"
    val subject = "Assembler Image data."
    val intent = Intent(Intent.ACTION_SEND)
    val addressees = arrayOf(to)
    intent.putExtra(Intent.EXTRA_EMAIL, addressees)
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.type = "message/rfc822"
    ContextCompat.startActivity(context, Intent.createChooser(intent, "Select Email Sending App :"), null)

}