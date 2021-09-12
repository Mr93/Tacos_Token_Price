package com.mamram.checktezostokenprice.utils

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import com.mamram.checktezostokenprice.R

@BindingAdapter("itemIndex")
internal fun setBackgroundItem(view: ViewGroup, itemIndex: Int) {
    if (itemIndex % 2 == 0) {
        view.setBackgroundResource(R.color.light_background_item_color)
    } else {
        view.setBackgroundResource(R.color.dark_background_item_color)
    }
}