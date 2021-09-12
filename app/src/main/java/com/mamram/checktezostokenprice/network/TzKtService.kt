package com.mamram.checktezostokenprice.network

import com.mamram.checktezostokenprice.data.dto.ContractInfoDto
import com.mamram.checktezostokenprice.data.dto.ContractStorageDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TzKtService {
    @GET("contracts/{address}")
    suspend fun getContractInfo(@Path("address") address: String): ContractInfoDto

    @GET("contracts/{address}/storage")
    suspend fun getContractStorage(@Path("address") address: String): ContractStorageDto
}