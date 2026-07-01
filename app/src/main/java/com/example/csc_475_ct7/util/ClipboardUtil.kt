package com.example.csc_475_ct7.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent

object ClipboardUtil {
    fun copyToClipboard(context: Context, text: String, label: String = "Conversion") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }

    fun shareText(context: Context, text: String, subject: String = "Unit Conversion") {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }
}
