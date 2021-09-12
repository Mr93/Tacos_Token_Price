package com.mamram.checktezostokenprice.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mamram.checktezostokenprice.data.model.ContractModel

@Entity
data class Contract(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") val contractName: String,
    @ColumnInfo(name = "address") val contractAddress: String,
    @ColumnInfo(name = "last_rate") val lastRate: Double
)

fun Contract.toModel(): ContractModel {
    return ContractModel(id, contractName, contractAddress, lastRate)
}

fun List<Contract>.toModels(): List<ContractModel> {
    return this.map { it.toModel() }
}