package com.example.aseefm.helpers

import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import com.example.aseefm.R
import com.example.aseefm.SavedPreference
import com.example.aseefm.networking.model.Artist
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.google.android.material.textfield.TextInputEditText

const val PREF_SELECTED_LANGUAGE = "selected_language"
const val PREF_SELECTED_DATE = "selected_date"
const val PREF_THEME_MODE = "theme_mode"
const val PREF_METRIC = "selected_metric"
const val PREF_HOUR = "selected_time"

fun TextInputEditText.customValidate(context: Context): Boolean {
    val valid = true

    if (this.text!!.isBlank()) {
        this.error = context.getString(R.string.please_insert_value)
        this.requestFocus()
        return false
    }

    when (this.tag) {
        "URL" -> {
            if (!this.text!!
                    .contains
                        (
                        "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
                            .toRegex()
                    )
            ) {
                this.error = context.getString(R.string.enter_valid_url)
                return false
            }
        }
        "YTURL" -> {
            if (!this.text!!
                    .contains(
                        "^(https?://)?(www\\.youtube\\.com|youtu\\.be)/.+\$"
                            .toRegex()
                    )
            ) {
                this.error = context.getString(R.string.youtube_url)
                return false
            }
        }
    }
    return valid
}

fun showProfileCustomDialog(context: Context) {
    val dialog = Dialog(context).apply {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setCancelable(true)
        this.setContentView(R.layout.custom_dialog_profile)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    val btnOk = dialog.findViewById<Button>(R.id.btnDialogOk)
    val edit = dialog.findViewById<TextInputEditText>(R.id.nameEditText)
    btnOk.text = context.getText(R.string.ok)

    btnOk.setOnClickListener {
        if (edit.text.toString() != "") {
            SavedPreference.setUsername(context, edit.text.toString())
        }
        dialog.dismiss()
    }
    dialog.show()
}

fun showCustomDialog(title: String, context: Context) {
    val dialog = Dialog(context).apply {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setCancelable(true)
        this.setContentView(R.layout.custom_dialog)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    val tvTitle = dialog.findViewById<TextView>(R.id.tvDialogTitle)
    val btnOk = dialog.findViewById<Button>(R.id.btnDialogOk)
    val btnCancel = dialog.findViewById<Button>(R.id.btnDialogCancel)

    tvTitle.text = title
    btnOk.text = context.getText(R.string.ok)
    btnCancel.visibility = View.GONE

    btnOk.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    )
        }
    }
    return false
}

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(
        Intent(this, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    )

inline fun <reified T : Activity> Context.startActivity(key: String, value: ArrayList<Artist>) =
    startActivity(
        Intent(this, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(key, value)
        }
    )

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean) =
    PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(key, value).apply()

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this).getBoolean(key, false)

fun View.startAnimation(animationId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, animationId))

fun callDelayed(delay: Long, function: Runnable) {
    Handler(Looper.getMainLooper()).postDelayed(
        function, delay
    )
}

fun getBoolFromPreferences(key: String, default: Boolean, context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, default) ?: default
}

fun getStringFromPreferences(key: String, default: String, context: Context): String {
    val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, default) ?: default
}

fun savePreferenceBool(key: String, value: Boolean, context: Context) {
    val sharedPreferences = context
        .getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(key, value)
    editor.apply()
}

fun savePreferenceString(key: String, value: String, context: Context) {
    val sharedPreferences = context
        .getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}

fun showCustomSnackbar(ctx: Context, view: View, message: String) {
    val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
    val snl = snackbar.view as SnackbarLayout

    val inflater = LayoutInflater.from(ctx)
    val customSnackbarView = inflater.inflate(R.layout.custom_snackbar, null)

    customSnackbarView.findViewById<TextView>(R.id.snackbar_text).apply {
        text = message
    }
    customSnackbarView.findViewById<ImageButton>(R.id.snackbar_dismiss).setOnClickListener {
        snackbar.dismiss()
    }

    // Add bottom margin to the view
    val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.setMargins(0, 0, 0, 75)
    view.layoutParams = layoutParams

    snl.addView(customSnackbarView)
    snackbar.show()
}

fun generateUniqueColor(input: String): String {
    val hashCode = (input + "Hello").hashCode()
    return String.format("#66%06X", (hashCode and 0xFFFFFF))
}