package com.mamram.checktezostokenprice.utils

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.mamram.checktezostokenprice.R
import kotlin.math.pow
import kotlin.math.roundToInt

fun Activity.showSnackBar(snackBarText: String, timeLength: Int) {
    val snackBar = com.google.android.material.snackbar.Snackbar.make(
        this.findViewById<View>(android.R.id.content),
        snackBarText,
        timeLength
    ).apply {
        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
    }
    snackBar.setTextColor(resources.getColor(R.color.white))
    val view = snackBar.view
    val params = view.layoutParams as FrameLayout.LayoutParams
    params.gravity = android.view.Gravity.TOP
    params.marginStart = this.resources.getDimensionPixelSize(R.dimen.activity_main_margin).toInt()
    params.marginEnd = this.resources.getDimensionPixelSize(R.dimen.activity_main_margin).toInt()
    params.topMargin = this.resources.getDimensionPixelSize(R.dimen.activity_main_margin).toInt()
    params.bottomMargin = this.resources.getDimensionPixelSize(R.dimen.activity_main_margin).toInt()
    view.layoutParams = params
    view.background = androidx.core.content.ContextCompat.getDrawable(
        this,
        R.drawable.background_red_round_small
    ) // for custom background
    snackBar.animationMode =
        com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
    snackBar.show()
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}