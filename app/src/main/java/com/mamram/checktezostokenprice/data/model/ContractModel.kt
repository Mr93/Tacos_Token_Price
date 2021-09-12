package com.mamram.checktezostokenprice.data.model

import androidx.room.Entity

@Entity
data class ContractModel(
    var id: Int, var contractName: String,
    var contractAddress: String,
    var lastRate: Double, var isReverse: Boolean = false
)