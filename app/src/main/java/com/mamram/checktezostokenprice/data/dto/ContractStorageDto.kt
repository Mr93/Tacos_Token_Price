package com.mamram.checktezostokenprice.data.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mamram.checktezostokenprice.data.entity.Contract
import com.mamram.checktezostokenprice.data.model.ContractStorageModel
import com.mamram.checktezostokenprice.data.model.StorageModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContractStorageDto(
    @SerializedName("storage")
    val storage: StorageDto?,
    @SerializedName("metadata")
    val metadata: Int?,
    @SerializedName("dex_lambdas")
    val dexLambdas: Int?,
    @SerializedName("token_lambdas")
    val tokenLambdas: Int?
) : Parcelable

fun ContractStorageDto.toModel(): ContractStorageModel {
    return ContractStorageModel(storage = storage?.toModel() ?: StorageModel())
}