package com.mamram.checktezostokenprice.data.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContractInfoDto(
    @SerializedName("type")
    val type: String?,
    @SerializedName("alias")
    val alias: String?
) : Parcelable