package com.mamram.checktezostokenprice.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ContractStorageModel(
    @SerializedName("storage")
    var storage: StorageModel = StorageModel(),
    @SerializedName("metadata")
    var metadata: Int = 0,
    @SerializedName("dex_lambdas")
    var dexLambdas: Int = 0,
    @SerializedName("token_lambdas")
    var tokenLambdas: Int = 0
) : Parcelable