package com.example.chocotest.base

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.chocotest.R

fun showAlert(message: String, context: Context) {
    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
        dialog.dismiss()
    }
    dialogBuilder
        .setMessage(message)
        .show()
}