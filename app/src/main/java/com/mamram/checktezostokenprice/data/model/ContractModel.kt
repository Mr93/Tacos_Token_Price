package com.mamram.checktezostokenprice.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContractModel(
    var id: Int, var contractName: String,
    var contractAddress: String,
    var lastRate: Double
)