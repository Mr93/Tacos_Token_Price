package com.mamram.checktezostokenprice.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContractInfoModel(
    @SerializedName("type")
    var type: String = "",
    @SerializedName("address")
    var address: String = ""
) : Parcelable